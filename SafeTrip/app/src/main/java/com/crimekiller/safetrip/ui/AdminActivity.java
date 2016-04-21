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


import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.model.User;
import com.crimekiller.safetrip.R;
import java.util.ArrayList;

/**
 * @author  Wenlu Zhang
 * @AndrewID: wenluz
 * April 13, 2016
 *
 *
 */
public class AdminActivity extends ListActivity {

    private SearchView searchUserView;
    private ListView userListView;
    private ArrayList<User> userList;
    private String username;

    private static String GET_USER_LIST_COMMAND = "Get User List";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_admin_activity);

        Intent intent = getIntent();//  get the username from former activity
        username = intent.getStringExtra("username");

        userList = new ArrayList<User>();

        connect();
        searchUserView = (SearchView) findViewById(R.id.searchUserView);
        searchUserView.setQueryHint("SearchUser");
        searchUserView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void connect() {

        AsyncTask<Void, ArrayList<User>, Void> read = new AsyncTask<Void, ArrayList<User>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(GET_USER_LIST_COMMAND,
                        username, null);
                socketClient.run();
                userList = socketClient.getUserList();
                publishProgress(userList);
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
            super(AdminActivity.this, android.R.layout.simple_list_item_1, users);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = AdminActivity.this.getLayoutInflater()
                        .inflate(R.layout.item_user_list, null);
            }
            User user = getItem(position);
            TextView userListTextView = (TextView) convertView.findViewById(R.id.UserListTextView);
            userListTextView.setText(user.getName());

            return convertView;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // get user from the adapter
        // User user =  ((UserListViewAdapter) getListAdapter()).getItem(position) ;
        User user = (User) getListView().getItemAtPosition(position);
        Intent i = new Intent(AdminActivity.this, AdminUserPagerActivity.class);
        //Static method to pass parameters among activities
        AdminUserPagerActivity.users = userList;
        //Using intent to pass parameters among activities
        i.putExtra(AdminUserPagerFragment.USER_NAME, user.getName());
        startActivityForResult(i, 0);
    }
}


