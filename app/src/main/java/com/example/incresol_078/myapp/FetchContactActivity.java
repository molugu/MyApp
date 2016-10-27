package com.example.incresol_078.myapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class FetchContactActivity extends AppCompatActivity {

    private static final String TAG = FetchContactActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;
    private TextView contact_Name,contact_Number,contact_Email,contactName_1,contactNumber_1,contactEmail_1;
    ImageView imageView;
    EditText editText_SMSContent,editText_PhoneNumber,editText_AreaName;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_contact);
        contact_Name=(TextView)findViewById(R.id.contactName);
        contact_Number=(TextView)findViewById(R.id.contactNumber);
        contact_Email=(TextView)findViewById(R.id.contactEmail);
        imageView = (ImageView) findViewById(R.id.img_contact);

        if(!checkContactsPermission()){
    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("requestCode -> "+requestCode,"permissions -> "+permissions);
        if(requestCode == 1000 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
        }

    }

    public boolean checkContactsPermission(){
        boolean check;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED )) {
//            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
            check = false;
        }else{
            check =true;
        }
        return check;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onClickSelectContact(View btnSelectContact) {
        if(!checkContactsPermission()){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1000);
        }else{
        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
        imageView.setImageBitmap(null);
        contact_Name.setText("");
        contact_Number.setText("");
        contact_Email.setText("");
        (findViewById(R.id.contactEmail_1)).setVisibility(View.INVISIBLE);
        (findViewById(R.id.contactNumber_1)).setVisibility(View.INVISIBLE);
        (findViewById(R.id.contactName_1)).setVisibility(View.INVISIBLE);
        (findViewById(R.id.errorImgMsg)).setVisibility(View.INVISIBLE);
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
    }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
Log.i("requestCode -> "+requestCode,"resultCode -> "+resultCode);
        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactName();
            retrieveContactNumber();
            retrieveContactPhoto();
            retrieveContactEmail();
        }
    }


    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            contact_Name.setText(contactName);
            (findViewById(R.id.contactName_1)).setVisibility(View.VISIBLE);

        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contact_Number.setText(contactNumber);
            (findViewById(R.id.contactNumber_1)).setVisibility(View.VISIBLE);
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);
    }

    private void retrieveContactEmail(){

        String contactEmail=null;
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Cursor cur1 = getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{contactID}, null);
        while (cur1.moveToNext()) {
            //to get the contact email
            String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            Log.e("Email", email);
            contact_Email.setText(email);
            (findViewById(R.id.contactEmail_1)).setVisibility(View.VISIBLE);
        }
        cur1.close();

    }


    private void retrieveContactPhoto() {

        Bitmap photo = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(contactID)));

            if (inputStream != null) {
                imageView.setVisibility(View.VISIBLE);
                photo = BitmapFactory.decodeStream(inputStream);
                Log.i("in if of retrieve photo","p"+photo);
                imageView = (ImageView) findViewById(R.id.img_contact);
                imageView.setImageBitmap(photo);
                assert inputStream != null;
                inputStream.close();
            }else{
                Log.i("Photo", "in else of retrieve photo");
                (findViewById(R.id.errorImgMsg)).setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
