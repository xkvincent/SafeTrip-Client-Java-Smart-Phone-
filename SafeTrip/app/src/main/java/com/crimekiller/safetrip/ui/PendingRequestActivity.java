package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;

import java.util.ArrayList;

public class PendingRequestActivity extends AppCompatActivity {

    private ArrayList<String> PendingRequest;
    private ListView requestListView;
    private String username;
    private String password;
    private Bundle bundle;
    private static String GET_PENDING_REQUEST_COMMAND = "Get Pending Request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_pending_requesta_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.PendingRequest_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();//  get the username from former activity
        bundle = intent.getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");
        PendingRequest = new ArrayList<String>();
        connect();

    }

    private class PendingRequestAdapter extends ArrayAdapter<String> {
        public PendingRequestAdapter(ArrayList<String> objects) {
            super(PendingRequestActivity.this, android.R.layout.simple_list_item_1, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = PendingRequestActivity.this.getLayoutInflater()
                        .inflate(R.layout.item_friend_request, null);
            }
            String name = getItem(position);
            TextView id_TextView =
                    (TextView)convertView.findViewById(R.id.PendingFriendNameTextView);
            id_TextView.setText( name );

            return convertView;
        }
    }

    public void connect(){

        AsyncTask<Void,ArrayList<String>,Void> read = new AsyncTask<Void, ArrayList<String>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(
                        GET_PENDING_REQUEST_COMMAND,username, null);
                socketClient.run();
                PendingRequest= socketClient.getPendingRequest();
                publishProgress(PendingRequest);
                return null;
            }

            @Override
            protected void onProgressUpdate(ArrayList<String>... values) {
                requestListView = (ListView) findViewById(R.id.requestlistview);
                PendingRequestAdapter adapter = new PendingRequestAdapter(PendingRequest);
                requestListView.setAdapter(adapter);
                super.onProgressUpdate(values);

                requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String name = PendingRequest.get(position);
                        Intent detailedPendingRequestIntent =
                                new Intent( PendingRequestActivity.this, DetailedPendingRequest.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("username",username);
                        bundle.putString("password",password);
                        bundle.putString("requestUsername",name);

                        detailedPendingRequestIntent.putExtras(bundle);
                        startActivity(detailedPendingRequestIntent);
                    }
                });

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
                        new Intent(PendingRequestActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(PendingRequestActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }
}
