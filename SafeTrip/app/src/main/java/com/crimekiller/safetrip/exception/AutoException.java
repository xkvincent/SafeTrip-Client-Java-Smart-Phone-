package com.crimekiller.safetrip.exception;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.Date;


public class AutoException extends Exception {
	
		File log = new File("log.txt");
		int errno;
		String name;
		
	public AutoException( ErrorInfo exception) {
		this.errno = exception.getErrorValue();
		this.name = exception.toString();
		fix( errno );
		log();
	}
	
	public enum ErrorInfo {
		PriceMissing(0),OptionSetMissing(1),OptionMissing(2),
		FileError(3), InvalidBasePrice(4), EditChoiceMissing(5), 
		OptionSetNameNotFound(6);
		
		private int error;
		
		private ErrorInfo( int error ){
			this.error = error;		
		}
		
		private int getErrorValue(){
			return error;
		}
	}
	
	public void log(){
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		StringBuffer output = new StringBuffer();
		output.append(ts.toString());
		output.append("\t");
		output.append("errno ");
		output.append(this.errno);
		output.append(":");
		output.append(this.name);
		output.append("\n");
		try {
			File file = new File("log.txt");
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
										(new FileOutputStream(file, true)));
				bw.write(output.toString());
				bw.flush();
				bw.close();
		} catch (IOException e) {
			System.out.println("Error -- " + e.toString());
		}
	}
	
	public void fix(int errno) {
		ExceptionHandler handler = new ExceptionHandler();
		switch (errno)
		{
		  case 0: handler.fixPriceMissing();
		  		  break;
		  case 1:handler.fixOptionSetMissing();
		  		  break;
		  case 2:handler.fixOptionMissing();
		  		  break;
		  case 3:handler.fixFileError();
		  		  break;
		  case 4:handler.fixInvalidBasePrice();
		  		  break;
		  case 5:handler.fixEditChoiceMissing();
		  		  break;
		  case 6:handler.fixOptionSetNameNotFound();
		  default:
			  break;
		}
	}
}
