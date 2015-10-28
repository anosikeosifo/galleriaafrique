package com.galleriafrique.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.galleriafrique.helper.SelectionBuilder;

import java.util.Arrays;

/**
 * Created by Onyecar on 6/1/2015.
 */
public class DataProvider extends ContentProvider {

    private static final String TAG = DataProvider.class.getName();
    DatabaseHelper mOpenHelper = null;
    private static UriMatcher sUriMatcher = buildUriMatcher();


    private static final int POST_ID = 101;
    private static final int POST_LIST = 102;




    public DataProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Log.v(TAG, "delete(uri=" + uri + ", values=" + selectionArgs + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        // TODO: Handle signOut
        if (uri == DataContract.BASE_CONTENT_URI) {
            // Handle whole database deletes (e.g. when signing out)
            deleteDatabase();
            notifyChange(uri, false);
            return 1;
        }


        int match = sUriMatcher.match(uri);


        final SelectionBuilder builder = buildSelection(uri, match);
        int retVal = builder.where(selection, selectionArgs).delete(db);
        notifyChange(uri, !DataContract.hasCallerIsSyncAdapterParameter(uri));
        return retVal;
    }

    @Override
    public String getType(Uri uri) {

        switch (sUriMatcher.match(uri)) {

            case POST_LIST:
                return DataContract.Post.CONTENT_TYPE;
            case POST_ID:
                return DataContract.Post.CONTENT_ITEM_TYPE;


            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG, "insert(uri=" + uri + ")");
        deleteDatabase();
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        long id = 0;
        boolean syncToNetwork = DataContract.hasCallerIsSyncAdapterParameter(uri);
        switch (match) {

            case POST_LIST: {
                id = db.insert(DatabaseHelper.Tables.POST, null, values);
                notifyChange(uri, syncToNetwork);
                return getUriForId(id, uri);
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public boolean onCreate() {

        mOpenHelper = new com.galleriafrique.provider.DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Log.v(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection) + ")");
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        // The bulk of the setting is done inside DataProvider.buildSelection()
        final SelectionBuilder builder = buildSelection(uri, match);
        switch (match) {

            case POST_LIST:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DataContract.Post.SORT_ORDER_DEFAULT;
                }
                break;

            default:
                break;
        }
        return builder.where(selection, selectionArgs).query(db, projection, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.i(TAG, "update(uri=" + uri + ", values=" + values.toString() + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "uri and match in update : " + uri + "match" + match);

        final SelectionBuilder builder = buildSelection(uri, match);
        int retVal = builder.where(selection, selectionArgs).update(db, values);
        boolean syncToNetwork = !DataContract.hasCallerIsSyncAdapterParameter(uri);
        notifyChange(uri, syncToNetwork);
        return retVal;
    }

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;


        matcher.addURI(authority, DataContract.PATH_POST, POST_LIST);
        matcher.addURI(authority, DataContract.PATH_POST + "/#", POST_ID);

//        matcher.addURI(authority, DataContract.PATH_WORKFLOW_HISTORY, WORKFLOW_HISTORY_LIST);
//        matcher.addURI(authority, DataContract.PATH_WORKFLOW_HISTORY + "/#", WORKFLOW_HISTORY_ID);


        return matcher;
    }

    private static Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            return itemUri;
        }
        //something went wrong
        throw new SQLException("Problem inserting into uri: " + uri);
    }

    private void notifyChange(Uri uri, boolean syncToNetwork) {
        Context context = getContext();
        context.getContentResolver().notifyChange(uri, null, syncToNetwork);

        // Widgets can't register content observers so we refresh widgets separately.
        // context.sendBroadcast(ScheduleWidgetProvider.getRefreshBroadcastIntent(context, false));
    }

    private SelectionBuilder buildSelection(Uri uri, int match) {

        Log.d(TAG, "uri and match:" + uri + "match" + match);
        final SelectionBuilder builder = new SelectionBuilder();
        switch (match) {

            case POST_LIST: {
                return builder.table(DatabaseHelper.Tables.POST);
            }
            case POST_ID: {
                final String id = uri.getLastPathSegment();
                return builder.table(DatabaseHelper.Tables.POST)
                        .where(DataContract.Post._ID + "=?", id);
            }


            default: {
                throw new UnsupportedOperationException("Unknown uri for " + match + ": " + uri);
            }
        }
    }


    private void deleteDatabase() {
        // TODO: wait for content provider operations to finish, then tear down
        mOpenHelper.close();
        Context context = getContext();
        DatabaseHelper.deleteDatabase(context);
        mOpenHelper = new DatabaseHelper(getContext());
    }


}
