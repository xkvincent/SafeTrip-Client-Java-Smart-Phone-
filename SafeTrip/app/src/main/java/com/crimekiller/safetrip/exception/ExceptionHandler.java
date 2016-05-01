/**
 * 
 */
package com.crimekiller.safetrip.exception;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

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

    public void fixWrongLogIn(Context mContext){//main
        Toast.makeText(mContext, "Wrong Username or Password!",
                Toast.LENGTH_SHORT).show();
    }

    public void fixWrongAdmin(Context mContext){//userpage
        Toast.makeText(mContext, "You Are Not an Admin!",
                Toast.LENGTH_SHORT).show();
    }

    public void fixNotSameNewPassword(Context mContext){//editPassword

        Toast.makeText(mContext, "New password are not same! ",
                Toast.LENGTH_SHORT).show();
    }

    public void fixWrongOldPassword(Context mContext){//editPassword

        Toast.makeText(mContext, "Input Wrong Old Password!",
                Toast.LENGTH_SHORT).show();
    }

    public void fixDifferentPassword(Context mContext){//signUp
        Toast.makeText(mContext, "Input Different Password! ",
                Toast.LENGTH_SHORT).show();
    }

    public void fixFailCreateUser(Context mContext){//signUp
        Toast.makeText(mContext, "Fail to Create User!",
                Toast.LENGTH_SHORT).show();
    }

    public void fixInValidPassword(Context mContext){//signUp
        Toast.makeText(mContext, "The length of password must between 6 and 12!",
                Toast.LENGTH_SHORT).show();
    }

    public void fixInValidEmail(Context mContext){//signUp
        Toast.makeText(mContext, "Invalid Email Format! ",
                Toast.LENGTH_SHORT).show();
    }

    public void fixMissingRequiredFields(Context mContext) {
        new AlertDialog.Builder(mContext)
                .setTitle("Fill all required fields: ")
                .setMessage("Please enter the plate number and destination ! ")
                .setNegativeButton("OK", null)
                .show();
    }

    public void fixTrackError(Context mContext) {
        new AlertDialog.Builder(mContext)
                .setTitle("Track Error: ")
                .setMessage("You can not track people who are not your friends ! ")
                .setNegativeButton("OK", null)
                .show();
    }

    public void fixShareLocationError(Context mContext) {
        new AlertDialog.Builder(mContext)
                .setTitle("Track Error: ")
                .setMessage("Your Friend has not shared any location ! ")
                .setNegativeButton("OK", null)
                .show();
    }
}
