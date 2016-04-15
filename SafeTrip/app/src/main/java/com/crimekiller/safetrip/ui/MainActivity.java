package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.ws.local.NotificationService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends Activity{

    private Button loginButton;
    private Button signupButton;
    private EditText editText;  //?
    private String username;//?this is the username need to be passed to all of the activities

    private Socket socket;
    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;
    private String command = "SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main_activity);

        editText = (EditText) findViewById(R.id.logIn_text1);//?

        loginButton = (Button)findViewById(R.id.logIn_login);
        signupButton = (Button)findViewById(R.id.logIn_signUp);
//        loginButton.setOnClickListener(this);
//        loginButton.setOnClickListener(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 2

                startService(new Intent(MainActivity.this,NotificationService.class));
                username = editText.getText().toString();//?
                Log.d("MianActivity", username);//?

                Intent intent = new Intent(MainActivity.this, UserPageActivity.class);
                intent.putExtra("username", username);//?
                startActivity(intent);
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 3
                username = editText.getText().toString();//?
                Log.d("MianActivity", username);//?

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.putExtra("username", username);//?
                startActivity(intent);
            }
        });

    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ui_main_activity:    //to 2
//                Intent intent = new Intent(MainActivity.this, UserPage_Activity.class);
//                startActivity(intent);
//                break;
//            case R.id.ui_signup_activity:   //to 3
//                Intent intent2 = new Intent(MainActivity.this, SignUp_Activity.class);
//                startActivity(intent2);
//                break;
//            default:
//                break;
//
//        }
//    }


}
