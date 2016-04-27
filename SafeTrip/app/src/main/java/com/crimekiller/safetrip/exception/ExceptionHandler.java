/**
 * 
 */
package com.crimekiller.safetrip.exception;

import android.app.AlertDialog;
import android.content.Context;

/**
 * @author  Wenlu Zhang
 * @AndrewID: wenluz
 * April 13, 2016
 *
 *
 */
public class ExceptionHandler {


	public void fixUserNotFound(Context mContext) {

		new AlertDialog.Builder(mContext)
				.setTitle("UserSearch：")
				.setMessage(" User cannot be found in the system !")
				.setNegativeButton("OK", null)
				.show();
	}

    public void fixAlreadyFriend(Context mContext) {

        new AlertDialog.Builder(mContext)
                .setTitle("FriendRequest：")
                .setMessage(" You Are Already Friends !")
                .setNegativeButton("OK", null)
                .show();
    }

    public void fixPendingFriend(Context mContext) {
        new AlertDialog.Builder(mContext)
                .setTitle("FriendRequest：")
                .setMessage("Already In Your Pending Request ! ")
                .setNegativeButton("OK", null)
                .show();
    }

    public void fixAlreadyRequest(Context mContext) {
        new AlertDialog.Builder( mContext )
                .setTitle("FriendRequest：")
                .setMessage("You have sent this Request before ! ")
                .setNegativeButton("OK", null)
                .show();
    }

    public void fixMissingRequiredFields(Context mContext) {
        new AlertDialog.Builder(mContext)
                .setTitle("Fill all required fields: ")
                .setMessage("Please enter the plate number and destination ! ")
                .setNegativeButton("OK", null)
                .show();
    }

}
