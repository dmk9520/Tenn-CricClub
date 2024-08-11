package com.example.jeet.tenncricclub;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

class ImageListAdapter extends BaseAdapter {
    List<String> mList;
    Context c;
    private ImageLoader mImageLoader;

    public ImageListAdapter(Context c, List<String> mList) {
        this.c = c;
        this.mList = mList;
        CustomVolleyRequestQueue req = CustomVolleyRequestQueue.getInstance(c);
        mImageLoader = req.getImageLoader();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(c, R.layout.gallery_model, null);
        NetworkImageView img = (NetworkImageView) v.findViewById(R.id.niv);
        String url = mList.get(position);
        mImageLoader.get(url, ImageLoader.getImageListener(img, R.drawable.white, R.drawable.defaultprofile));
        img.setImageUrl(url, mImageLoader);
        return v;
    }
}
