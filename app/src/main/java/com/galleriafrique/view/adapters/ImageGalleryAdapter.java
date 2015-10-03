package com.galleriafrique.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.galleriafrique.R;
import com.galleriafrique.controller.fragment.base.BaseFragment;
import com.galleriafrique.model.comment.Comment;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.view.holders.ImageGalleryHolder;

/**
 * Created by osifo on 9/23/15.
 */
public class ImageGalleryAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private int imageCount;
    private String[] imageUris;
    private ImageGalleryAdapterListener imageGalleryAdapterListener;
    private String[] arrPath;

    public ImageGalleryAdapter(BaseFragment fragment, String[] imageUris ) {
        this.imageUris = imageUris;
        this.imageCount = imageUris.length;
        this.context = fragment.getActivity();
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageGalleryAdapterListener = (ImageGalleryAdapterListener)fragment;
    }

    @Override
    public int getCount() {
        return imageCount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageGalleryHolder holder;

        if (view == null) {
            view = inflater.inflate(R.layout.image_gallery_item, null);
            holder = new ImageGalleryHolder(view);
            view.setTag(holder);
        } else {
            holder = (ImageGalleryHolder)view.getTag();
        }

        setContent(holder, position);
        setListeners(holder);
        return view;
    }


    private void setContent(ImageGalleryHolder holder, int position) {
        Glide.with(context).load(imageUris[position]).centerCrop().into(holder.image);

        holder.image.setId(position);
        holder.image.setPadding(2, 2, 2, 2);
        holder.id = position;
    }

    private void setListeners(ImageGalleryHolder holder ) {
        holder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                imageGalleryAdapterListener.selectImage(imageUris[view.getId()]);
            }
        });
    }

    public interface ImageGalleryAdapterListener {
        void selectImage(String imageUri);
    }
}
