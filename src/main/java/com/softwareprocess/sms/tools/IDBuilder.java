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
	 * 工资记录ID
	 * sr-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getSalaryRecordID() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "sr"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 进货记录ID
	 * rr-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getRestockRecordID() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "rr"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 审批项ID
	 * wc-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getConfirmID() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "wc"+"-"+time+getFixLenthString(4);
		return id;
	}
	/**
	 * 文件ID
	 * file-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getProjectUploadDir() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "file"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 账单ID
	 * bill-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getBillID() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "bill"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 商品单号ID
	 * sn-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getSaleNumber() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "sn"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 销售记录ID
	 * sr-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getSaleRecordID() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "sr"+"-"+time+getFixLenthString(4);
		return id;
	}
	
	/**
	 * 退货记录ID
	 * br-[yyMMddhhmmss][4位随机数]
	 * @return
	 */
	public String getBackRecordID() {
		DateUtil dateUtil = new DateUtil();
		String time = dateUtil.getCurrentTime("yyMMddhhmmss");
		String id = "br"+"-"+time+getFixLenthString(4);
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
