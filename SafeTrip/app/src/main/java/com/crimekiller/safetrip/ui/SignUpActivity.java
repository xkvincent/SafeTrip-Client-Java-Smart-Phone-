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

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by xuvincent on 16/4/2.
 */
public class SignUpActivity extends Activity{

    private Button signupButton;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    private String username;
    private String email;
    private String password;
    private String password2;

//    private String command = "SignUp";
    private static String SIGN_UP_COMMAND = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_signup_activity);

        editText1 = (EditText)findViewById(R.id.signUp_username);
        editText2 = (EditText)findViewById(R.id.signUp_email);
        editText3 = (EditText)findViewById(R.id.signUp_password);
        editText4 = (EditText)findViewById(R.id.signUp_password2);

        signupButton = (Button)findViewById(R.id.signUp_signUP);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = editText1.getText().toString();
                email = editText2.getText().toString();
                password = editText3.getText().toString();
                password2 = editText4.getText().toString();

                Log.d("SignUP username", username);
                Log.d("SignUP email",email);
                Log.d("SignUP password1",password);
                Log.d("SignUP password2",password2);

                //when twice password not same
                if(!password.equals(password2)){
                    Toast.makeText(SignUpActivity.this, "Input Different Password! ",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    connect();
                }
            }
        });
    }



    public void connect(){

        AsyncTask<Void,Integer,Boolean> read = new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {


                DefaultSocketClient socketClient = new DefaultSocketClient(
                        SIGN_UP_COMMAND,username,email,password);
                socketClient.run();

                Boolean result = socketClient.getResult();
                Log.d("ceshi",result.toString());

                return result;

            }

            @Override
            protected void onPostExecute(Boolean result) {
                Log.d("onpost","jinru");
                if(result){
                    Log.d("result = ",result.toString());
                    Intent intent = new Intent(SignUpActivity.this, UserPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("password", password);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Log.d("result=",result.toString());
                    Toast.makeText(SignUpActivity.this, "Fail to Create User!",
                            Toast.LENGTH_SHORT).show();

                }
            }

        };
        read.execute();
        Log.d("defalut","false");

    }

}
