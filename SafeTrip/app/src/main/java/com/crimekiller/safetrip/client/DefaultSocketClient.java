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

	public DefaultSocketClient(String command, String username, String requestname){
		this. username =  username;
		this.command = command;
        this.requestname = requestname;
		this.friendsList = new ArrayList<User>();
        this.userList = new ArrayList<User>();
        this.pendingRequest = new ArrayList<String>();
        this.requestList = new ArrayList<String>();
	}

//    private String username;//??
//    private String password;//??
//    private Boolean result;//??
//    private String oldPassword;
//    private String newPassword;

//	public DefaultSocketClient(String command, String username, String password){ //???
//		this.command = command;
//		this.username = username;
//		this.password = password;
//		this.friendsList = new ArrayList<User>();
//		this.userList = new ArrayList<User>();
//	}

//	public DefaultSocketClient(String command,String username,
//								   String oldPassword, String newPassword){	//???
//		this.command = command;
//		this.username = username;
//		this.oldPassword = oldPassword; //!!在signup中看成email
//		this.newPassword = newPassword;	//!!在signup中看成password
////		this.friendsList = new ArrayList<User>();
////		this.userList = new ArrayList<User>();
//	}

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

//            }else if(command.equals("Login")){	//??
//				String data = username + ";" + password;
//				objOutputStream.writeObject(data);//不知道这一步对不对??
//				result = (Boolean)objInputStream.readObject();
//				String receive = (String)objInputStream.readObject();
//				String buf[] = receive.split(",");
				//username = (String)objInputStream.readObject();
				//password = (String)objInputStream.readObject();
//				username = buf[0];
//				password = buf[1];
//				System.out.println(" Username: " + username + "; Password:" + password);

//			}else if( command.equals("MyProfile") ){

//			}

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
            } else {
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

//	public String getUsername(){//??
//		return username;
//	}
//
//	public String getPassword(){//??
//		return password;
//	}

//	public Boolean getResult(){//??
//		return result;
//	}

}
