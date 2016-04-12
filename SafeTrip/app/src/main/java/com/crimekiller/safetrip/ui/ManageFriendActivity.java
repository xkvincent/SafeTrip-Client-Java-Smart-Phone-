package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.crimekiller.safetrip.Model.User;
import com.crimekiller.safetrip.R;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ManageFriendActivity extends Activity {
    private SearchView searchFriendsView;
    private Button viewPendingRequestsBtn;
    private ListView friendsListView;
    private ArrayList<User> friendsList;
    private Socket socket;

   // public final String LocalHost = "10.0.2.2";
   public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_manage_friend_activity);
        friendsList = new ArrayList<User>();
        connect();

        searchFriendsView=(SearchView) findViewById(R.id.searchFriendsView);
        searchFriendsView.setQueryHint("SearchView");

        viewPendingRequestsBtn = (Button) findViewById(R.id.ViewPendingRequestsBtn);
        viewPendingRequestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendingRequest = new Intent(ManageFriendActivity.this,
                        PendingRequestActivity.class);
                startActivity(pendingRequest);
            }
        });
    }

    private class FriendListAdapter extends ArrayAdapter<User> {

        public FriendListAdapter(ArrayList<User> objects){
            super(ManageFriendActivity.this, android.R.layout.simple_list_item_1,objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if( convertView == null ){
                convertView = ManageFriendActivity.this.getLayoutInflater()
                                .inflate(R.layout.item_friend_list,null);
            }

            User user = getItem(position);
            String name = user.getName();
            TextView friendListTextView = (TextView)
                                convertView.findViewById(R.id.FriendListTextView);
            friendListTextView.setText(name);
            return convertView;
        }
    }

    public void connect(){

            AsyncTask<Void,ArrayList<User>,Void> read = new AsyncTask<Void, ArrayList<User>, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        socket = new Socket(LocalHost, PORT);
                        objInputStream = new ObjectInputStream(socket.getInputStream());
                        objOutputStream = new ObjectOutputStream(socket.getOutputStream());

                        objOutputStream.writeObject("Manage Friend");

                        // Get response from server

                        try {
                            friendsList = (ArrayList<User>) objInputStream.readObject();
                            System.out.println(" Server Response: " + friendsList.size());

                            objOutputStream.close();
                            objInputStream.close();
                            socket.close();

                        } catch (IOException | ClassNotFoundException e) {
                            System.out.println("ClassNotFoundException ");
                            e.printStackTrace();
                        }

                        publishProgress(friendsList);

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(ArrayList<User>... values) {

                    friendsListView = (ListView) findViewById(R.id.friendslistview);
                    FriendListAdapter adapter= new FriendListAdapter(friendsList);
                    friendsListView.setAdapter(adapter);
                    super.onProgressUpdate(values);
                }
            };
        read.execute();
    }
}
