package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class SignUpActivity extends Activity{

    private Button signupButton;
    private EditText editText1;//??
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    private String username;//??
    private String email;
    private String password;
    private String password2;

    private Socket socket;
    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;
    private String command = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_signup_activity);

        editText1 = (EditText)findViewById(R.id.signUp_username);//??
        editText2 = (EditText)findViewById(R.id.signUp_email);//??
        editText3 = (EditText)findViewById(R.id.signUp_password);//??
        editText4 = (EditText)findViewById(R.id.signUp_password2);//??

        signupButton = (Button)findViewById(R.id.signUp_signUP);
//        signupButton.setOnClickListener(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 2

                username = editText1.getText().toString();//?
                email = editText2.getText().toString();//?
                password = editText3.getText().toString();//?
                password2 = editText4.getText().toString();//?

                //when twice password not same
                if(!password.equals(password2)){
                    Toast.makeText(SignUpActivity.this, "Input Different Password! ",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if (connect()) {

                        Intent intent = new Intent(SignUpActivity.this, UserPageActivity.class);
                        startActivity(intent);
                    } else {//when username existe or twice password not same
                        Toast.makeText(SignUpActivity.this, "Fail to Create User!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
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


    public Boolean connect(){

        AsyncTask<Void,ArrayList<User>,Boolean> read = new AsyncTask<Void, ArrayList<User>, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(
                        command,username,email,password);
                socketClient.run();

                return socketClient.getResult();
            //返回一个Boolean就行了


//                userList = socketClient.getUserList();
//                publishProgress(userList);

//                return null;
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
        return null;
    }



}
