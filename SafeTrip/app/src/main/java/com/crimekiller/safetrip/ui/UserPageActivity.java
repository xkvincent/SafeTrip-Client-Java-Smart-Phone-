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
    private String username;//?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_userpage_activity);

        Intent intent = getIntent();//?  get the username from former activity
        username = intent.getStringExtra("username");//?
        Log.d("UserPageActivity", username);//?

        profileBT = (Button)findViewById(R.id.userPage_myProfile);
        postBT = (Button)findViewById(R.id.userPage_post);
        friendsBT = (Button)findViewById(R.id.userPage_friends);
        adminBT = (Button) findViewById(R.id.userPage_admin);
//        profileButton.setOnClickListener(this);
//        postButton.setOnClickListener(this);
//        friendsButton.setOnClickListener(this);

        profileBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 5

                Intent intent = new Intent(UserPageActivity.this, MyProfileActivity.class);
                intent.putExtra("username", username);//?
                startActivity(intent);
            }
        });

        postBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, ManagePostActivity.class);
                intent.putExtra("username", username);//?
                startActivity(intent);
            }
        });
        friendsBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, ManageFriendActivity.class);
                intent.putExtra("username", username);//?
                startActivity(intent);
            }
        });
        adminBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserPageActivity.this, AdminActivity.class);
                intent.putExtra("username", username);//?
                startActivity(intent);
            }
        });

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.myProfile:    //to 5
//                Intent intent = new Intent(UserPage_Activity.this, MyProfile_Activity.class);
//                startActivity(intent);
//                break;
//            case R.id.manage:   //to ?
//                Intent intent2 = new Intent(UserPage_Activity.this, SignUp_Activity.class);//?
//                startActivity(intent2);
//                break;
//            case R.id.post:   //to ?
//                Intent intent3 = new Intent(UserPage_Activity.this, SignUp_Activity.class);//?
//                startActivity(intent3);
//                break;
//            default:
//                break;
//
//        }
//    }

}
