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
public class ManageUsernameActivity extends Activity{


    private Button finishBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mange_username);

        finishBt = (Button)findViewById(R.id.manageUsername_finish);
//        finishBt.setOnClickListener(this);

        finishBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 5
                Intent intent = new Intent(ManageUsernameActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.finish:    //to finish
//                Intent intent = new Intent(ManageUsername_Activity.this, .class);//往上一个activity
//                startActivity(intent);
//                break;
//            default:
//                break;
//
//        }
//    }

}
