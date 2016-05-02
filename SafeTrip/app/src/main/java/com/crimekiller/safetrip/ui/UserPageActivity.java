package com.crimekiller.safetrip.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.exception.AutoException;

/**
 * Created by xuvincent on 16/4/2.
 */
public class UserPageActivity extends AppCompatActivity {

    private Button profileBT;
    private Button postBT;
    private Button friendsBT;
    private Button adminBT;
    private Button locationBT;
    private Button trackBT;
    private TextView userPage_user;
    private TextView Welcome;
    private ImageView adminImage;

    private String username;
    private String password;
    private Bundle bundle;
    private LocationManager lm;
    private Location lc;

    private static String  SHARE_LOCATION = "Share my location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_userpage_activity);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GoodDog.otf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.Userpage_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");

        userPage_user = (TextView) findViewById(R.id.userPage_user);
        userPage_user.setText(username);
        userPage_user.setTypeface(tf);

        Welcome = (TextView) findViewById(R.id.Welcome);
        Welcome.setTypeface(tf);

        profileBT = (Button)findViewById(R.id.userPage_myProfile);

        postBT = (Button)findViewById(R.id.userPage_post);

        friendsBT = (Button)findViewById(R.id.userPage_friends);

        adminBT = (Button) findViewById(R.id.userPage_admin);

        locationBT = (Button) findViewById(R.id.userPage_location);

        trackBT = (Button) findViewById(R.id.userPage_track);

        adminImage = (ImageView) findViewById(R.id.UserPage_AdminImageView);

        if (!username.equals("admin")) {
            adminBT.setVisibility(View.GONE);
            adminImage.setVisibility(View.GONE);
        } else {
            profileBT.setVisibility(View.GONE);
            postBT.setVisibility(View.GONE);
            friendsBT.setVisibility(View.GONE);
            locationBT.setVisibility(View.GONE);
            trackBT.setVisibility(View.GONE);
        }

        profileBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserPageActivity.this, MyProfileActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        postBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, ManagePostActivity.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        friendsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, ManageFriendActivity.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        adminBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (username.equals("admin")) {
                    Intent intent = new Intent(UserPageActivity.this, AdminActivity.class);

                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {

                    try {
                        throw new AutoException(AutoException.ErrorInfo.WrongAdmin,
                                UserPageActivity.this);
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }

                }
            }
        });

        locationBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocation();
                new AlertDialog.Builder(UserPageActivity.this)
                        .setTitle("Alert: ")
                        .setMessage("You Have shared your location ! ")
                        .setNegativeButton("OK", null)
                        .show();
            }
        });

        trackBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, TrackLocationActivity.class );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public void checkLocation(){

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        lc = lm.getLastKnownLocation(lm.GPS_PROVIDER);
        updateShow(lc);

        lm.requestLocationUpdates(lm.GPS_PROVIDER, 0, 0, new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                //updateShow(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission( UserPageActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission( UserPageActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( UserPageActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                updateShow(lm.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                updateShow(null);
            }
        });
    }

    private void updateShow(Location location) {

        StringBuilder longitude = new StringBuilder();
        StringBuilder latitude = new StringBuilder();

        longitude.append(location.getLongitude());
        latitude.append(location.getLatitude());

        System.out.println("*****L" + longitude.toString() + "********Lat" + latitude.toString());
        connect(longitude.toString(), latitude.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) // switch based on selected MenuItem's ID
        {
            case R.id.logout_LogOutItem:
                // create an Intent to launch the AddEditContact Activity
                Intent logOut =
                        new Intent(UserPageActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }

    public void connect( final String longitude, final String latitude){

        AsyncTask<String,Void,Void> task = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {

                DefaultSocketClient socketClient = new DefaultSocketClient(
                        SHARE_LOCATION,username,longitude,latitude);
                socketClient.run();
                return null;
            }
        };
        task.execute();

    }

}
