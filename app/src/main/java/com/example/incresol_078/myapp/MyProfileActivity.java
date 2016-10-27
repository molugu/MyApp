package com.example.incresol_078.myapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Incresol-078 on 30-05-2016.
 */
public class MyProfileActivity extends AppCompatActivity {


    public static final String RegisterPreferences="RegistrationData";
    public static final String LoginCredentialsPreferences="LoginData";
    public static final String FirstName="firstnamekey";
    public static final String LastName="lastnamekey";
    public static final String Email="emailkey";
    public static final String MobileNumber="mobileNumberkey";
    public static final String Photo="photoKey";
    private static final int IMAGE_PICK = 1;
    private static final int CAMERA_REQUEST = 0;
    private final int RESULT_CROP = 400;
    ImageView imageView_myPicture;
    EditText editText_firstName,editText_lastName,editText_email,editText_mobileNumber;
    int flag;
    SharedPreferences sharedPreferences,sharedPreferences_login;
    private static final String TAG=MyProfileActivity.class.getSimpleName();

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        editText_firstName=(EditText)findViewById(R.id.editText_firstname);
        editText_lastName =(EditText)findViewById(R.id.editText_lastname);
        editText_email=(EditText)findViewById(R.id.editText_email);
        editText_mobileNumber=(EditText)findViewById(R.id.editText_mobileNumber);
        imageView_myPicture=(ImageView)findViewById(R.id.imageView_myPicture);

        sharedPreferences=getSharedPreferences(RegisterPreferences, Context.MODE_PRIVATE);
        sharedPreferences_login=getSharedPreferences(LoginCredentialsPreferences,Context.MODE_PRIVATE);
        String user_email=sharedPreferences_login.getString(Email,"");
        int number=sharedPreferences.getInt("Number",0);
        for(int i=1;i<=number;i++){
            String val = sharedPreferences.getString(Email+"_"+i,"");
            if(val.equals(user_email)){
                flag=i;
                String firstName=sharedPreferences.getString(FirstName+"_"+i,"");

                String lastName=sharedPreferences.getString(LastName+"_"+i,"");
                String mobileNumber=sharedPreferences.getString(MobileNumber+"_"+i,"");
                String photo=sharedPreferences.getString(Photo+"_"+i,"");
                editText_firstName.setText(firstName);
                editText_lastName.setText(lastName);
                editText_email.setText(user_email);
                editText_mobileNumber.setText(mobileNumber);
                imageView_myPicture.setImageBitmap(StringToBitMap(photo));

            }
        }

        Button button_myPicture=(Button)findViewById(R.id.button_myPicture);
        button_myPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkCameraPermission()) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                } else {
                    alertDialogDisplay();
                }
            }
        });

        final Button button_update=(Button)findViewById(R.id.button_update);
        assert button_update != null;
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText_firstName.getText().toString().trim().length()>4){
                    if(editText_lastName.getText().toString().trim().length()>2){
                        if(checkEmailAddress(editText_email.getText().toString())){
                            if(editText_mobileNumber.getText().toString().length()>=10){

                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putString(FirstName+"_"+flag,editText_firstName.getText().toString());
                                editor.putString(LastName+"_"+flag,editText_lastName.getText().toString());
                                editor.putString(Email+"_"+flag,editText_email.getText().toString());
                                editor.putString(MobileNumber+"_"+flag,editText_mobileNumber.getText().toString());
                                editor.commit();
                                Toast.makeText(getApplicationContext(),"Successfully updated",Toast.LENGTH_SHORT).show();

                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(button_update.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                            }else{
                                Toast.makeText(getApplicationContext(),"MobileNumber should have min 10 numerical",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(),"Email format is not correct",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Last Name should be min 3 characters",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"First Name should be min 5 characters",Toast.LENGTH_SHORT).show();
                }




            }
        });

        if(!checkCameraPermission()){
            requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("requestCode ==>"+requestCode,"permissions ==>"+permissions);
if(requestCode==1000&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
    alertDialogDisplay();
}
    }

    public boolean checkCameraPermission(){
        boolean check;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M &&(checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED )){
            check=false;
        }else{
            check=true;
        }
        return check;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        switch(requestCode)
        {
            case CAMERA_REQUEST:
                if(resultCode == RESULT_OK)
                {

                    String[] projection = { MediaStore.Images.Media.DATA };
                    Cursor cursor=getContentResolver().query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection , null, null, null);

                    cursor.moveToLast();
                    int column_index_data= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    String imagePath = cursor.getString(column_index_data);
                    performCrop(imagePath);



                }
                break ;

            case IMAGE_PICK:
                if(resultCode == RESULT_OK)
                {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    System.out.println("filePath -> "+filePath);
                    Log.i("filePath",filePath);
                    cursor.close();

                    performCrop(filePath);
                }
            break;
            case RESULT_CROP:
                if(resultCode == RESULT_OK)
                {
                    Log.i(TAG,"in result crop");
                    Bundle extras = data.getExtras();
                    if(extras != null) {
                        Bitmap photo_gallery = extras.getParcelable("data");
                        imageView_myPicture.setImageBitmap(photo_gallery);
                        imageView_myPicture.setScaleType(ImageView.ScaleType.FIT_XY);
                        MediaStore.Images.Media.insertImage(getContentResolver(), photo_gallery,"","");
                        editor.putString(Photo + "_" + flag, BitMapToString(photo_gallery));
                    }
                }
                break;

        }
        editor.commit();
    }
/*public void launchCrop(Uri picUri){
    Intent cropIntent = new Intent("com.android.camera.action.CROP");
    //indicate image type and Uri
    cropIntent.setDataAndType(picUri, "image*//*");
    //set crop properties
    cropIntent.putExtra("crop", "true");
    //indicate aspect of desired crop
    cropIntent.putExtra("aspectX", 1);
    cropIntent.putExtra("aspectY", 1);
    //indicate output X and Y
    cropIntent.putExtra("outputX", 256);
    cropIntent.putExtra("outputY", 256);
    //retrieve data on return
    cropIntent.putExtra("return-data", true);
    //start the activity - we handle returning in onActivityResult
    startActivityForResult(cropIntent, RESULT_CROP);
}*/

    public Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public static String BitMapToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte[] arr = baos.toByteArray();
        return Base64.encodeToString(arr, Base64.DEFAULT);
    }


    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void alertDialogDisplay(){
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_REQUEST);
                } else if (items[item].equals("Choose from Library")) {
                    Intent gallery_intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    gallery_intent.setType("image/*");
                    startActivityForResult(gallery_intent, IMAGE_PICK);


                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public boolean checkEmailAddress(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
