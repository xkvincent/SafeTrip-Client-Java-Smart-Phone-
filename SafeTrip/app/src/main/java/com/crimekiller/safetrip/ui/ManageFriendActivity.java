package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.crimekiller.safetrip.R;

import java.util.ArrayList;

public class ManageFriendActivity extends Activity {
    private SearchView searchFriendsView;
    private Button viewPendingRequestsBtn;
    private ListView friendsListView;
    private ArrayList<String> friendsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_friends);
        friendsList = new ArrayList<String>();

        friendsList.add("aa");
        friendsList.add("bb");
        friendsList.add("cc");
        friendsList.add("dd");
        friendsList.add("ee");
        friendsList.add("ff");
        friendsList.add("gg");
        friendsList.add("hh");


        searchFriendsView=(SearchView) findViewById(R.id.searchFriendsView);
        searchFriendsView.setQueryHint("SearchView");

        viewPendingRequestsBtn = (Button) findViewById(R.id.ViewPendingRequestsBtn);
        viewPendingRequestsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendingRequest = new Intent( ManageFriendActivity.this,
                                                    PendingRequestActivity.class);
                startActivity(pendingRequest);
            }
        });

        friendsListView = (ListView) findViewById(R.id.friendslistview);
        FriendListAdapter adapter= new FriendListAdapter(friendsList);
        friendsListView.setAdapter(adapter);

    }

    private class FriendListAdapter extends ArrayAdapter<String> {

        public FriendListAdapter(ArrayList<String> objects){
            super(ManageFriendActivity.this, android.R.layout.simple_list_item_1,objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if( convertView == null ){
                convertView = ManageFriendActivity.this.getLayoutInflater()
                                .inflate(R.layout.item_friend_list,null);
            }
            String name = getItem(position);
            TextView friendListTextView = (TextView)
                                convertView.findViewById(R.id.FriendListTextView);
            friendListTextView.setText(name);
            return convertView;
        }
    }


}
