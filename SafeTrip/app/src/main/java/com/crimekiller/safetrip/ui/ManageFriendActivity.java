package com.crimekiller.safetrip.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.exception.AutoException;
import com.crimekiller.safetrip.model.User;
import com.crimekiller.safetrip.R;
import java.util.ArrayList;

public class ManageFriendActivity extends AppCompatActivity {


    private SearchView searchFriendsView;
    private Button viewPendingRequestsBtn;
    private TextView userName;
    private ListView friendsListView;

    private ArrayList<User> friendList;
    private ArrayList<User> userList;
    private ArrayList<String> pendingList;
    private ArrayList<String> requestList;

    private String username;
    private Bundle bundle;

    private static String GET_FRIEND_LIST_COMMAND = "Get Friend List";
    private static String SEND_FRIEND_REQUEST_COMMAND = "Send Friend Request" ;

    //custom defined String
    private final static String ALREADY_FRIEND = "Already Friend";
    private final static String PENDING_REQUEST = "Pending Friend";
    private final static String ALREADY_REQUEST = "Already Send Request";
    private final static String ALLOW_REQUEST = "Allow Request";
    private final static String USER_NOT_FOUND = "User Can Not Be Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_manage_friend_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ManageFriend_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();//  get the username from former activity
        bundle = intent.getExtras();
        username = bundle.getString("username");

        userName = (TextView) findViewById(R.id.username);
        userName.setText(username);

        friendList = new ArrayList<User>();
        userList = new ArrayList<User>();
        pendingList = new ArrayList<String>();
        requestList = new ArrayList<String>();

        connect();

        searchFriendsView=(SearchView) findViewById(R.id.searchFriendsView);
        searchFriendsView.setQueryHint("Search User");
        searchFriendsView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryUserName) {
                final String queryName = queryUserName;
                String res = findUser(queryUserName);
                switch ( res ) {
                    case ALREADY_FRIEND:
                        try {
                            throw new AutoException(AutoException.ErrorInfo.AlreadyFriend, ManageFriendActivity.this );
                        } catch (AutoException e) {
                            //Do nothing, handler has been invoked in the AutoException fix()
                        }
                        break;
                    case PENDING_REQUEST:
                        try {
                            throw new AutoException(AutoException.ErrorInfo.PendingFriend, ManageFriendActivity.this );
                        } catch (AutoException e) {
                            //Do nothing, handler has been invoked in the AutoException fix()
                        }
                        break;
                    case ALREADY_REQUEST:
                        try {
                            throw new AutoException(AutoException.ErrorInfo.AlreadyRequest, ManageFriendActivity.this );
                        } catch (AutoException e) {
                            //Do nothing, handler has been invoked in the AutoException fix()
                        }
                        break;
                    case USER_NOT_FOUND:
//                        new AlertDialog.Builder(ManageFriendActivity.this)
//                            .setTitle(" FriendRequest:  ")
//                            .setMessage(USER_NOT_FOUND)
//                            .setPositiveButton("OK", null)
//                            .show();
                        try {
                            throw new AutoException(AutoException.ErrorInfo.UserNotFound, ManageFriendActivity.this );
                        } catch (AutoException e) {
                            //Do nothing, handler has been invoked in the AutoException fix()
                        }
                        break;
                    case ALLOW_REQUEST:
                        new AlertDialog.Builder(ManageFriendActivity.this)
                            .setTitle("FriendRequest：")
                            .setMessage("Send Friend Request? ")
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ManageFriendActivity.this,
                                            "Request Has Been Sent", Toast.LENGTH_SHORT).show();
                                    sendFriendRequest(queryName);
                                }

                            }).show();
                        break;
                    default:
                        break;
                }
                searchFriendsView.setIconified(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewPendingRequestsBtn = (Button) findViewById(R.id.ViewPendingRequestsBtn);
        viewPendingRequestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendingRequest = new Intent(ManageFriendActivity.this,
                        PendingRequestActivity.class);
                pendingRequest.putExtras(bundle);//
                startActivity(pendingRequest);
            }
        });
    }

    private String findUser(String queryUserName) {
        if( pendingList.contains(queryUserName)){
            return PENDING_REQUEST;
        }
        if( requestList.contains(queryUserName)) {
            return ALREADY_REQUEST;
        }
        for( User friend:friendList){
            if( friend.getName().equals(queryUserName))
                return ALREADY_FRIEND;
        }
        for( User user: userList){
            if(user.getName().equals(queryUserName))
                return ALLOW_REQUEST;
        }
            return USER_NOT_FOUND;
    }


    private void sendFriendRequest( String queryUserName) {
        final String requestname = queryUserName;
        AsyncTask<Void,ArrayList<User>,Void> sendRequest = new AsyncTask<Void, ArrayList<User>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(SEND_FRIEND_REQUEST_COMMAND,
                                                                        username, requestname);
                socketClient.run();
                return null;
            }
        };
        sendRequest.execute();
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
                    DefaultSocketClient socketClient = new DefaultSocketClient(
                                                        GET_FRIEND_LIST_COMMAND,username, null);
                    socketClient.run();
                    friendList = socketClient.getFriendsList();
                    userList = socketClient.getUserList();
                    pendingList = socketClient.getPendingRequest();
                    requestList = socketClient.getRequestList();
                    publishProgress(friendList,userList);
                    return null;
                }

                @Override
                protected void onProgressUpdate(ArrayList<User>... values) {

                    friendsListView = (ListView) findViewById(R.id.friendslistview);
                    FriendListAdapter adapter= new FriendListAdapter(friendList);
                    friendsListView.setAdapter(adapter);
                    super.onProgressUpdate(values);
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
                        new Intent(ManageFriendActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(ManageFriendActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }
}
