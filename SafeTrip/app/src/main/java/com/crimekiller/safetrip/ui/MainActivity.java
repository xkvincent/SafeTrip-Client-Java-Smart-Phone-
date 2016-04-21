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
import com.crimekiller.safetrip.ws.local.NotificationService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button loginButton;
    private Button signupButton;
    private EditText editText1;
    private EditText editText2;
    private String username;
    private String password;

    private static String LOG_IN_COMMAND = "Login";
    //private String command = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main_activity);

        editText1 = (EditText) findViewById(R.id.logIn_text1);
        editText2 = (EditText)findViewById(R.id.logIn_text2);

        loginButton = (Button) findViewById(R.id.logIn_login);
        signupButton = (Button) findViewById(R.id.logIn_signUp);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                username = editText1.getText().toString();
                password = editText2.getText().toString();
                Log.d("MianActivity", username);
                Log.d("MianActivity", password);


        //        if(connect()) {




//                    Intent intent = new Intent(MainActivity.this, UserPageActivity.class);
//                    intent.putExtra("username", username);//?
//                    startActivity(intent);
      //          }
//                else{  //?? when input wrong user data
//                    Toast.makeText(MainActivity.this, "Wrong Username or Password!",
//                            Toast.LENGTH_SHORT).show();
//                }

                connect();
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText1.getText().toString();
                Log.d("MianActivity", username);

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }

    public void connect(){

        AsyncTask<Void,ArrayList<User>,Boolean> read = new AsyncTask<Void, ArrayList<User>, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {

                DefaultSocketClient socketClient = new DefaultSocketClient(
                        LOG_IN_COMMAND,username,password);
                socketClient.run();

                Boolean result = socketClient.getResult();
                Log.d("ceshi",result.toString());

                return result;

            }

            @Override
            protected void onPostExecute(Boolean result) {
                Log.d("onpost","jinru");
                if(result) {

                    Intent notificationService = new Intent(MainActivity.this, NotificationService.class);
                    notificationService.putExtra("username", username);
                    startService(notificationService);

                    Intent intent = new Intent(MainActivity.this, UserPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("password", password);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Log.d("jinru", "toast");
                    Toast.makeText(MainActivity.this, "Wrong Username or Password!",
                            Toast.LENGTH_SHORT).show();
                }

            }

        };
        read.execute();

    }


}
