package com.example.incresol_078.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    EditText editText_email;
    EditText editText_password;
    Intent intent;
    public final static String EXTRA_MESSAGE = "com.example.incresol_078.MESSAGE";
    private static final String TAG=MainActivity.class.getSimpleName();
    SharedPreferences sharedPreferences_login,sharedPreferences;
    public static final String RegisterPreferences="RegistrationData";
    public static final String LoginCredentialsPreferences="LoginData";
    public static final String IsLoggedIn="IsLoggedIn";
    public static final String Email="emailkey";
    public static final String PasswordKey="passwordkey";
    public static final String FirstNameKey="firstnamekey";
    public static final String ProviderKey="providerkey";
    public String firstname;
public String provider;

    //google data below

    public static GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    String email,name;

//facebook variables
    LoginButton facebookButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_main);
        facebookButton = (LoginButton)findViewById(R.id.login_button);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //facebook related data below





        facebookButton.setReadPermissions(Arrays
                .asList("public_profile, email, user_birthday, user_friends"));
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

               // new fblogin().execute(loginResult.getAccessToken());
                Intent intent_button=new Intent(getApplicationContext(), HomePageActivity.class);
               /* intent_button.putExtra("facebook_email", email);
                intent_button.putExtra("facebook_name", name);
                intent_button.putExtra("provider","facebook");*/
                provider="facebook";
                createLoginSession(provider);
                startActivity(intent_button);

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                System.out.println("exception -> "+exception);
                Toast.makeText(MainActivity.this, "Some error has occured", Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editText_email=(EditText) findViewById(R.id.editText_email);
        editText_password= (EditText) findViewById(R.id.editText_password);
        intent=getIntent();
        String logoutMessage= intent.getStringExtra(ContentMain.EXTRA_MESSAGE);
        String registeredMsg=intent.getStringExtra("registeredMsg");
        if(registeredMsg!=null){
            Toast.makeText(getApplicationContext(),registeredMsg,Toast.LENGTH_SHORT).show();
        }
        if(logoutMessage!=null){
        Toast.makeText(getApplicationContext(),logoutMessage,Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkUserExist(int number,String email,String passwordKey){
        Log.i(TAG,"username"+email+"\tpassword"+passwordKey+"\tnumber"+number);

        for(int i=1;i<=number;i++){
            String val = sharedPreferences.getString(Email+"_"+i,"");

            String val1 = sharedPreferences.getString(PasswordKey+"_"+i,"");

            if(val.equalsIgnoreCase(email)&& val1.equalsIgnoreCase(passwordKey) ){
             firstname= sharedPreferences.getString(FirstNameKey+"_"+i,"");
                return true;
            }
        }
        return false;
    }

    /** Called when the user clicks the Login button */
    public void login(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        Log.i(TAG, "In login method");
        String email=editText_email.getText().toString().trim();
        String password=editText_password.getText().toString().trim();
        sharedPreferences=getSharedPreferences(RegisterPreferences, Context.MODE_PRIVATE);
        int number = sharedPreferences.getInt("Number",0);
        boolean check = checkUserExist(number,email,password);

            if (check) {

                intent = new Intent(getApplicationContext(), HomePageActivity.class);
                String myapplogin="myapplogin";
               // intent.putExtra("provider",myapplogin);
                createLoginSession(email,password,firstname,myapplogin);
                startActivity(intent);
                finish();
            } else {
               Toast.makeText(getApplicationContext(), "Invalid Details", Toast.LENGTH_SHORT).show();
            }
        }

    public void registration(View view){
        intent=new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void createLoginSession(String email,String password,String firstname,String provider){
        sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_login=sharedPreferences_login.edit();
        editor_login.putBoolean(IsLoggedIn,true);
        editor_login.putString(Email,email);
        editor_login.putString(PasswordKey,password);
        editor_login.putString(FirstNameKey,firstname);
        editor_login.putString(ProviderKey,provider);
        editor_login.commit();
    }

    //google related methods and data below

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.i(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String user_email=acct.getEmail();
            String displayName=acct.getDisplayName();
            Intent homepage_intent=new Intent(getApplicationContext(), HomePageActivity.class);
          /*  homepage_intent.putExtra("google_userEmail",user_email);
            homepage_intent.putExtra("google_displayName",displayName);
            homepage_intent.putExtra("provider","google");*/
            provider="google";
            createLoginSession(user_email,displayName,provider);
            startActivity(homepage_intent);
           /* updateUI(true);*/
        } else {

           Toast.makeText(getApplicationContext(),"Login Cancelled",Toast.LENGTH_SHORT).show();
            // Signed out, show unauthenticated UI.
          //  updateUI(false);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Toast.makeText(getApplicationContext(),"Connection failed",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    public void createLoginSession(String email,String firstname,String provider){
        sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_login=sharedPreferences_login.edit();
        editor_login.putBoolean(IsLoggedIn,true);
        editor_login.putString(Email,email);
        editor_login.putString(FirstNameKey,firstname);
        editor_login.putString(ProviderKey,provider);
        editor_login.commit();
    }
//end of google related data

    //facebook related method
public void createLoginSession(String provider){
    sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor_login=sharedPreferences_login.edit();
    editor_login.putBoolean(IsLoggedIn,true);
    editor_login.putString(ProviderKey,provider);
    editor_login.commit();
}//end of facebook related method
}
