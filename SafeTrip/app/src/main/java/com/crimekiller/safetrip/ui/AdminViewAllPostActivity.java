package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.client.DefaultSocketClient;
import com.crimekiller.safetrip.model.AdminPostAdapter;
import com.crimekiller.safetrip.model.Post;

import java.util.ArrayList;

public class AdminViewAllPostActivity extends AppCompatActivity {

    private String username;
    private Bundle bundle;
    private ListView AdminAllPostListView;
    private ArrayList<Post> postList = new ArrayList<Post>();
    private static String ADMIN_GET_ALLPOST_COMMAND = "Admin Get All Post List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_admin_view_all_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ViewAllPost_toolbar);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();//  get the username from former activity
        bundle = intent.getExtras();
        username = bundle.getString("username");

        AdminAllPostListView = (ListView) findViewById(R.id.Admin_All_Post_List);
        connect();

    }

    public void connect() {
        AsyncTask<Void, ArrayList<Post>, Void> read = new AsyncTask<Void, ArrayList<Post>, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DefaultSocketClient socketClient = new DefaultSocketClient(ADMIN_GET_ALLPOST_COMMAND,
                        username, null);
                socketClient.run();
                postList = socketClient.getPostList();
                publishProgress(postList);
                return null;
            }

            @Override
            protected void onProgressUpdate(ArrayList<Post>... values) {
                AdminPostAdapter adapter = new AdminPostAdapter(AdminViewAllPostActivity.this, postList);
                AdminAllPostListView.setAdapter(adapter);

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
                        new Intent(AdminViewAllPostActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(AdminViewAllPostActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }

}
