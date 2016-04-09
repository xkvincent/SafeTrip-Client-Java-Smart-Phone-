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
public class MyProfileActivity extends Activity{

    private Button manageUsernameBt;
    private Button editPasswordBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_myprofile_activity);

        manageUsernameBt = (Button)findViewById(R.id.myProfile_manage);
        editPasswordBt = (Button)findViewById(R.id.myProfile_edit);
//        manageUsernameBt.setOnClickListener(this);
//        editPasswordBt.setOnClickListener(this);

        manageUsernameBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 8
                Intent intent = new Intent(MyProfileActivity.this, ManageUsernameActivity.class);
                startActivity(intent);
            }
        });

        editPasswordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 9
                Intent intent = new Intent(MyProfileActivity.this, EditPasswordActivity.class);
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
