package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Boolean result;//??
    private EditText editText1;//??
    private EditText editText2;
    private EditText editText3;
    private String oldPassword;//??
    private String newPassword;
    private String newPassword2;


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

        editText1 = (EditText)findViewById(R.id.editPassword_old);//?
        editText2 = (EditText)findViewById(R.id.editPassword_password);//?
        editText3 = (EditText)findViewById(R.id.editPassword_password2);//?

        oldPassword = editText1.getText().toString();//??
        newPassword = editText2.getText().toString();
        newPassword2 = editText3.getText().toString();

        finishBt = (Button)findViewById(R.id.editPassword_finish2);
//        finishBt.setOnClickListener(this);

        finishBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 5
                //if twice new password not same
                if(!newPassword.equals(newPassword2)){
                    Toast.makeText(EditPasswordActivity.this, "New password are not same! ",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if (connect()) {
                        Intent intent = new Intent(EditPasswordActivity.this,
                                MyProfileActivity.class);
                        startActivity(intent);
                    } else {// input wrong old password
                        Toast.makeText(EditPasswordActivity.this, "Fail to Edit Password!",
                                Toast.LENGTH_SHORT).show();
                    }
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
                /*DefaultSocketClient socketClient = new DefaultSocketClient(command,username,
                        oldPassword,newPassword);
                socketClient.run();

                return socketClient.getResult();*/
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
