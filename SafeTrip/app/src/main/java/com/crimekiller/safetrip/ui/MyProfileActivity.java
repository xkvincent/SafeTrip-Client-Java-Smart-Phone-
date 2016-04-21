package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crimekiller.safetrip.R;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by xuvincent on 16/4/2.
 */
public class MyProfileActivity extends Activity{

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

}
