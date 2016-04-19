package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crimekiller.safetrip.R;
import com.crimekiller.safetrip.DBLayout.DBConnector;
import com.crimekiller.safetrip.model.Post;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ViewDetailedSelfPostActivity extends AppCompatActivity {

    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;

    private Socket socket;
    private String command = "Delete Post";
    private String username;
    private ArrayList<Post> postList;

    private TextView dateTV, licensePlateTV, destinationTV, modelTV, colorTV, departureTV;
    private Post aPost = new Post();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_view_detailed_self_post_activity);

        Button deleteBtn = (Button) findViewById(R.id.DetailedSelfPost_Delete);

        dateTV = (TextView) findViewById(R.id.DetailedSelfPost_Date);
        licensePlateTV = (TextView) findViewById(R.id.DetailedSelfPost_Plate);
        destinationTV = (TextView) findViewById(R.id.DetailedSelfPost_Destination);
        modelTV = (TextView) findViewById(R.id.DetailedSelfPost_Model);
        colorTV = (TextView) findViewById(R.id.DetailedSelfPost_Color);
        departureTV = (TextView) findViewById(R.id.DetailedSelfPost_Departure);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.d("ViewDetailedSelfPost", username);

        String date = intent.getStringExtra("DATE");
        String licensePlate = intent.getStringExtra("PLATE");
        String destination = intent.getStringExtra("DESTINATION");
        String model = intent.getStringExtra("MODEL");
        String color = intent.getStringExtra("COLOR");
        String departure = intent.getStringExtra("DEPARTURE");
        String owner = intent.getStringExtra("OWNER");

        dateTV.setText(date);
        licensePlateTV.setText(licensePlate);
        destinationTV.setText(destination);
        modelTV.setText(model);
        colorTV.setText(color);
        departureTV.setText(departure);

        aPost = new Post(date, licensePlate, destination, model, color, departure, owner);

        deleteBtn.setOnClickListener(deleteButtonClicked);

    }

        View.OnClickListener deleteButtonClicked = new View.OnClickListener () {

            @Override
            public void onClick(View v)
            {

                AsyncTask<Object, Object, Object> savePostTask =
                        new AsyncTask<Object, Object, Object>()
                        {
                            @Override
                            protected Object doInBackground(Object... params)
                            {
                                //delete post from local database
                                deleteFromDataBase(aPost);

                                //add data to remote database
                                try {
                                    socket = new Socket(LocalHost, PORT);
                                    objInputStream = new ObjectInputStream(socket.getInputStream());
                                    objOutputStream = new ObjectOutputStream(socket.getOutputStream());

                                    objOutputStream.writeObject(command);
                                    objOutputStream.flush();
                                    objOutputStream.writeObject(aPost);
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
                                //finish(); // return to the previous Activity
                                Intent i = new Intent(ViewDetailedSelfPostActivity.this, ViewSelfPostActivity.class);
                                i.putExtra("username", username);
                                startActivity(i);
                            } // end method onPostExecute
                        }; // end AsyncTask

                // save the post to the database using a separate thread
                savePostTask.execute((Object[]) null);

            } // end method onClick
        }; // end OnClickListener saveButtonClicked


    private void deleteFromDataBase(Post post)
    {
        // get DatabaseConnector to interact with the SQLite database
        DBConnector dbConnector = new DBConnector(this);

        // insert post into local database
        dbConnector.deleteRecord(post);

    }






}
