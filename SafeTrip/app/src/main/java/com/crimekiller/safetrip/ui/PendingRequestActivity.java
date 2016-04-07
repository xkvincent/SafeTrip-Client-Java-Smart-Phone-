package com.crimekiller.safetrip.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.crimekiller.safetrip.R;

import java.util.ArrayList;

public class PendingRequestActivity extends Activity {

    private ArrayList<String> PendingRequest;
    private ListView requestListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_request);

        PendingRequest = new ArrayList<String>();
        PendingRequest.add("aaa");
        PendingRequest.add("bbb");

        requestListView = (ListView) findViewById(R.id.requestlistview);
        PendingRequestAdapter adapter = new PendingRequestAdapter(PendingRequest);
        requestListView.setAdapter(adapter);
    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        // get the student from the adapter
//        String name = ((PendingRequestAdapter)getListAdapter()).getItem(position);
//        Intent i = new Intent(PendingRequestActivity.this, StudentPagerActivity.class);
//
//        i.putExtra( StudentPagerFragment.EXTRA_SID, student.getSID());
//        startActivityForResult(i, 0);
//    }

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
}
