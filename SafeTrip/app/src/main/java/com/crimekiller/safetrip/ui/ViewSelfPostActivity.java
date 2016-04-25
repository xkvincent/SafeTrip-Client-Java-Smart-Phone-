package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crimekiller.safetrip.DBLayout.DBConnector;
import com.crimekiller.safetrip.model.Post;
import com.crimekiller.safetrip .model.SimplePostAdapter;
import com.crimekiller.safetrip.R;

import java.util.ArrayList;

public class ViewSelfPostActivity extends AppCompatActivity {

    private ArrayList<Post> postList = new ArrayList<Post>();
    private ListView selfPostListView;
    private String username;
    private String password;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();//?  get the username from former activity
        bundle = intent.getExtras();
        username = bundle.getString("username");
        password = bundle.getString("password");
        Log.d("ViewSelfPostActivity", username);//?

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_view_self_post_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ViewSelfPost_toolbar);
        setSupportActionBar(toolbar);

        selfPostListView = (ListView) findViewById(R.id.ViewSelfPost_SelfPostList);
        new loadPostTask().execute();

        /*SimplePostAdapter adapter = new SimplePostAdapter(this, postList);
        selfPostListView.setAdapter(adapter);

        selfPostListView.setOnItemClickListener(viewDetailedPost);*/


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

            Bundle bundle = new Bundle();
            bundle.putString("username", username);
            bundle.putString("password", password);

            // pass the selected contact's row ID as an extra with the Intent
            bundle.putString("DATE", post.getDate());
            bundle.putString("PLATE", post.getLicensePlate());
            bundle.putString("DESTINATION", post.getDestination());
            bundle.putString("MODEL", post.getModel());
            bundle.putString("COLOR", post.getColor());
            bundle.putString("DEPARTURE", post.getDeparture());
            bundle.putString("OWNER",post.getOwner());

            viewDetailedPost.putExtras(bundle);
            startActivity(viewDetailedPost); // start the ViewContact Activity
        } // end method onItemClick

    }; // end viewContactListener



    private class loadPostTask extends AsyncTask<Long, Object, Cursor>
    {
        DBConnector databaseConnector = new DBConnector(ViewSelfPostActivity.this);

        // perform the database access
        @Override
        protected Cursor doInBackground(Long... params)
        {
            databaseConnector.open();

            // get a cursor containing all data on given entry
            return databaseConnector.getAllRecords();
        } // end method doInBackground

        // use the Cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor result)
        {
            super.onPostExecute(result);
            String owner;

            while (result.moveToNext()) {
                owner = result.getString(result.getColumnIndex("owner"));

                if (owner.equals(username)) {
                    String date = result.getString(result.getColumnIndex("date"));
                    String plate = result.getString(result.getColumnIndex("licenseplate"));
                    String destination = result.getString(result.getColumnIndex("destination"));
                    String model = result.getString(result.getColumnIndex("model"));
                    String color= result.getString(result.getColumnIndex("color"));
                    String departure = result.getString(result.getColumnIndex("departure"));

                    Post aPost = new Post(date, plate,destination,model,color,departure,owner);
                    postList.add(aPost);

                }

            }

            result.close(); // close the result cursor
            databaseConnector.close(); // close database connection

            SimplePostAdapter adapter = new SimplePostAdapter(ViewSelfPostActivity.this, postList);
            selfPostListView.setAdapter(adapter);

            selfPostListView.setOnItemClickListener(viewDetailedPost);
        } // end method onPostExecute
    } // end class LoadContactTask

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
                        new Intent(ViewSelfPostActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(ViewSelfPostActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }



}
