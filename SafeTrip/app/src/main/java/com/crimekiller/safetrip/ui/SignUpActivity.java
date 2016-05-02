package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.exception.AutoException;


/**
 * Created by xuvincent on 16/4/2.
 */
public class SignUpActivity extends AppCompatActivity {

    private Button signupButton;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;

    private String username;
    private String email;
    private String password;
    private String password2;

    private static String SIGN_UP_COMMAND = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_signup_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.SignUp_toolbar);
        setSupportActionBar(toolbar);

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

                //when twice password not same
                if(!password.equals(password2)){
                    try {
                        throw new AutoException(AutoException.ErrorInfo.DifferentPassword,
                                SignUpActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }
                }else if(password.length()>12||password.length()<6){
                    try {
                        throw new AutoException(AutoException.ErrorInfo.InValidPassword,
                                SignUpActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }
                }
                else if(!email.contains("@")){
                    try {
                        throw new AutoException(AutoException.ErrorInfo.InValidEmail,
                                SignUpActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }
                }
                else if( username.length() == 0  ){
                    try {
                        throw new AutoException(AutoException.ErrorInfo.InValidUserName,
                                SignUpActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }

                } else {
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

                return result;

            }

            @Override
            protected void onPostExecute(Boolean result) {
                if(result){
                    Intent intent = new Intent(SignUpActivity.this, UserPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("password", password);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    try {
                        throw new AutoException(AutoException.ErrorInfo.FailCreateUser
                                , SignUpActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }

                }
            }

        };
        read.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) // switch based on selected MenuItem's ID
        {
            case R.id.LogOutItem:
                // create an Intent to launch the AddEditContact Activity
                Intent logOut =
                        new Intent(SignUpActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }

}
