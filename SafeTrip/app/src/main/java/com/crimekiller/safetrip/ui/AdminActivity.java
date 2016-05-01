package com.crimekiller.safetrip.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.exception.AutoException;
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
   // private ListView userListView;
    private ArrayList<User> userList;
    private String username;
    private Bundle bundle;
    private Button viewAllPost;

    private static String GET_USER_LIST_COMMAND = "Get User List";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();//  get the username from former activity
        bundle = intent.getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_admin_activity);

        username = bundle.getString("username");
        userList = new ArrayList<User>();

        connect();

        searchUserView = (SearchView) findViewById(R.id.searchUserView);
        searchUserView.setQueryHint("SearchUser");
        searchUserView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if( !userList.contains(query) )
                    try {
                        throw new AutoException(AutoException.ErrorInfo.UserNotFound, AdminActivity.this );
                    } catch (AutoException e) {
                        //Do nothing, handler has been invoked in the AutoException fix()
                    }

                searchUserView.setIconified(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewAllPost = (Button) findViewById(R.id.Admin_ViewAllPost);
        viewAllPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminViewAllPostActivity.class);
                i.putExtras(bundle);
                startActivity(i);
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
                System.out.println("On Progress Update");
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
                        new Intent(AdminActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(AdminActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }


}


