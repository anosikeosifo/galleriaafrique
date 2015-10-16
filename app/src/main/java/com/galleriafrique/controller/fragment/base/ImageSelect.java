package com.galleriafrique.controller.fragment.base;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.Home;
import com.galleriafrique.model.post.Post;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.repo.PostRepo;
import com.galleriafrique.view.adapters.ImageGalleryAdapter;

import java.util.ArrayList;

/**
 * Created by osifo on 9/23/15.
 */
public class ImageSelect extends BaseFragment implements ImageGalleryAdapter.ImageGalleryAdapterListener {

    private Home activity;
    public int imageCount;
    private int[] thumbnailIDs;
    private ImageGalleryAdapter galleryAdapter;
    private String[] arrPath;
    private String capturedPhotoPath;
    private GridView galleryGrid;

    @Override
    public String getTitleText() {
        return "Select image";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_gallery_grid, container, false);
        this.activity = (Home)getActivity();
        setContent(view);
        return view;
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);;
//        this.activity = (Home)getActivity();
//        setContent(view);
//    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void initUI(View view) {
        galleryGrid = (GridView)view.findViewById(R.id.gallery_gridview);
        galleryGrid.setAdapter(galleryAdapter);
    }

    private void setContent(View view) {
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
        final String orderBy = MediaStore.Images.Media.DATE_MODIFIED;
        Cursor imageCursor = activity.getBaseContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, orderBy + " DESC");
        int image_column_index = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
        imageCount = imageCursor.getCount();

        this.arrPath = new String[imageCount];
        thumbnailIDs = new int[imageCount];

        arrPath = new String[imageCount];
        for (int i = 0; i < imageCount; i++) {
            imageCursor.moveToPosition(i);
            thumbnailIDs[i] = imageCursor.getInt(image_column_index);//sets the id of each image thumbnails
            int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            arrPath[i] = imageCursor.getString(dataColumnIndex);
        }
        galleryAdapter = new ImageGalleryAdapter(this, arrPath);
        initUI(view);
        imageCursor.close();
    }


    @Override
    public void selectImage(String imageUri) {
        activity.getFragmentSwitcher().showAddPost(imageUri);
    }
}