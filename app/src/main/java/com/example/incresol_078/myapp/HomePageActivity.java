package com.example.incresol_078.myapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.provider.Settings;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Incresol-078 on 30-05-2016.
 */
public class HomePageActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public final static String EXTRA_MESSAGE = "com.example.incresol_078.MESSAGE";
    public static String user_email;
    private static final String TAG=HomePageActivity.class.getSimpleName();
    public static final String LoginCredentialsPreferences="LoginData";
    public static final String FirstNameKey="firstnamekey";
    public static final String ProviderKey="providerkey";
    public static boolean isDndEnabled=false;
    MyReceiver mReceiver;
    IntentFilter filter;
    SharedPreferences sharedPreferences_login;
    TextView textView_username;
    ToggleButton toggleButton;
    /*android.hardware.camera2.CameraDevice cam;*/
    android.hardware.Camera camera;


    //google data
    private GoogleApiClient mGoogleApiClient;
    Intent intent;
    String provider,userEmail,displayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        textView_username=(TextView) findViewById(R.id.textView_username);

        /*Intent intent=getIntent();
       user_email= intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.i(TAG,"email "+user_email);*/

        //This below 4 lines are the code for power button functionality of an android phone
        filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new MyReceiver (this);

        toggleButton = (ToggleButton) findViewById(R.id.onOffFlashlight);

//google related data

        intent=getIntent();
        sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences, Context.MODE_PRIVATE);
        provider=sharedPreferences_login.getString(ProviderKey,"");
       // provider=intent.getStringExtra("provider");

       Log.i("provider=======*****=>",provider);

        if (provider.equals("google")){
            displayName=sharedPreferences_login.getString(FirstNameKey,"");
            textView_username.setText("  "+displayName);
        }else if (provider.equals("myapplogin")){

            String username= sharedPreferences_login.getString(FirstNameKey,"");
            textView_username.setText(username);
        }else if (provider.equals("facebook")){
           // userEmail = intent.getStringExtra("facebook_email");
            displayName=intent.getStringExtra("facebook_name");
            textView_username.setText("  "+displayName);
        }


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();




    }





    public void buttonTest(View view){
       Intent intent_button=new Intent(this, ButtonsTest.class);
        startActivity(intent_button);
    }
    public void buttonMyProfile(View view){
        Intent intent_myProfile=new Intent(this, MyProfileActivity.class);
        startActivity(intent_myProfile);
    }

    public void buttonMoveFile(View view){
        Intent intent_myProfile=new Intent(this, MoveFileActivity.class);
        startActivity(intent_myProfile);
    }

    public void buttonFetchContact(View view){
        Intent intent_myProfile=new Intent(this, FetchContactActivity.class);
        startActivity(intent_myProfile);
    }

    public void buttonSendSMS(View view){
        Intent intent_myProfile=new Intent(this, SendSMSActivity.class);
        startActivity(intent_myProfile);
    }

    public void buttonSetAlarm(View view){
        Intent intent_myProfile=new Intent(this, SetAlarmActivity.class);
        startActivity(intent_myProfile);
    }

    public void button_stopWatch(View view){
        Intent intent_myProfile=new Intent(this, StopWatchActivity.class);
        startActivity(intent_myProfile);
    }

    public void button_view(View view){
        Intent intent_view=new Intent(this, ViewActivity.class);
        startActivity(intent_view);
    }
    public void button_listView(View view){
        Intent intent_view=new Intent(this, ListViewActivity.class);
        startActivity(intent_view);
    }
    public void onToggleClicked(View view){

        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){

Log.i("in before is checked===","in");
            if (((ToggleButton)view).isChecked()) {

                if (Build.VERSION.SDK_INT >= 23) {
                    //below code for version 23

                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    // Usually front camera is at 0 position.
                    try {
                        String cameraId = camManager.getCameraIdList()[0];
                        camManager.setTorchMode(cameraId, true);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }

                } else {
                    camera = android.hardware.Camera.open();
                    android.hardware.Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters(parameters);
                    camera.startPreview();
                }

            } else {

                if (Build.VERSION.SDK_INT >= 23) {
                    //below code for version 23

                    CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    // Usually front camera is at 0 position.
                    try {
                        String cameraId = camManager.getCameraIdList()[0];
                        camManager.setTorchMode(cameraId, false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                } else {

                    camera = android.hardware.Camera.open();
                    android.hardware.Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters(parameters);
                    camera.stopPreview();
                    camera.release();
                }
            }

        }else{
            Log.i("in else===","in");
            toggleButton.setChecked(false);
            Toast.makeText(getApplicationContext(), "Your device doesn't support flash light", Toast.LENGTH_SHORT).show();
        }

    }



   /* public void buttonDnd(View view){
        Log.i(TAG,"in dnd");
        Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        startActivity(intent);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
Intent intent_item;
        switch (item.getItemId())
        {
            case R.id.action_logout:

                signOut();
                // User chose the "Settings" item, show the app settings UI...

                return true;

            case R.id.action_settings:
                intent_item = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(intent_item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This is Back button functionality of an android device.
     */
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);

    }

    @Override
    protected void onResume() {
        registerReceiver(mReceiver, filter);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }


    private void signOut() {
        if (provider.equals("google")) {
            mGoogleApiClient.connect();
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Log.i("status -> ", "" + status.getStatusMessage());
                            if (status.getStatusCode() == 0) {
                                Intent intent_google = new Intent(getApplicationContext(), MainActivity.class);
                                sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor_login=sharedPreferences_login.edit();
                                editor_login.clear();
                                editor_login.commit();
                                startActivity(intent_google);
                                HomePageActivity.this.finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Issues with the connections", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else if(provider.equals("myapplogin")){
            String logoutMessage="you are logged out";
         Intent intent_item = new Intent(this, MainActivity.class);
            /**/
            intent_item.putExtra(EXTRA_MESSAGE,logoutMessage);
            sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_login=sharedPreferences_login.edit();
            editor_login.clear();
            editor_login.commit();
            startActivity(intent_item);
            HomePageActivity.this.finish();
        }else if (provider.equals("facebook")){
            LoginManager.getInstance().logOut();
            Log.i("Facebook","logged out");
            Intent intent_facebook=new Intent(getApplicationContext(),MainActivity.class);
            sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor_login=sharedPreferences_login.edit();
            editor_login.clear();
            editor_login.commit();
            startActivity(intent_facebook);
            HomePageActivity.this.finish();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    // [END signOut]


}
