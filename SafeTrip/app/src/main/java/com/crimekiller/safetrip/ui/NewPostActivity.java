package com.crimekiller.safetrip.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crimekiller.safetrip.dblayout.DBConnector;
import com.crimekiller.safetrip.exception.AutoException;
import com.crimekiller.safetrip.model.Post;
import com.crimekiller.safetrip.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewPostActivity extends AppCompatActivity {

    public final String LocalHost = "10.0.2.2";
    public final int PORT = 4000;
    private ObjectInputStream objInputStream = null;
    private ObjectOutputStream objOutputStream = null;

    private Socket socket;
    private String command = "New Post";
    private ArrayList<Post> postList;

    // EditTexts for post information
    private EditText licensePlateET;
    private EditText destinationET;
    private EditText modelET;
    private EditText colorET;
    private EditText departureET;

    private Button finishBtn;
    private String username;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_new_post_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.NewPost_toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();//?  get the username from former activity
        bundle = intent.getExtras();
        username = bundle.getString("username");//?
        Log.d("NewPostActivity", username);//?

        licensePlateET = (EditText) findViewById(R.id.NewPost_LicensePlate);
        destinationET = (EditText) findViewById(R.id.NewPost_Destination);
        modelET = (EditText) findViewById(R.id.NewPost_Model);
        colorET = (EditText) findViewById(R.id.NewPost_Color);
        departureET = (EditText) findViewById(R.id.NewPost_Departure);

        finishBtn = (Button) findViewById(R.id.NewPost_Finish);

        finishBtn.setOnClickListener(finishButtonClicked);

    }

    View.OnClickListener finishButtonClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            // test if the input is legal
            String testFlag = testInput();
            if (testFlag.equals("true"))// if legal input
            {
                AsyncTask<Object, Object, Object> savePostTask =
                        new AsyncTask<Object, Object, Object>()
                        {
                            @Override
                            protected Object doInBackground(Object... params)
                            {
                                Post newPost = obtainPost();
                                //add data to local database
                                addToDataBase(newPost);

                                //add data to remote database
                                try {
                                      socket = new Socket(LocalHost, PORT);
                                      objInputStream = new ObjectInputStream(socket.getInputStream());
                                      objOutputStream = new ObjectOutputStream(socket.getOutputStream());

                                      objOutputStream.writeObject(command);
                                      objOutputStream.flush();
                                      objOutputStream.writeObject(newPost);

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
                                Intent i = new Intent(NewPostActivity.this, ViewSelfPostActivity.class);
                                i.putExtras(bundle);
                                startActivity(i);
                            } // end method onPostExecute
                        }; // end AsyncTask

                        // save the post to the database using a separate thread
                        savePostTask.execute((Object[]) null);

            } else {
                
                try {
                    throw new AutoException(AutoException.ErrorInfo.MissingRequiredFields, NewPostActivity.this );
                } catch (AutoException e) {
                    //Do nothing, handler has been invoked in the AutoException fix()
                }// end else

            }

        } // end method onClick
    }; // end OnClickListener saveButtonClicked

    private String testInput(){

        String result;

        // test the post related information entered by user
        // if each input row is empty
        // it is illegal
        if (licensePlateET.getText().toString().length() == 0 ||destinationET.getText().toString().length() == 0 ){
            result = "missing fields";
        } else {
            result = "true";
        }

            return result;

    }

    private Post obtainPost()  {

        String date="";
        String licensePlate="";
        String destination= "";
        String model = "";
        String color = "";
        String departure = "";

        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm MM/dd/yyyy", Locale.US);

        date = dateformat.format(new Date());
        licensePlate = licensePlateET.getText().toString();
        destination = destinationET.getText().toString();
        model = modelET.getText().toString();
        color = colorET.getText().toString();
        departure = departureET.getText().toString();

        Post newPost = new Post(date, licensePlate,destination,model,color,departure,username);
        return newPost;

    }


    private void addToDataBase(Post post)
    {
        // get DatabaseConnector to interact with the SQLite database
        DBConnector dbConnector = new DBConnector(this);

        // insert post into local database
        dbConnector.insertRecord(post);

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
                        new Intent(NewPostActivity.this, MainActivity.class);

                startActivity(logOut); // start the Activity
                return true;

            case R.id.MainPageItem:
                Intent mainPage=
                        new Intent(NewPostActivity.this, UserPageActivity.class);
                mainPage.putExtras(bundle);

                startActivity(mainPage); // start the Activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } // end switch
    }



}
