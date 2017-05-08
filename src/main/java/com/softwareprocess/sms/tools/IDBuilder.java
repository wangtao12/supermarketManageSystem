package com.softwareprocess.sms.tools;

import java.util.Random;

/**
 * 各种ID生成工具
 * @author w
 */
public class IDBuilder {
	
	
	/**
	 * 员工ID
	 * e-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getEmployeeID() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "e"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 任意长度伪随机数生成器
	 * @param strLength
	 * @return
	 */
	public static String getFixLenthString(int strLength) {
	    Random rm = new Random();  
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
	    String fixLenthString = String.valueOf(pross);  
	    return fixLenthString.substring(1, strLength + 1);  
	}
	

}
