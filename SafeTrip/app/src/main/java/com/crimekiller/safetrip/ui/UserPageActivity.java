package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.model.User;
import com.crimekiller.safetrip.ws.local.NotificationService;

import java.util.ArrayList;

/**
 * Created by xuvincent on 16/4/2.
 */
public class UserPageActivity extends AppCompatActivity {

    private Button profileBT;
    private Button postBT;
    private Button friendsBT;
    private Button adminBT;
    private String username;
    private String password;
    private Bundle bundle;

    private static String ADMIN_COMMAND = "Admin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_userpage_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Userpage_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");
        Log.d("UserPageActivity", username);
        Log.d("UserPageActivity", password);

        profileBT = (Button)findViewById(R.id.userPage_myProfile);
        postBT = (Button)findViewById(R.id.userPage_post);
        friendsBT = (Button)findViewById(R.id.userPage_friends);
        adminBT = (Button) findViewById(R.id.userPage_admin);

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
                //intent.putExtra("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        friendsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, ManageFriendActivity.class);
                //intent.putExtra("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        adminBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                connect();
            if(username.equals("admin")) {
                Intent intent = new Intent(UserPageActivity.this, AdminActivity.class);
                //intent.putExtra("username", username);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{
                Log.d("jinru", "toast");
                Toast.makeText(UserPageActivity.this, "You Are Not an Admin!",
                            Toast.LENGTH_SHORT).show();
            }
            }
        });

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


//    public void connect(){
//
//        AsyncTask<Void,ArrayList<User>,Boolean> read = new AsyncTask<Void, ArrayList<User>, Boolean>() {
//            @Override
//            protected Boolean doInBackground(Void... params) {
//
//                DefaultSocketClient socketClient = new DefaultSocketClient(
//                        ADMIN_COMMAND,username,password);
//                socketClient.run();
//
//                Boolean result = socketClient.getResult();
//                Log.d("ceshi",result.toString());
//
//                return result;
//
//            }
//
//            @Override
//            protected void onPostExecute(Boolean result) {
//                Log.d("onpost","jinru");
//                if(result) {
//
////                    Intent intent = new Intent(UserPageActivity.this, UserPageActivity.class);
////                    Bundle bundle = new Bundle();
////                    bundle.putString("username", username);
////                    bundle.putString("password", password);
////                    intent.putExtras(bundle);
////                    startActivity(intent);
//
//                    Intent intent = new Intent(UserPageActivity.this, AdminActivity.class);
//                    intent.putExtra("username", username);
//                    startActivity(intent);
//                }
//                else {
//                    Log.d("jinru", "toast");
//                    Toast.makeText(UserPageActivity.this, "You Are Not an Admin!",
//                            Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//        };
//        read.execute();
//
//    }



}
