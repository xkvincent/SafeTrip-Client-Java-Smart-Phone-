package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();//?  get the username from former activity
        username = intent.getStringExtra("username");//?
        Log.d("AllPostActivity", username);//?
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ui_view_all_post_activity);

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
                                postList = (ArrayList<Post>)objInputStream.readObject();
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

}
