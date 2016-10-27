package com.example.incresol_078.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by Incresol-078 on 30-05-2016.
 */
public class SplashScreenActivity extends AppCompatActivity {
    public static final String LoginCredentialsPreferences="LoginData";

    public static final String IsLoggedIn="IsLoggedIn";
    SharedPreferences  sharedPreferences_login;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLoggedIn()){
                    Intent homePageIntent = new Intent(SplashScreenActivity.this, HomePageActivity.class);
                    SplashScreenActivity.this.startActivity(homePageIntent);
                    SplashScreenActivity.this.finish();
                }else{
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();}
            }
        },SPLASH_DISPLAY_LENGTH);
    }
    public boolean isLoggedIn(){
        sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences, Context.MODE_PRIVATE);
        return sharedPreferences_login.getBoolean(IsLoggedIn, false);
    }
}
