package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crimekiller.safetrip.dblayout.DBConnector;
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
        setContentView(R.layout.ui_view_self_post_activity);

        selfPostListView = (ListView) findViewById(R.id.ViewSelfPost_SelfPostList);
        new loadPostTask().execute();

        SimplePostAdapter adapter = new SimplePostAdapter(this, postList);
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

            while (result.moveToNext()) {

                String date = result.getString(result.getColumnIndex("date"));
                String plate = result.getString(result.getColumnIndex("licenseplate"));
                String destination = result.getString(result.getColumnIndex("destination"));
                String model = result.getString(result.getColumnIndex("model"));
                String color= result.getString(result.getColumnIndex("color"));
                String departure = result.getString(result.getColumnIndex("departure"));

                Post aPost = new Post(date, plate,destination,model,color,departure,"");
                postList.add(aPost);
            }

            result.close(); // close the result cursor
            databaseConnector.close(); // close database connection
        } // end method onPostExecute
    } // end class LoadContactTask


}
