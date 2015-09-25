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
    private ImageGalleryAdapterListener imageGalleryAdapterListener;

    public ImageGalleryAdapter(BaseFragment fragment) {
        this.context = fragment.getActivity();
        this.inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageGalleryAdapterListener = (ImageGalleryAdapterListener)fragment;
    }

    @Override
    public int getCount() {
        return 6;
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

        try {
            setBitmap(holder.image, position);
        } catch(Throwable e) {
            CommonUtils.toast(context, "Images couldn't be loaded, Please retry.");
        }

        return view;
    }

    public void setBitmap(final ImageView imageview, final int id) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                return MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, null);
            }

            protected void onPostExecute(Bitmap result){
                super.onPostExecute(result);
                imageview.setImageBitmap(result);
            }
        }.execute();
    }

    private void setContent(ImageGalleryHolder holder, int position) {
        holder.image.setId(position);
        holder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.image.setPadding(2, 2, 2, 2);
        holder.id = position;
    }

    private void setListeners(ImageGalleryHolder holder ) {
        holder.image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int viewID = view.getId();
                //Uri imagePath = Uri.parse("file://" + arrPath[id]);
                imageGalleryAdapterListener.selectImage(viewID);
            }
        });
    }

    public interface ImageGalleryAdapterListener {
        void selectImage(int id);
    }
}
