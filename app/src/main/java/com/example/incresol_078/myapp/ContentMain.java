package com.example.incresol_078.myapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Incresol-078 on 24-05-2016.
 */
public class ContentMain extends AppCompatActivity {
    TextView textView_username;
    Button button,button_ambulance,button_submit;
    FloatingActionButton fab;
    Intent intent;
    RadioButton radioButton_male,radioButton_female;
    Switch swith_OnOff;
    CheckBox checkBox_indian;
    RatingBar ratingBar;
    DatePicker datePicker;
    EditText editText_email;
    Toast toast;
    public final static String EXTRA_MESSAGE = "com.example.incresol_078.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        button = (Button) findViewById(R.id.button_progress);
        textView_username = (TextView)findViewById(R.id.textView_username) ;
        radioButton_male=(RadioButton) findViewById(R.id.radioButton_male);
        radioButton_female=(RadioButton) findViewById(R.id.radioButton_female);
        swith_OnOff=(Switch) findViewById(R.id.switch_OnOff);
        checkBox_indian=(CheckBox) findViewById(R.id.checkBox_indian);
        button_submit=(Button) findViewById(R.id.button_submit);
        ratingBar=(RatingBar) findViewById(R.id.ratingBar);
        datePicker=(DatePicker) findViewById(R.id.datePicker);
        editText_email=(EditText) findViewById(R.id.editText_email);

        intent=getIntent();
        String userName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        textView_username.setText(userName);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast = Toast.makeText(getApplicationContext(), "Call 101", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }
        });
        button_ambulance = (Button) findViewById(R.id.button_ambulance);
        button_ambulance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toast = Toast.makeText(getApplicationContext(), "Call 108", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 30, 80);
                toast.show();
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Mail Services Coming soon", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button_submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
               StringBuffer result=new StringBuffer();
                result.append(" Indian : ").append(checkBox_indian.isChecked());
                result.append(" Swith : ").append(swith_OnOff.isChecked());
                result.append(" Male : ").append(radioButton_male.isChecked());
                result.append(" Female : ").append(radioButton_female.isChecked());
                result.append(" Rating : ").append(ratingBar.getRating());
                result.append(" Day : ").append(datePicker.getDayOfMonth());
                result.append(" Email : ").append(editText_email.getText().toString());
                Snackbar.make(view, result.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }

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

        switch (item.getItemId())
        {
        case R.id.action_logout:
            // User chose the "Settings" item, show the app settings UI...
            String logoutMessage="you are logged out";
            intent = new Intent(this, MainActivity.class);
            /**/
            intent.putExtra(EXTRA_MESSAGE,logoutMessage);
            startActivity(intent);
            return true;

            case R.id.action_settings:
                intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                startActivity(intent);
                return true;

    }
        return super.onOptionsItemSelected(item);
    }
}
