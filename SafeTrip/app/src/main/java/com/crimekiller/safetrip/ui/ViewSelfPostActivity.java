package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crimekiller.safetrip.model.Post;
import com.crimekiller.safetrip .model.SimplePostAdapter;
import com.crimekiller.safetrip.R;

import java.util.ArrayList;

public class ViewSelfPostActivity extends AppCompatActivity {

    private ArrayList<Post> postList = new ArrayList<Post>();
    private ListView selfPostListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_self_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Post post1 = new Post("3/10/2016", "F1234", "SafeWay", "Camry", "red", "CMU");
        Post post2 = new Post("3/21/2016", "DA123", "BestBuy", "Ford", "black", "CMU");

        postList.add(post1);
        postList.add(post2);

        SimplePostAdapter adapter = new SimplePostAdapter(this, postList);

        selfPostListView = (ListView) findViewById(R.id.ViewSelfPost_SelfPostList); // get the built-in ListView
        selfPostListView.setAdapter(adapter);

        selfPostListView.setOnItemClickListener(viewDetailedPost);
    }

    AdapterView.OnItemClickListener viewDetailedPost = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)

        {
            // create an Intent to launch the ViewContact Activity
            Intent viewDetailedPost =
                    new Intent(ViewSelfPostActivity.this, ViewDetailedSelfPostActivity.class);

            Post post = postList.get(position);

            // pass the selected contact's row ID as an extra with the Intent
            viewDetailedPost.putExtra("DATE", post.getDate());
            viewDetailedPost.putExtra("PLATE", post.getLicensePlate());
            viewDetailedPost.putExtra("DESTINATION", post.getDestination());
            viewDetailedPost.putExtra("MODEL", post.getModel());
            viewDetailedPost.putExtra("COLOR", post.getColor());
            viewDetailedPost.putExtra("DEPARTURE", post.getDeparture());
            startActivity(viewDetailedPost); // start the ViewContact Activity
        } // end method onItemClick

    }; // end viewContactListener



}
