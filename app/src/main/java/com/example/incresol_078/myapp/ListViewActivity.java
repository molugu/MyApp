package com.example.incresol_078.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

/**
 * Created by Incresol-078 on 21-06-2016.
 */
public class ListViewActivity extends AppCompatActivity{

    private ListView listView;
    private Integer imageId[]={
            R.drawable.freepik,R.drawable.image_01,R.drawable.image_02,
            R.drawable.image_03,R.drawable.setting_jpg,R.drawable.settings,
            R.drawable.settings_2,R.drawable.background,R.drawable.sociocons_1024x748
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        CustomList customList = new CustomList(this,imageId);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(customList);


    }

}
