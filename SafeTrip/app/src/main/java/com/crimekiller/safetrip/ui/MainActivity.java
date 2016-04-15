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
    private EditText editText1;  //?
    private EditText editText2;
    private String username;//?this is the username need to be passed to all of the activities
    private String password;

    private Socket socket;
    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;
    private String command = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main_activity);

        editText1 = (EditText) findViewById(R.id.logIn_text1);//??
        editText2 = (EditText)findViewById(R.id.logIn_text2);//??

        loginButton = (Button) findViewById(R.id.logIn_login);
        signupButton = (Button) findViewById(R.id.logIn_signUp);
//        loginButton.setOnClickListener(this);
//        loginButton.setOnClickListener(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 2

                startService(new Intent(MainActivity.this, NotificationService.class));
                username = editText1.getText().toString();//?
                password = editText2.getText().toString();//??
                Log.d("MianActivity", username);//?
                Log.d("MianActivity", password);//??

                if(connect()) {

                    Intent intent = new Intent(MainActivity.this, UserPageActivity.class);
                    intent.putExtra("username", username);//?
                    startActivity(intent);
                }
                else{  //?? when input wrong user data
                    Toast.makeText(MainActivity.this, "Wrong Username or Password!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//to 3
                username = editText1.getText().toString();//?
                Log.d("MianActivity", username);//?

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.putExtra("username", username);//?
                startActivity(intent);
            }
        });

    }


    public Boolean connect(){//when input right username and password, return truth

        AsyncTask<Void,ArrayList<User>,Boolean> read = new AsyncTask<Void, ArrayList<User>, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(command,username,password);
                socketClient.run();

                return socketClient.getResult();//??

            //返回一个Boolean就行了


//                userList = socketClient.getUserList();
//                publishProgress(userList);

  //              return false;
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
