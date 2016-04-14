/**
 * 
 */
package com.crimekiller.safetrip.client;

/**
 * @author  Wenlu Zhang 
 * @AndrewID: wenluz
 * Feb 17, 2016
 *
 * 
 */
public interface SocketClientInterface {
	
	public boolean openConnection();
	
    public void handleSession();
    
    public void closeConnection();

}
