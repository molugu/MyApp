package com.example.incresol_078.myapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Incresol-078 on 21-06-2016.
 */
public class CustomList extends ArrayAdapter<Integer> {
    private Integer[] imageid;
    private Activity context;
    public CustomList(Activity context,Integer[] imageid){
        super(context, R.layout.activity_list_layout, imageid);
        this.context = context;
        this.imageid = imageid;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_list_layout, null, true);

        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);
        image.setImageResource(imageid[position]);
        return  listViewItem;
    }
}
