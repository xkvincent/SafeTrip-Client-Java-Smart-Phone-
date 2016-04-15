package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.model.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 * Created by xuvincent on 16/4/2.
 */
public class EditPasswordActivity extends Activity{

    private Button finishBt;
    private String username;//?

    private Socket socket;
    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;
    private String command = "EditPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_edit_password_activity);

        Intent intent = getIntent();//?
        username = intent.getStringExtra("username");//?
        Log.d("EditActivity", username);//?

        finishBt = (Button)findViewById(R.id.editPassword_finish2);
//        finishBt.setOnClickListener(this);

        finishBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 5
                if(connect()) {
                    Intent intent = new Intent(EditPasswordActivity.this, MyProfileActivity.class);
                    startActivity(intent);
                }
                else{// input wrong old password or twice new are not same
                    Toast.makeText(EditPasswordActivity.this, "Fail to Edit Password!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.finish2:    //to finish
//                Intent intent = new Intent(EditPassword_Activity.this, .class);//往上一个activity
//                startActivity(intent);
//                break;
//            default:
//                break;
//
//        }
//    }

    public Boolean connect(){//when input right username and password, return truth

        AsyncTask<Void,ArrayList<User>,Boolean> read = new AsyncTask<Void, ArrayList<User>, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(command);
                socketClient.run();

                //返回一个Boolean就行了


//                userList = socketClient.getUserList();
//                publishProgress(userList);

                return false;
            }

//            @Override
//            protected void onProgressUpdate(ArrayList<User>... values) {
//
//                UserAdapter adapter = new UserAdapter(userList);
//                setListAdapter(adapter);
//                super.onProgressUpdate(values);
//            }
        };
        read.execute();
        return false;
    }


}
