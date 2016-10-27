package com.example.incresol_078.myapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Incresol-078 on 16-06-2016.
 */
public class SendSMSActivity extends AppCompatActivity {

    EditText editText_SMSContent, editText_PhoneNumber;
    String message,phoneNumber;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsms);
        editText_SMSContent = (EditText) findViewById(R.id.editText_SMSContent);
        editText_PhoneNumber = (EditText) findViewById(R.id.editText_PhoneNumber);

        if(!checkSendSMSPermission()){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},100);
        }
    }

    public boolean checkSendSMSPermission(){
        boolean check;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED)){
            check=false;
        }else{
            check=true;
        }
        return check;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            message = editText_SMSContent.getText().toString();
            phoneNumber = editText_PhoneNumber.getText().toString();
            sendSMSNow(message,phoneNumber);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void sendSMS(View view) {
        message = editText_SMSContent.getText().toString();
        phoneNumber = editText_PhoneNumber.getText().toString();
        if ((!message.equals("")) && (!phoneNumber.equals(""))) {
            if(!checkSendSMSPermission()){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},1000);
            }else {
            sendSMSNow(message,phoneNumber);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Enter Mobile Number and Messgae",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void sendSMSNow(String message,String phoneNumber){

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(getApplicationContext(), "Message Sent",
                        Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Check mobile data or check your SIM  availability",
                        Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }



    }
}