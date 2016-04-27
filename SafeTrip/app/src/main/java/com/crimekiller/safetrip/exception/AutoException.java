package com.crimekiller.safetrip.exception;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author  Wenlu Zhang
 * @AndrewID: wenluz
 * April 13, 2016
 *
 *
 */

public class AutoException extends Exception {

	private int errno;
	private String errorName;
	private Context mContext;

	public enum ErrorInfo{
		UserNotFound(0), AlreadyFriend(1), PendingFriend(2), AlreadyRequest(3),
		MissingRequiredFields(4);

		private int errno;
		ErrorInfo( int errno){
			this.errno = errno;
		}

		public int getErrno(){
			return this.errno;
		}
	}

	public AutoException( ErrorInfo error , Context mContext ){

		this.errno = error.getErrno();
		this.errorName = error.toString();
		this.mContext = mContext;
		fix(errno);
		log();
	}
	private void log(){
		Date date = new Date();
		Timestamp ts = new Timestamp( date.getTime() );
		StringBuffer buff = new StringBuffer();

		buff.append(ts.toString());
		buff.append("\t");
		buff.append("Error:" + this.errorName);
		buff.append("\n");

		File file = new File( mContext.getFilesDir(), "log.txt");
		try {
			BufferedWriter bw = new BufferedWriter( new OutputStreamWriter(
					new FileOutputStream( file, true )));
			bw.write( buff.toString() );
			bw.flush();
			bw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void fix(int errno) {
		ExceptionHandler handler = new ExceptionHandler();
		switch( errno ) {
			case 0:
				handler.fixUserNotFound(mContext);
				break;
            case 1:
				handler.fixAlreadyFriend(mContext);
                break;
            case 2:
				handler.fixPendingFriend(mContext);
                break;
            case 3:
				handler.fixAlreadyRequest(mContext);
			case 4:
				handler.fixMissingRequiredFields(mContext);
			default:
				break;
		}

	}
}
