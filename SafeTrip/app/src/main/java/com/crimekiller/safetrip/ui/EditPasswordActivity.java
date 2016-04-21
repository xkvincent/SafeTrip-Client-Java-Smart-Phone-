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
    private String username;
    private Boolean result;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private String oldPassword;
    private String newPassword;
    private String newPassword2;

    private String password;
    private Bundle bundle;

    private static String EDIT_PASSWORD_COMMAND = "EditPassword";
    //private String command = "EditPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_edit_password_activity);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");

        Log.d("EditActivity", username);
        Log.d("EditActivity", password);

        editText1 = (EditText)findViewById(R.id.editPassword_old);
        editText2 = (EditText)findViewById(R.id.editPassword_password);
        editText3 = (EditText)findViewById(R.id.editPassword_password2);

        finishBt = (Button)findViewById(R.id.editPassword_finish2);

        finishBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldPassword = editText1.getText().toString();
                newPassword = editText2.getText().toString();
                newPassword2 = editText3.getText().toString();

                Log.d("oldPassword",oldPassword);
                Log.d("newPass",newPassword);

                //if twice new password not same
                if(!newPassword.equals(newPassword2)){
                    Toast.makeText(EditPasswordActivity.this, "New password are not same! ",
                            Toast.LENGTH_SHORT).show();
                }
                else {

                    if (password.equals(oldPassword)) {
                        connect();

                        Log.d("Edit", "in succ");
                        Intent intent2 = new Intent(EditPasswordActivity.this,
                                MyProfileActivity.class);
                        intent2.putExtras(bundle);
                        startActivity(intent2);
                    } else {// input wrong old password
                        Toast.makeText(EditPasswordActivity.this, "Input Wrong Old Password!",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public void connect(){//when input right username and password, return truth

        AsyncTask<Void,ArrayList<User>,Void> read = new AsyncTask<Void, ArrayList<User>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                DefaultSocketClient socketClient = new DefaultSocketClient(EDIT_PASSWORD_COMMAND, username,
                        newPassword);
                socketClient.run();

                return null;
            }
        };
        read.execute();

    }

}
