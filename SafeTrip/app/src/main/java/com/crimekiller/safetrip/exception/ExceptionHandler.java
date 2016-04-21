/**
 * 
 */
package com.crimekiller.safetrip.exception;

/**
 * @author  Wenlu Zhang
 * @AndrewID: wenluz
 * April 13, 2016
 *
 *
 */
public class ExceptionHandler {
	
	public String fixPriceMissing(){
		return String.valueOf(0);
	}
	
	public String fixInvalidBasePrice() {
		return String.valueOf(18000);
	}
	
	public String fixOptionSetMissing(){
		String ErrorMessage =" Missing Option Set";
		System.out.println(ErrorMessage);
		return ErrorMessage;
	}
	
	public String fixOptionMissing(){
		String ErrorMessage =" Missing Option";
		System.out.println(ErrorMessage);
		return ErrorMessage;
	}
	
	public void fixFileError() {
		// TODO Auto-generated method stub
		String ErrorMessage =" FileCanNotFound";
		System.exit(0);
	}

	public void fixEditChoiceMissing() {
		// TODO Auto-generated method stub
		
	}

	public void fixOptionSetNameNotFound() {
		// TODO Auto-generated method stub
		System.out.println("OptionSetName has not been found");
	}	
}
