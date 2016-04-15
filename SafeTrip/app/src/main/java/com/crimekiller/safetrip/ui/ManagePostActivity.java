package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.crimekiller.safetrip.R;

public class ManagePostActivity extends AppCompatActivity implements View.OnClickListener {

    private Button newPost,allPost, selfPost;
    private Intent i;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_manage_post_activity);

        Intent intent = getIntent();//?  get the username from former activity
        username = intent.getStringExtra("username");//?
        Log.d("ManagePostActivity", username);//?

        newPost  = (Button) findViewById(R.id.ManagePost_NewPost);
        allPost = (Button) findViewById(R.id.ManegePost_ViewAllPost);
        selfPost = (Button) findViewById(R.id.ManagePost_ViewSelfPost);

        newPost.setOnClickListener(this);
        allPost.setOnClickListener(this);
        selfPost.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manage_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void onClick(View v) {

        switch (v.getId()){

            // for newPost button
            case R.id.ManagePost_NewPost:
                i = new Intent(this, NewPostActivity.class);
                break;

            // for viewAllPost button
            case R.id.ManegePost_ViewAllPost:
                i = new Intent(this, ViewAllPostActivity.class);
                break;

            // for viewSelfPost button
            case R.id.ManagePost_ViewSelfPost:
                i = new Intent(this, ViewSelfPostActivity.class);

        }
        // start activity
        i.putExtra("username", username);//?
        startActivity(i);
    }


}
