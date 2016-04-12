package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crimekiller.safetrip.R;

public class ViewDetailedSelfPostActivity extends AppCompatActivity {

    private TextView dateTV, licensePlateTV, destinationTV, modelTV, colorTV, departureTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_view_detailed_self_post_activity);

        Button deleteBtn = (Button) findViewById(R.id.DetailedSelfPost_Delete);

        dateTV =(TextView)findViewById(R.id.DetailedSelfPost_Date);
        licensePlateTV =(TextView)findViewById(R.id.DetailedSelfPost_Plate);
        destinationTV =(TextView)findViewById(R.id.DetailedSelfPost_Destination);
        modelTV =(TextView)findViewById(R.id.DetailedSelfPost_Model);
        colorTV =(TextView)findViewById(R.id.DetailedSelfPost_Color);
        departureTV =(TextView)findViewById(R.id.DetailedSelfPost_Departure);

        Intent intent = getIntent();

        dateTV.setText(intent.getStringExtra("DATE"));
        licensePlateTV.setText(intent.getStringExtra("PLATE"));
        destinationTV.setText(intent.getStringExtra("DESTINATION"));
        modelTV.setText(intent.getStringExtra("MODEL"));
        colorTV.setText(intent.getStringExtra("COLOR"));
        departureTV.setText(intent.getStringExtra("DEPARTURE"));

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)  {
                Toast.makeText(ViewDetailedSelfPostActivity.this, "No implementation yet", Toast.LENGTH_LONG).show();
            }
        });

    }







}
