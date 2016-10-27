package com.example.incresol_078.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Incresol-078 on 26-05-2016.
 */
public class RegistrationActivity extends AppCompatActivity {
Intent intent_logout,intent;
    EditText editText_firstname,editText_lastname,editText_email,editText_mobileNumber,editText_password,editText_confirmPassword;
    Button button_register;
    CheckBox checkBox;
    public static final String RegisterPreferences="RegistrationData";
    public static final String FirstName="firstnamekey";
    public static final String LastName="lastnamekey";
    public static final String Email="emailkey";
    public static final String MobileNumber="mobileNumberkey";
    public static final String Password="passwordkey";
    public static final String ConfirmPassword="confirmpasswordkey";
    public static final String RegistrationFailed="This email is already Registered";
    SharedPreferences sharedPreferences;
    TextView textView_privacy,textView_terms;

    public boolean checkEmailExist(int number,String emailCheck){
        for(int i=1;i<=number;i++){
            String val = sharedPreferences.getString(Email+"_"+i,"");
            if(val.equalsIgnoreCase(emailCheck)){

                return true;
            }
        }
        return false;
    }

    public boolean validateregistration(){
        boolean valid = false;
        if(editText_firstname.getText().toString().trim().length() > 4){
            if(editText_lastname.getText().toString().trim().length() > 2){
                if(checkEmail(editText_email.getText().toString())){
                    if(editText_mobileNumber.getText().toString().trim().length() > 9){
                        if(editText_password.getText().toString().trim().length() > 7) {
                            if (editText_confirmPassword.getText().toString().trim().length() > 7) {
                                if(editText_password.getText().toString().equals(editText_confirmPassword.getText().toString())) {
                                    if (checkBox.isChecked()) {
                                    valid = true;
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Check the Privacy Policy", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "Password and Confirm Password doesn't match", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"Confirm Password should be min 8 characters!",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),"Password should be min 8 characters!",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(),"MobileNumber should have min 10 numericals ",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Email format is not correct!",Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(getApplicationContext(),"Last Name should be min 3 characters!",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(),"First Name should be min 5 characters!",Toast.LENGTH_SHORT).show();
        }
        return valid;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editText_firstname=(EditText)findViewById(R.id.editText_firstname);
        editText_lastname=(EditText)findViewById(R.id.editText_lastname);
        editText_email=(EditText)findViewById(R.id.editText_email);
        editText_password=(EditText)findViewById(R.id.editText_password);
        editText_confirmPassword=(EditText)findViewById(R.id.editText_confirmPassword);
        editText_mobileNumber=(EditText)findViewById(R.id.editText_mobileNumber);
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        button_register=(Button)findViewById(R.id.button_register);
        sharedPreferences=getSharedPreferences(RegisterPreferences, Context.MODE_PRIVATE);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                int number = sharedPreferences.getInt("Number",0);
                String firstname=editText_firstname.getText().toString();
                String lastname=editText_lastname.getText().toString();
                String email=editText_email.getText().toString();
                String mobileNumber=editText_mobileNumber.getText().toString();
                String password=editText_password.getText().toString();
                String confirmPassword=editText_confirmPassword.getText().toString();
                Boolean checkbox=checkBox.isChecked();

                if(validateregistration()){

                if(number == 0){

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(FirstName+"_"+1,firstname);
                    editor.putString(LastName+"_"+1,lastname);
                    editor.putString(Email+"_"+1,email);
                    editor.putString(MobileNumber+"_"+1,mobileNumber);
                    editor.putString(Password+"_"+1,password);
                    editor.putString(ConfirmPassword+"_"+1,confirmPassword);
                    editor.putInt("Number",1);
                    editor.commit();

                   /* editText_firstname.setText("");
                    editText_lastname.setText("");
                    editText_email.setText("");
                    editText_mobileNumber.setText("");
                    editText_password.setText("");
                    checkBox.setChecked(false);

               Toast.makeText(getApplicationContext(),"Thank you for registering",Toast.LENGTH_SHORT).show();*/

                    Intent main=new Intent(getApplicationContext(),MainActivity.class);
                    String registered="Thank you for registering";
                    main.putExtra("registeredMsg",registered);
                    startActivity(main);
                    finish();

                }

                else{

                    boolean check = checkEmailExist(number,email);

                    if(check){
                        Toast.makeText(getApplicationContext(),RegistrationFailed,Toast.LENGTH_SHORT).show();

                    }else{
                        number  = number+1;
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(FirstName+"_"+number,firstname);
                        editor.putString(LastName+"_"+number,lastname);
                        editor.putString(Email+"_"+number,email);
                        editor.putString(MobileNumber+"_"+number,mobileNumber);
                        editor.putString(Password+"_"+number,password);
                        editor.putString(ConfirmPassword+"_"+1,confirmPassword);
                        editor.putInt("Number",number);
                        editor.commit();

                        /*editText_firstname.setText("");
                        editText_lastname.setText("");
                        editText_email.setText("");
                        editText_mobileNumber.setText("");
                        editText_password.setText("");
                        checkBox.setChecked(false);

                        Toast.makeText(getApplicationContext(),"Thank you for registering",Toast.LENGTH_SHORT).show();*/

                        Intent main=new Intent(getApplicationContext(),MainActivity.class);
                        String registered="Thank you for registering";
                        main.putExtra("registeredMsg",registered);
                        startActivity(main);
                        finish();
                    }
                }}else{
//                    Toast.makeText(getApplicationContext(),"Please enter all the credentials",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void loginActivity(View view){
        intent_logout=new Intent(this,MainActivity.class);
        startActivity(intent_logout);
    }

    public void linkClicked(View view){

        switch (view.getId()){
            case R.id.textView_terms:
            intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(intent);
                break;

            case R.id.textView_privacy:
               intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.incresol.com"));
                startActivity(intent);
                break;


        }
    }

    private boolean checkEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
