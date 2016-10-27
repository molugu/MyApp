package com.example.incresol_078.myapp;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Incresol-078 on 27-05-2016.
 */
public class ButtonsTest extends AppCompatActivity implements View.OnClickListener{

    Button button_first,button_second,button_third,button_fourth,button_fifth;
/*View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.activity_buttonstest,container,false);
        button_first=(Button)myView.findViewById(R.id.button_first);
        button_second=(Button)myView.findViewById(R.id.button_second);
        button_third=(Button)myView.findViewById(R.id.button_third);
        button_fourth=(Button)myView.findViewById(R.id.button_fourth);
        button_fifth=(Button)myView.findViewById(R.id.button_fifth);

        button_first.setOnClickListener(this);
        button_second.setOnClickListener(this);
        button_third.setOnClickListener(this);
        button_fourth.setOnClickListener(this);
        button_fifth.setOnClickListener(this);
        return myView;

    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttonstest);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        button_first=(Button)findViewById(R.id.button_first);
        button_second=(Button)findViewById(R.id.button_second);
        button_third=(Button)findViewById(R.id.button_third);
        button_fourth=(Button)findViewById(R.id.button_fourth);
        button_fifth=(Button)findViewById(R.id.button_fifth);

        button_first.setOnClickListener(this);
        button_second.setOnClickListener(this);
        button_third.setOnClickListener(this);
        button_fourth.setOnClickListener(this);
        button_fifth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Handler handler;
        final Toast toast;
        switch (v.getId()){
            case R.id.button_first:
                button_first.setVisibility(View.INVISIBLE);
                button_second.setVisibility(View.VISIBLE);
                toast =   Toast.makeText(getApplicationContext(),"First button pressed",Toast.LENGTH_SHORT);
                toast.show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
                break;
            case R.id.button_second:
                button_second.setVisibility(View.INVISIBLE);
                button_third.setVisibility(View.VISIBLE);
                toast=Toast.makeText(getApplicationContext(),"Second button pressed",Toast.LENGTH_SHORT);
                toast.show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
                break;
            case R.id.button_third:
                button_third.setVisibility(View.INVISIBLE);
                button_fourth.setVisibility(View.VISIBLE);
                toast=Toast.makeText(getApplicationContext(),"Third pressed",Toast.LENGTH_SHORT);
                toast.show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
                break;
            case R.id.button_fourth:
                button_fourth.setVisibility(View.INVISIBLE);
                button_fifth.setVisibility(View.VISIBLE);
                toast= Toast.makeText(getApplicationContext(),"Fourth button pressed",Toast.LENGTH_SHORT);
                toast.show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
                break;
            case R.id.button_fifth:
                button_fifth.setVisibility(View.INVISIBLE);
                button_first.setVisibility(View.VISIBLE);
                toast=Toast.makeText(getApplicationContext(),"Fifth button pressed",Toast.LENGTH_SHORT);
                toast.show();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 500);
                break;
        }
    }
}
