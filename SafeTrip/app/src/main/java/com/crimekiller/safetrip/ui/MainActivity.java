package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.exception.AutoException;
import com.crimekiller.safetrip.model.User;
import com.crimekiller.safetrip.ws.local.NotificationService;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Button loginButton;
    private Button signupButton;
    private EditText editText1;
    private EditText editText2;
    private String username;
    private String password;
    private TextView loginTitle;

    private static String LOG_IN_COMMAND = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main_activity);

        Typeface GoodDog = Typeface.createFromAsset(getAssets(), "fonts/brushstr.ttf");

        editText1 = (EditText) findViewById(R.id.logIn_text1);
        editText2 = (EditText)findViewById(R.id.logIn_text2);
        loginTitle = (TextView) findViewById(R.id.logIn_title);
        loginTitle.setTypeface(GoodDog);

        loginButton = (Button) findViewById(R.id.logIn_login);
        signupButton = (Button) findViewById(R.id.logIn_signUp);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText1.getText().toString();
                password = editText2.getText().toString();

                connect();
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText1.getText().toString();

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }

    public void connect(){

        AsyncTask<Void,ArrayList<User>,Boolean> read = new AsyncTask<Void,
                ArrayList<User>, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {

                DefaultSocketClient socketClient = new DefaultSocketClient(
                        LOG_IN_COMMAND,username,password);
                socketClient.run();

                Boolean result = socketClient.getResult();
                return result;

            }

            @Override
            protected void onPostExecute(Boolean result) {
                Bundle bundle = new Bundle();
                bundle.putString("username", username);
                bundle.putString("password", password);

                if(result) {

                    Intent notificationService = new Intent(MainActivity.this,
                            NotificationService.class);

                    notificationService.putExtras(bundle);
                    startService(notificationService);

                    Intent intent = new Intent(MainActivity.this, UserPageActivity.class);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    try {
                        throw new AutoException(AutoException.ErrorInfo.WrongLogIn,
                                MainActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }
                }

            }

        };
        read.execute();

    }


}

