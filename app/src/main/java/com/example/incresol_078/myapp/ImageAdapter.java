package com.example.incresol_078.myapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Incresol-078 on 21-06-2016.
 */
public class ImageAdapter extends BaseAdapter {

    Context mContext;

    public ImageAdapter(Context context){
        mContext=context;
    }


    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView==null){
            imageView=new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200,200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        }else{
            imageView=(ImageView)convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }



    public Integer[] mThumbIds={
            R.drawable.freepik,R.drawable.image_01,R.drawable.image_02,
            R.drawable.image_03,R.drawable.setting_jpg,R.drawable.settings,
            R.drawable.settings_2,R.drawable.background,R.drawable.sociocons_1024x748
    };
}
