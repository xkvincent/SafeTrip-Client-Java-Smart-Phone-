package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.crimekiller.safetrip.R;

public class NewPostActivity extends AppCompatActivity {

    // EditTexts for contact information
    private EditText licensePlateET;
    private EditText destinationET;
    private EditText modelET;
    private EditText colorET;
    private EditText departureET;

    private Button finishBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_new_post_activity);

        licensePlateET = (EditText) findViewById(R.id.NewPost_LicensePlate);
        destinationET = (EditText) findViewById(R.id.NewPost_Destination);
        modelET = (EditText) findViewById(R.id.NewPost_Model);
        colorET = (EditText) findViewById(R.id.NewPost_Color);
        departureET = (EditText) findViewById(R.id.NewPost_Departure);

        finishBtn = (Button) findViewById(R.id.NewPost_Finish);

        finishBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(NewPostActivity.this, ViewAllPostActivity.class);
                startActivity(i);
            }
        });


    }



}
