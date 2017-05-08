package com.softwareprocess.sms.param;

import com.softwareprocess.sms.tools.JsonUtil;

/**
 * dataTables表格数据
 */
public class DataTableSendParam {
	/**
	 * 记录总数
	 */
	private Object recordsTotal;
	/**
	 * 过滤条数
	 */
	private Object recordsFiltered;
	/**
	 * 数据
	 */
	private Object data;

	/**
	 * 构造函数
	 * @param recordsTotal 记录总数
	 * @param recordsFiltered 过滤条数
	 * @param data 数据
	 */
	public DataTableSendParam(Object recordsTotal, Object recordsFiltered, Object data) {
		super();
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsFiltered;
		this.data = data;
	}

	public Object getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Object recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Object getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	/**
	 * 将对象转换为JSON
	 * @return 对象JSON
	 */
	public String toJSON(){
		return JsonUtil.toJSON(this);
	}


}
