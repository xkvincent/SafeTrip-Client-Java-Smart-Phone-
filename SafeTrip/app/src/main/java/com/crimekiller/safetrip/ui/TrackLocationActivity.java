package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.exception.AutoException;
import com.crimekiller.safetrip.model.User;

import java.util.ArrayList;

public class TrackLocationActivity extends AppCompatActivity {
    private EditText trackFriendName;
    private Button trackLocation;
    private String username;
    private String password;
    private String trackname;

    private Bundle bundle;
    private String latitude ;
    private String longtitude;

    private ArrayList<String> locationList;
    private static String TRACK_FRIEND_LIST_COMMAND = "Track Friend List";

    //-34, 151
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_track_location_activity);
        trackLocation = (Button) findViewById(R.id.trackLocation);
        trackFriendName = (EditText) findViewById(R.id.track_friend_name);
        locationList = new ArrayList<String>();

        Intent intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");

        trackLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackname = trackFriendName.getText().toString();
                connect();

                if( locationList !=null && locationList.size() !=0 ){
                    latitude = locationList.get(0);
                    longtitude = locationList.get(1);

                    Intent intent = new Intent( TrackLocationActivity.this, MapsActivity.class);
                    Bundle myBundle = new Bundle();
                    myBundle.putString("username", username);
                    myBundle.putString("password", password);
                    myBundle.putString("latitude", latitude );
                    myBundle.putString("longtitude", longtitude);
                    intent.putExtras(myBundle);

                    startActivity(intent);
                }
               else{
                    try {
                        throw new AutoException(AutoException.ErrorInfo.TrackError, TrackLocationActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }
                    // System.out.println("You can not track person who are not your friend");
                }
            }
        });
    }

    public void connect() {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(TRACK_FRIEND_LIST_COMMAND,
                        username, trackname );
                socketClient.run();
                locationList = socketClient.getLocationList();
                //publishProgress(userList);
                return null;
            }

        };
        task.execute();
    }
}
