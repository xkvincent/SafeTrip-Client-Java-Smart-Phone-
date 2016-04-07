package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.crimekiller.safetrip.R;

/**
 * Created by xuvincent on 16/4/2.
 */
public class SignUpActivity extends Activity{

    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        signupButton = (Button)findViewById(R.id.signUp_signUP);
//        signupButton.setOnClickListener(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 2
                Intent intent = new Intent(SignUpActivity.this, UserPageActivity.class);
                startActivity(intent);
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.login:    //to 2
//                Intent intent = new Intent(MainActivity.this, UserPage_Activity.class);
//                startActivity(intent);
//                break;
//            case R.id.signup:   //to 3
//                Intent intent2 = new Intent(MainActivity.this, SignUp_Activity.class);
//                startActivity(intent2);
//                break;
//            default:
//                break;
//
//        }
//    }

}