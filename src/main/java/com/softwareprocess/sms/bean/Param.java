package com.softwareprocess.sms.bean;

public class Param {
	private static String dir = "\\sms\\supermarket\\";
	private static int maxMoney = 1000;
	public static String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public static int getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(int maxMoney) {
		this.maxMoney = maxMoney;
	}

}
