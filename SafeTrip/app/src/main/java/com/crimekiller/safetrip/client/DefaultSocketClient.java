/**
 * 
 */
package com.crimekiller.safetrip.client;

import android.util.Log;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

import com.crimekiller.safetrip.model.Post;
import com.crimekiller.safetrip.model.User;
/**
 * @author  Wenlu Zhang 
 * @AndrewID: wenluz
 * April 13, 2016
 *
 * 
 */
public class DefaultSocketClient extends Thread
								implements SocketClientInterface{

	private ObjectInputStream objInputStream = null;
	private ObjectOutputStream objOutputStream = null;

	private Socket socket;
	private String command;
	private String username;
    private String requestname;

	private ArrayList<User> friendsList;
    private ArrayList<User> userList;
    private ArrayList<String> pendingRequest;
    private ArrayList<String> requestList;
	private ArrayList<Post> postList;
	private ArrayList<String> locationList;
	private User user;

    private String password;
    private Boolean result;
	private String email;


	public DefaultSocketClient(String command, String username, String requestname){
		this. username =  username;
		this.command = command;
        this.requestname = requestname;

		this.user = new User();
		this.friendsList = new ArrayList<User>();
        this.userList = new ArrayList<User>();
        this.pendingRequest = new ArrayList<String>();
        this.requestList = new ArrayList<String>();
		this.postList = new ArrayList<Post>();
		this.locationList = new ArrayList<String>();

        password = requestname;
	}


	public DefaultSocketClient(String command,String username,
							   String email, String password) {
		// Need Consideration
        // When Sharing location, email = latitude, password = longtitude
        this.command = command;
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public void run(){
		if(openConnection()){
			handleSession();
			closeConnection();
		}
	}
	
	public boolean openConnection() {
				
		try {
				socket = new Socket( LocalHost, PORT );
				objInputStream = new ObjectInputStream( socket.getInputStream() );
				objOutputStream = new ObjectOutputStream( socket.getOutputStream() );
				
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void handleSession(){

        try {
			objOutputStream.writeObject(command);
            objOutputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get response from server
		try {
            if(command.equals(GET_FRIEND_LIST_COMMAND)){

				objOutputStream.writeObject(username);

			    friendsList = (ArrayList<User>) objInputStream.readObject();
				userList = (ArrayList<User>) objInputStream.readObject();
                pendingRequest = (ArrayList<String>) objInputStream.readObject();
                requestList = (ArrayList<String>) objInputStream.readObject();
			    System.out.println(" Server Response: " + userList.size());

            }else if(command.equals(GET_USER_LIST_COMMAND)){

                userList = (ArrayList<User>) objInputStream.readObject();

            }else if(command.equals(SEND_FRIEND_REQUEST_COMMAND)){
                // In this case the username = the name of the user to send friend Request to
                objOutputStream.writeObject( username );
                objOutputStream.flush();
                objOutputStream.writeObject(requestname);
                objOutputStream.flush();
                pendingRequest = (ArrayList<String>) objInputStream.readObject();

            }else if(command.equals(GET_PENDING_REQUEST_COMMAND)){

                objOutputStream.writeObject(username);
                pendingRequest = (ArrayList<String>) objInputStream.readObject();

            }else if( command.equals(ACCEPT_PENDING_REQUEST_COMMAND)){

                objOutputStream.writeObject(username);
                objOutputStream.flush();
                objOutputStream.writeObject(requestname);
                objOutputStream.flush();
                System.out.println("Accept pending request");

            }else if( command.equals(DECLINE_PENDING_REQUEST_COMMAND)){

                objOutputStream.writeObject(username);
                objOutputStream.flush();
                objOutputStream.writeObject(requestname);
                objOutputStream.flush();

            }else if (command.equals(ADMIN_GET_ALLPOST_COMMAND)){

                postList = (ArrayList<Post>)objInputStream.readObject();
                objOutputStream.flush();
                objOutputStream.writeObject(requestname);
                objOutputStream.flush();

            }else if (command.equals(TRACK_FRIEND_LIST_COMMAND)){

                objOutputStream.writeObject(username);
                objOutputStream.flush();
                objOutputStream.writeObject(requestname);
                objOutputStream.flush();
                locationList = (ArrayList<String>) objInputStream.readObject();

            }else if( command.equals(SHARE_LOCATION)){
                ArrayList<String >locationlist = new ArrayList<String>();
                //When Sharing location, parameters reused
                // email = longtitude
                // password = latitude

                locationlist.add(email);
                locationlist.add(password);

                objOutputStream.writeObject(username);
                objOutputStream.flush();
                objOutputStream.writeObject(locationlist);
                objOutputStream.flush();

                System.out.println("$$$$$$$$$$$$$ username " + username );
            }
            else if(command.equals(LOG_IN_COMMAND)) {

                String data = username;
                objOutputStream.writeObject(data);
                objOutputStream.flush();
                User user = (User) objInputStream.readObject();
                if (user != null) {
                    if (user.getPassword().equals(password)) {//if right password
                        result = true;
                    } else {
                        result = false;
                    }
                } else {// input unexit username
                    result = false;
                }

			}else if( command.equals("EditPassword") ){
				User user = new User();
				user.setName(username);
				user.setPassword(password);
				objOutputStream.writeObject(user);
				objOutputStream.flush();

			}else if(command.equals("SignUp")){//put a user into DB,and return boolean

				objOutputStream.writeObject(username);
				objOutputStream.flush();
				result = (Boolean)objInputStream.readObject();
				Log.d("get result", result.toString());
				if(result) {//success
					User user = new User();
					user.setName(username);
					user.setPassword(password);
					user.setEmail(email);
					objOutputStream.writeObject(user);
					objOutputStream.flush();

				}
			}else {
                System.out.println("Command Can Not be Found");
            }

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("ClassNotFoundException ");
			e.printStackTrace();
		}
	}

	public void closeConnection(){
		try {
				socket.close();
				objInputStream.close();
				objOutputStream.close();
	        }
	       catch (IOException e){
	    	   e.printStackTrace();
	       }       
	}

    public ArrayList<User> getUserList() {
        return userList;
    }

    public ArrayList<User> getFriendsList() {
        return friendsList;
    }

    public ArrayList<String> getPendingRequest() {
        return pendingRequest;
    }

    public ArrayList<String> getRequestList() {
        return requestList;
    }

	public String getUsername(){
		return username;
	}

    public User getUser(){ return user; }

	public Boolean getResult(){
		return result;
	}

    public ArrayList<Post> getPostList() { return postList; }

	public ArrayList<String> getLocationList() {
		return locationList;
	}
}
