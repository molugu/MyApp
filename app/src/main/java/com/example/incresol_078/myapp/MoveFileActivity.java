package com.example.incresol_078.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by Incresol-078 on 06-06-2016.
 */
public class MoveFileActivity extends AppCompatActivity {
Button button_chooseFile,button_chooseFolder,button_moveFolder;
    TextView textView_sourcePath;
    public String sourcePath,destinationPath;
    private static final int CHOOSE_FILE_REQUESTCODE = 0;
    private static final String TAG=MoveFileActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movefile);
        button_chooseFile=(Button) findViewById(R.id.button_chooseFile);
        button_chooseFolder=(Button) findViewById(R.id.button_chooseFolder);
      //  button_moveFolder=(Button)findViewById(R.id.button_moveFile);
        textView_sourcePath=(TextView) findViewById(R.id.textView_sourcePath);
        button_chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_FILE_REQUESTCODE);

            }
        });

        button_chooseFolder.setOnClickListener(new View.OnClickListener() {
            private String m_chosenDir = "";
            private boolean m_newFolderEnabled = true;

            @Override
            public void onClick(View v)
            {
                // Create DirectoryChooserDialog and register a callback
                DirectoryChooserDialog directoryChooserDialog =
                        new DirectoryChooserDialog(MoveFileActivity.this,
                                new DirectoryChooserDialog.ChosenDirectoryListener()
                                {
                                    @Override
                                    public void onChosenDir(String chosenDir) {
                                        m_chosenDir = chosenDir;
                                        /*Toast.makeText(
                                                MoveFileActivity.this, "Chosen directory: " +
                                                        chosenDir, Toast.LENGTH_LONG).show();*/
                                        Log.i(TAG,m_chosenDir+"    "+sourcePath);
                                       // try {
                                        String sdCard = Environment.getExternalStorageDirectory().toString();
                                        // the file to be moved or copied
                                        File sourceLocation = new File (sdCard + sourcePath);
                                        // make sure your target location folder exists!
                                        File targetLocation = new File (sdCard + m_chosenDir);
                                        // just to take note of the location sources
                                        Log.i(TAG, "sourceLocation: " + sourceLocation);
                                        Log.i(TAG, "targetLocation: " + targetLocation);

                                       if(sourceLocation.renameTo(targetLocation))
                                        {
                                            Log.i("Moving", "Moving file successful.");
                                            Toast.makeText(getApplicationContext(), "Check the copy of the image in the same path as the gallery image. File is name file.jpg", Toast.LENGTH_LONG).show();

                                        }
                                        else
                                        {
                                            Log.i("Moving", "Moving file failed.");
                                            Toast.makeText(getApplicationContext(), "SDCARD Not writable.", Toast.LENGTH_LONG).show();
                                        }

                                                /*if (sourceLocation.exists()) {
                                                    FileChannel src = new FileInputStream(sourceLocation).getChannel();
                                                    FileChannel dst = new FileOutputStream(targetLocation).getChannel();
                                                    dst.transferFrom(src, 0, src.size());       // copy the first file to second.....
                                                    src.close();
                                                    dst.close();
                                                    Toast.makeText(getApplicationContext(), "Check the copy of the image in the same path as the gallery image. File is name file.jpg", Toast.LENGTH_LONG).show();

                                            }else {
                                                    Toast.makeText(getApplicationContext(), "SDCARD Not writable.123", Toast.LENGTH_LONG).show();
                                                }
                                        }catch (Exception e) {
                                            System.out.println("Error :" + e.getMessage());
                                        }*/



                                    }
                                });
                // Toggle new folder button enabling
                directoryChooserDialog.setNewFolderEnabled(m_newFolderEnabled);
                // Load directory chooser dialog for initial 'm_chosenDir' directory.
                // The registered callback will be called upon final directory selection.
                directoryChooserDialog.chooseDirectory(m_chosenDir);
                m_newFolderEnabled = ! m_newFolderEnabled;
            }
        });



    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        switch(requestCode)
        {
            case CHOOSE_FILE_REQUESTCODE:
                if(resultCode == RESULT_OK)
                {

                    Uri projection = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(projection, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    sourcePath = cursor.getString(columnIndex);
                    Log.i("file path",sourcePath);
                    textView_sourcePath.setVisibility(View.VISIBLE);
                    textView_sourcePath.setText(sourcePath);
                    cursor.close();

                }
                break ;

        }

    }




}
