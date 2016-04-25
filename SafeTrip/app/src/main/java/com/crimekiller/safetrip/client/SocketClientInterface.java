/**
 * 
 */
package com.crimekiller.safetrip.client;

/**
 * @author  Wenlu Zhang
 * @AndrewID: wenluz
 * April 13, 2016
 *
 *
 */

public interface SocketClientInterface {

    static String GET_FRIEND_LIST_COMMAND = "Get Friend List";
    static String GET_USER_LIST_COMMAND = "Get User List";
    static String SEND_FRIEND_REQUEST_COMMAND = "Send Friend Request";
    static String GET_PENDING_REQUEST_COMMAND = "Get Pending Request";
    static String ACCEPT_PENDING_REQUEST_COMMAND = "Accept Pending Request";
    static String DECLINE_PENDING_REQUEST_COMMAND = "Decline Pending Request";
    static String LOG_IN_COMMAND = "Login";
    final String LocalHost = "10.0.2.2";
    final int PORT = 4000;

	public boolean openConnection();
	
    public void handleSession();
    
    public void closeConnection();

}
