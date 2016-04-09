package com.crimekiller.safetrip.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.crimekiller.safetrip.model.User;
import com.crimekiller.safetrip.R;

import java.util.ArrayList;

public class AdminActivity extends ListActivity {

    private SearchView searchUserView;
    private ListView userListView;
    private ArrayList<User> userList;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_admin_activity);

        userList = new ArrayList<User>();
        userList = User.getUser();

        UserAdapter adapter = new UserAdapter(userList);
        setListAdapter(adapter);

        searchUserView = (SearchView) findViewById(R.id.searchUserView);
        searchUserView.setQueryHint("SearchUser");
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
//        User user =  ((UserListViewAdapter) getListAdapter()).getItem(position) ;
        User user = (User)getListView().getItemAtPosition(position);
        Intent i = new Intent( AdminActivity.this, AdminUserPagerActivity.class);

        i.putExtra( AdminUserPagerFragment.USER_NAME, user.getName());
        startActivityForResult(i, 0);
    }
}
