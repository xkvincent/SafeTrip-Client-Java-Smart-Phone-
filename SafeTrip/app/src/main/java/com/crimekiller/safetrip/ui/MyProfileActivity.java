package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crimekiller.safetrip.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by xuvincent on 16/4/2.
 */
public class MyProfileActivity extends Activity{

//    private Button manageUsernameBt;
    private Button editPasswordBt;
    private String username;//?

    private Socket socket;
    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;
    private String command = "MyProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_myprofile_activity);

        Intent intent = getIntent();//?  get the username from former activity
        username = intent.getStringExtra("username");//?
        Log.d("MyprofileActivity", username);//?

//        manageUsernameBt = (Button)findViewById(R.id.myProfile_manage);
        editPasswordBt = (Button)findViewById(R.id.myProfile_edit);
//        manageUsernameBt.setOnClickListener(this);
//        editPasswordBt.setOnClickListener(this);

//        manageUsernameBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {//to 8
//                startActivity(intent);
//            }
//        });

        editPasswordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 9
                Intent intent = new Intent(MyProfileActivity.this, EditPasswordActivity.class);
                intent.putExtra("username", username);//?
                startActivity(intent);
            }
        });

    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.manage:    //to 8
//                Intent intent = new Intent(MyProfile_Activity.this, ManageUsername_Activity.class);
//                startActivity(intent);
//                break;
//            case R.id.edit:   //to 9
//                Intent intent2 = new Intent(MyProfile_Activity.this, EditPassword_Activity.class);
//                startActivity(intent2);
//                break;
//            default:
//                break;
//
//        }
//    }
}
