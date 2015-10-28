package com.galleriafrique.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "galleriafrique.db";
    private static final int DATABASE_VERSION = 101;
    private static String TAG = DatabaseHelper.class.getName();
    private final Context mContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_POST_TABLE);

//        db.execSQL(SQL_CREATE_WORKFLOW_HISTORY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Cancel all current or pending syncs
//        Account account = AccountUtils.getChosenAccount(mContext);
//        if (account != null) {
//            ContentResolver.cancelSync(account, AppSettings.PROVIDER_AUTHORITY);
//        }

        //upgradeDb(db, oldVersion);
        upgradeDb(db, oldVersion, newVersion);

//        if (account != null) {
//            Bundle settingsBundle = new Bundle();
//            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
//            settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
//            Log.i(TAG, "DB upgrade complete requesting resync");
//            ContentResolver.requestSync(account, AppSettings.PROVIDER_AUTHORITY, settingsBundle);
//        }
    }




    final static String SQL_CREATE_POST_TABLE = "CREATE TABLE "
            + Tables.POST + "("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataContract.Post.POST_ID + " INTEGER,"
            + DataContract.Post.USERNAME + " VARCHAR,"
            + DataContract.Post.DESCRIPTION + " VARCHAR,"
            + DataContract.Post.IMAGE_URL + " VARCHAR,"
            + DataContract.Post.CREATED_AT + " VARCHAR,"
            + DataContract.Post.USER_AVATAR + " VARCHAR,"
            + DataContract.Post.COMMENT_COUNT + " INTEGER,"
            + DataContract.Post.FAVORITE_COUNT + " INTEGER,"
            + "UNIQUE (" + DataContract.Post.POST_ID + ") ON CONFLICT REPLACE )";



//    public static void updateAccountSearchIndex(SQLiteDatabase db) {
//        db.execSQL("DELETE FROM " + Tables.ACCOUNTS_SEARCH);
//        db.execSQL(SQL_UPDATE_SEARCH_TABLE);
//        Log.i(TAG, "AccountSearch table updating");
//    }
//
//    public static void updateParticipantSearchIndex(SQLiteDatabase db) {
//        db.execSQL("DELETE FROM " + Tables.PARTICIPANT_SEARCH);
//        db.execSQL(SQL_UPDATE_PARTICIPANT_SEARCH_TABLE);
//        Log.i(TAG, "ParticipantSearch table updating");
//    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }


    private void upgradeDb(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
    }

    interface Tables {
        String POST = "post";

    }


    /**
     * {@code REFERENCES} clauses.
     */

    private interface References {

//        String CREDIT_BATCH_ID = "REFERENCES " + Tables.CREDIT_BATCH
//                + "(" + DataContract.CreditBatch._ID + ")";

    }

    /**
     * {@code TRIGGERS} clauses.
     */

    private interface Triggers {
        // Deletes from dependent tables when corresponding settlement are deleted.
        String WORKFLOW_DELETE = "workflow_delete";
    }

    /**
     * Fully-qualified field names.
     */
    private interface Qualified {

//        String WORKFLOW_PROCESS_ID = Tables.WORKFLOW_HISTORY + "."
//                + DataContract.WorkflowHistoryColumns.PROCESS_ID;
    }


//    public int getLastAccount()
//    {
//        int account = 0;
//        String selectQuery = "SELECT  * FROM " + Tables.ACCOUNT
//                + " ORDER BY "+DataContract.Account.UPDATED
//                +" DESC LIMIT 1";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                    account =cursor.getInt(cursor.getColumnIndex(DataContract.Account._ID));
//            } while (cursor.moveToNext());
//        }
//
//        // closing connection
//        cursor.close();
//        db.close();
//
//        // returning customers
//        return account;
//    }
//    public List<String> getUpdatedAccounts(int amount)
//    {
//        List<String> accounts = new ArrayList<>();
//
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + Tables.ACCOUNT
//                + " ORDER BY "+DataContract.Account.UPDATED
//                +" DESC LIMIT "+amount;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//                do {
//                        accounts.add(cursor.getString(cursor.getColumnIndex(DataContract.Account.NAME)));
//                } while (cursor.moveToNext());
//        }
//
//        // closing connection
//        cursor.close();
//        db.close();
//
//        // returning customers
//        return accounts;
//    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){

            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }


    }
}
