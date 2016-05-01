package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.exception.AutoException;

import java.util.ArrayList;

public class TrackLocationActivity extends AppCompatActivity {
    private EditText trackFriendName;
    private TextView titleTv;
    private TextView ButtonTv;
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

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GoodDog.otf");
        TextView tv = (TextView)findViewById(R.id.text);

        titleTv = (TextView) findViewById(R.id.titleTv);
        titleTv.setTypeface(tf);    //Set Font

        Toolbar toolbar = (Toolbar) findViewById(R.id.MyProfile_toolbar);
        setSupportActionBar(toolbar);

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
            }
        });
    }

    public void connect() {

        AsyncTask<Void, ArrayList<String>, Void> task = new AsyncTask<Void, ArrayList<String>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(TRACK_FRIEND_LIST_COMMAND,
                        username, trackname );
                socketClient.run();
                locationList = socketClient.getLocationList();
                publishProgress(locationList);
                return null;
            }

            @Override
            protected void onProgressUpdate(ArrayList<String>... values) {

                if( locationList.size() == 0 ){
                    //Not Friend
                    try {
                        throw new AutoException(AutoException.ErrorInfo.TrackError, TrackLocationActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }
                } else if( locationList.size() == 1){
                    //No share Location
                    try {
                        throw new AutoException(AutoException.ErrorInfo.ShareLocationError, TrackLocationActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }
                }
                else {
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
                super.onProgressUpdate(values);
            }

        };
        task.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) // switch based on selected MenuItem's ID
        {
            case R.id.LogOutItem:
                // create an Intent to launch the AddEditContact Activity
                Intent logOut =
                        new Intent(TrackLocationActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(TrackLocationActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }
}
