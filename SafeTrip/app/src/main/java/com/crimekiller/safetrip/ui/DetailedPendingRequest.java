package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.model.User;

import java.util.ArrayList;

public class DetailedPendingRequest extends AppCompatActivity {

    private String username;
    private String password;
    private String requestUsername;
    private TextView usernameTextView;
    private Button acceptBtn;
    private Button declineBtn;

    private static String ACCEPT_PENDING_REQUEST_COMMAND = "Accept Pending Request";
    private static String DECLINE_PENDING_REQUEST_COMMAND = "Decline Pending Request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_detailed_pending_request);

        Toolbar toolbar = (Toolbar) findViewById(R.id.DetailedPendingRequest_toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = this.getIntent().getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");
        requestUsername = bundle.getString("requestUsername");

        usernameTextView = (TextView) findViewById(R.id.usernameTextView );
        usernameTextView.setText(requestUsername);

        acceptBtn = (Button) findViewById(R.id.acceptBtn);
        declineBtn = (Button) findViewById(R.id.declineBtn);

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(ACCEPT_PENDING_REQUEST_COMMAND);
                Intent intent =new Intent(DetailedPendingRequest.this,ManageFriendActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("username", username);
                bundle1.putString("password", password);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(DECLINE_PENDING_REQUEST_COMMAND);
                Intent intent =new Intent(DetailedPendingRequest.this,ManageFriendActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("username", username);
                bundle2.putString("password", password);
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
    }
    public void connect( String command ){
        final String com = command;
        AsyncTask<Void,ArrayList<User>,Void> handleRequest = new AsyncTask<Void, ArrayList<User>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(
                        com,username, requestUsername );
                socketClient.run();
                return null;
            }
        };
        handleRequest.execute();
    }

}
