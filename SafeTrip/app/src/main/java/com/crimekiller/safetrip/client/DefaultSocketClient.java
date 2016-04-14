/**
 * 
 */
package com.crimekiller.safetrip.client;

import com.crimekiller.safetrip.model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author  Wenlu Zhang 
 * @AndrewID: wenluz
 * April 13, 2016
 *
 * 
 */
public class DefaultSocketClient extends Thread
								implements SocketClientInterface{

	public final String LocalHost = "10.0.2.2";
	public final int PORT = 4000;
	private ObjectInputStream objInputStream = null;
	private ObjectOutputStream objOutputStream = null;

	private Socket socket;
	private String command;
	private ArrayList<User> friendsList;
    private ArrayList<User> userList;

	public DefaultSocketClient(String command){
		this.command = command;
		this.friendsList = new ArrayList<User>();
        this.userList = new ArrayList<User>();
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get response from server
		try {
            if(command.equals("Manage Friend")){
			    friendsList = (ArrayList<User>) objInputStream.readObject();
			    System.out.println(" Server Response: " + friendsList.size());
            }else if(command.equals("Administrate User")){
                userList = (ArrayList<User>) objInputStream.readObject();
                System.out.println(" Server Response: " + userList.size());
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

    public ArrayList<User> getFriends() {
        return friendsList;
    }
}
