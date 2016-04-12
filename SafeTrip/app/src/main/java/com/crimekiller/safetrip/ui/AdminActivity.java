package com.crimekiller.safetrip.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

public class AdminActivity extends ListActivity {

    private SearchView searchUserView;
    private ListView userListView;
    private ArrayList<User> userList;
    private Socket socket;

    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_admin_activity);
        connect();

        searchUserView = (SearchView) findViewById(R.id.searchUserView);
        searchUserView.setQueryHint("SearchUser");
    }

    public void connect(){

        AsyncTask<Void,ArrayList<User>,Void> read = new AsyncTask<Void, ArrayList<User>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    socket = new Socket(LocalHost, PORT);
                    objInputStream = new ObjectInputStream(socket.getInputStream());
                    objOutputStream = new ObjectOutputStream(socket.getOutputStream());

                    objOutputStream.writeObject("Administrate User");

                    // Get response from server

                    try {
                        userList = (ArrayList<User>) objInputStream.readObject();
                        System.out.println(" Server Response: " + userList.size());

                        objOutputStream.close();
                        objInputStream.close();
                        socket.close();

                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("ClassNotFoundException ");
                        e.printStackTrace();
                    }

                    publishProgress(userList);

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(ArrayList<User>... values) {

                UserAdapter adapter = new UserAdapter(userList);
                setListAdapter(adapter);
                super.onProgressUpdate(values);
            }
        };
        read.execute();
    }

    private class UserAdapter extends ArrayAdapter<User> {

        public UserAdapter(ArrayList<User> users) {
            super(AdminActivity.this,android.R.layout.simple_list_item_1, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = AdminActivity.this.getLayoutInflater()
                        .inflate(R.layout.item_user_list, null);
            }
            User user = getItem(position);
            TextView userListTextView = (TextView) convertView.findViewById(R.id.UserListTextView);
            userListTextView.setText( user.getName() );

            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        // get user from the adapter
        // User user =  ((UserListViewAdapter) getListAdapter()).getItem(position) ;
        User user = (User)getListView().getItemAtPosition(position);
        Intent i = new Intent( AdminActivity.this, AdminUserPagerActivity.class);

        i.putExtra( AdminUserPagerFragment.USER_NAME, user.getName());
        startActivityForResult(i, 0);
    }


}
