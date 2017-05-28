package com.softwareprocess.sms.param;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * dataTables表格数据接收
 */
public class DataTableReceiveParam {
	/**
	 * 开始条数
	 */
	private String startNum = null;
	/**
	 * 每页条数
	 */
	private String pageLength = null;
	//private String maxNum = null;

	/**
	 * 构造函数
	 * @param request
	 */
	public DataTableReceiveParam(HttpServletRequest request) {
		super();
		if(request != null) {
			this.startNum = request.getParameter("start");
			this.pageLength = request.getParameter("length");
			//this.maxNum = String.valueOf(Integer.parseInt(startNum)+Integer.parseInt(pageLength));
		}
	}

	/**
	 * 构造函数
	 * @param startPage
	 * @param pageLength
	 */
	public DataTableReceiveParam(String startPage,String pageLength) {
		super();
		this.startNum = startPage;
		this.pageLength = pageLength;
		//this.maxNum = String.valueOf(Integer.parseInt(startNum)+Integer.parseInt(pageLength));
	}

	public void setDatabaseQuery(Map<String, Object> param) {
		param.put("startNum",startNum);
		param.put("pageLength",pageLength);
		//param.put("maxNum",maxNum);
	}

	public String getStartNum() {
		return startNum;
	}

	public void setStartNum(String startNum) {
		this.startNum = startNum;
	}

	public String getPageLength() {
		return pageLength;
	}

	public void setPageLength(String pageLength) {
		this.pageLength = pageLength;
	}
}
