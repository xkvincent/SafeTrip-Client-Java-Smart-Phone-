package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crimekiller.safetrip.R;

/**
 * Created by xuvincent on 16/4/2.
 */
public class MyProfileActivity extends AppCompatActivity {

    private Button editPasswordBt;
    private TextView textView;
    private String username;
    private Bundle bundle;
    private Button editPicture;

    private static String MY_PROFILE_COMMAND = "MyProfile";
    //private String command = "MyProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_myprofile_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.MyProfile_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");
//        username = intent.getStringExtra("username");
        Log.d("MyprofileActivity", username);

        textView = (TextView)findViewById(R.id.myProfile_textView6);
        textView.setText("Username: "+username);

        editPasswordBt = (Button)findViewById(R.id.myProfile_edit);

        editPasswordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, EditPasswordActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
                        new Intent(MyProfileActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(MyProfileActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }

}
