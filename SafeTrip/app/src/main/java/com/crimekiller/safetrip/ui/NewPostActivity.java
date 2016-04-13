package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crimekiller.safetrip.DBLayout.DBConnector;
import com.crimekiller.safetrip.Model.Post;
import com.crimekiller.safetrip.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewPostActivity extends AppCompatActivity {

    // EditTexts for post information
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

       /* finishBtn.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(NewPostActivity.this, ViewAllPostActivity.class);
                startActivity(i);
            }
        });*/

        finishBtn.setOnClickListener(finishButtonClicked);

    }

    View.OnClickListener finishButtonClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // test if the input is legal
            String testFlag = testInput();
            if (testFlag.equals("true"))// if legal input
            {
                AsyncTask<Object, Object, Object> savePostTask =
                        new AsyncTask<Object, Object, Object>()
                        {
                            @Override
                            protected Object doInBackground(Object... params)
                            {
                                //add data to database
                                addToDataBase();

                                return null;
                            } // end method doInBackground

                            @Override
                            protected void onPostExecute(Object result)
                            {
                                //finish(); // return to the previous Activity
                                Intent i = new Intent(NewPostActivity.this, ViewSelfPostActivity.class);
                                startActivity(i);
                            } // end method onPostExecute
                        }; // end AsyncTask

                // save the post to the database using a separate thread
                savePostTask.execute((Object[]) null);

            } else
            {
                Toast.makeText(getApplicationContext(), testFlag, Toast.LENGTH_LONG).show();

            } // end else
        } // end method onClick
    }; // end OnClickListener saveButtonClicked

    private String testInput(){

        // test the post related information entered by user
        // if each input row is empty
        // it is illegal
        if (licensePlateET.getText().toString().length() == 0 || destinationET.getText().toString().length() == 0 ){
            return "Please fill all required fields.";
        } else {
            return "true";
        }


    }


    private void addToDataBase()
    {

        String date="";
        String licensePlate="";
        String destination= "";
        String model = "";
        String color = "";
        String departure = "";
        // get DatabaseConnector to interact with the SQLite database
        DBConnector dbConnector = new DBConnector(this);


        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm MM/dd/yyyy", Locale.US);

        date = dateformat.format(new Date());
        licensePlate = licensePlateET.getText().toString();
        destination = destinationET.getText().toString();
        model = modelET.getText().toString();
        color = colorET.getText().toString();
        departure = departureET.getText().toString();

        Post newPost = new Post(date, licensePlate,destination,model,color,departure," ");

        // insert post into local database
        dbConnector.insertRecord(newPost);

    }



}
