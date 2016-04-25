package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.model.AllPostAdapter;
import com.crimekiller.safetrip.model.Post;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ViewAllPostActivity extends AppCompatActivity {

    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;
    private String command = "Get All Post";

    private Socket socket;


    private ArrayList<Post> postList = new ArrayList<Post>();
    private ListView AllPostListView;
    private String username;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();//?  get the username from former activity
        bundle = intent.getExtras();
        username = bundle.getString("username");
        Log.d("AllPostActivity", username);//?
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ui_view_all_post_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ViewAllPost_toolbar);
        setSupportActionBar(toolbar);

        AllPostListView = (ListView) findViewById(R.id.ViewAllPost_AllPostList);
        loadAllPostTask();


    }


    public void loadAllPostTask()  {
        AsyncTask<Object, Object, Object> getAllPost =
                new AsyncTask<Object, Object, Object>()
                {
                    @Override
                    protected Object doInBackground(Object... params)
                    {

                        try {
                            socket = new Socket(LocalHost, PORT);
                            objInputStream = new ObjectInputStream(socket.getInputStream());
                            objOutputStream = new ObjectOutputStream(socket.getOutputStream());

                            objOutputStream.writeObject(command);
                            objOutputStream.flush();
                            objOutputStream.writeObject(username);
                            objOutputStream.flush();

                            // Get response from server
                            try {
                                //postList = (ArrayList<Post>)objInputStream.readObject();
                                ArrayList<Post> sortedPostList = new ArrayList<Post>();
                                sortedPostList = (ArrayList<Post>)objInputStream.readObject();
                                for (int i=sortedPostList.size()-1;i>=0;i--) {
                                    postList.add(sortedPostList.get(i));

                                }
                                System.out.println(" Server Response: " + postList.size());

                                objOutputStream.close();
                                objInputStream.close();
                                socket.close();

                            } catch (IOException | ClassNotFoundException e) {
                                System.out.println("ClassNotFoundException ");
                                e.printStackTrace();
                            }
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    } // end method doInBackground

                    @Override
                    protected void onPostExecute(Object result)
                    {
                        AllPostAdapter adapter = new AllPostAdapter(ViewAllPostActivity.this, postList);
                        AllPostListView.setAdapter(adapter);

                    } // end method onPostExecute
                }; // end AsyncTask

        // save the post to the database using a separate thread
        getAllPost.execute((Object[]) null);
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
                        new Intent(ViewAllPostActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(ViewAllPostActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }

}
