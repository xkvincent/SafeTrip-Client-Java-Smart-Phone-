package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crimekiller.safetrip.R;

public class MainActivity extends Activity{

    private Button loginButton;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main_activity);

        loginButton = (Button)findViewById(R.id.logIn_login);
        signupButton = (Button)findViewById(R.id.logIn_signUp);
//        loginButton.setOnClickListener(this);
//        loginButton.setOnClickListener(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 2
                Intent intent = new Intent(MainActivity.this, UserPageActivity.class);
                startActivity(intent);
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 3
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
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
