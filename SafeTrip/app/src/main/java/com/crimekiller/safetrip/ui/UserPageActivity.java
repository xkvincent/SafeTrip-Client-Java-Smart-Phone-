package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crimekiller.safetrip.R;

/**
 * Created by xuvincent on 16/4/2.
 */
public class UserPageActivity extends Activity{

    private Button profileBT;
    private Button postBT;
    private Button friendsBT;
    private Button adminBT;
    private String username;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_userpage_activity);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");
        Log.d("UserPageActivity", username);

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
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        friendsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, ManageFriendActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        adminBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, AdminActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }



}
