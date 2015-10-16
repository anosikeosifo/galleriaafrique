package com.galleriafrique.view.holders;

import android.view.View;
import android.widget.ImageView;

import com.galleriafrique.R;

/**
 * Created by osifo on 9/23/15.
 */
public class ImageGalleryHolder {
    public int id;
    public ImageView image;

    public ImageGalleryHolder(View view) {
        this.image = (ImageView) view.findViewById(R.id.gallery_item);
    }
}
