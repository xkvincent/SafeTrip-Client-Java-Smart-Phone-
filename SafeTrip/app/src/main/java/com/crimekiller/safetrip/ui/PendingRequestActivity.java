package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;

import java.util.ArrayList;

public class PendingRequestActivity extends Activity {

    private ArrayList<String> PendingRequest;
    private ListView requestListView;
    private String username;
    private static String GET_PENDING_REQUEST_COMMAND = "Get Pending Request";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_pending_requesta_activity);

        Intent intent = getIntent();//  get the username from former activity
        username = intent.getStringExtra("username");
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
                        bundle.putString("requestUsername",name);

                        detailedPendingRequestIntent.putExtras(bundle);
                        startActivity(detailedPendingRequestIntent);
                    }
                });

            }
        };
        read.execute();
    }
}
