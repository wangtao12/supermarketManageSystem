package com.softwareprocess.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareprocess.sms.param.DataTableReceiveParam;
import com.softwareprocess.sms.persistence.ApprovalMapper;
import com.softwareprocess.sms.tools.ExcelUtil;
import com.softwareprocess.sms.tools.IDBuilder;
import com.softwareprocess.sms.tools.MapUtil;

@Service
public class ApprovalService {
	@Autowired
	ApprovalMapper approvalMapper;

	@Autowired
	CommonDatabaseService commonDatabaseService;

	IDBuilder idBuilder = new IDBuilder();

	

	public List<Map<String, Object>> getApprovalList(HttpServletRequest request, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "status", status);
		return approvalMapper.getApprovalList(param);
	}

	public List<Map<String, Object>> getApprovalFileList(HttpServletRequest request, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "status", status);
		return approvalMapper.getApprovalFileList(param);
	}

	public List<Map<String, Object>> getApprovalInfo(String rid) {
		Map<String, Object> param = new HashMap<>();
		param.put("rid", rid);
		List<Map<String, Object>> resultList = approvalMapper.getApprovalInfo(param);
		return resultList;
	}

	

	public List<Map<String, Object>> getGoodList(String gname) {
		Map<String, Object> gparam = new HashMap<>();
		gparam.put("gname", gname);
		System.out.println("查询商品信息");
		return approvalMapper.getGoodIdByName(gparam);
	}

	public List<Map<String, Object>> getApprovalSalaryList(HttpServletRequest request, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "status", status);
		return approvalMapper.getApprovalSalaryList(param);
	}

	public List<Map<String, Object>> getApprovalRestockList(HttpServletRequest request, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "status", status);
		return approvalMapper.getApprovalRestockList(param);
	}

	public List<Map<String, Object>> getGkidByName(String type) {
		return approvalMapper.getGkidByName(type);
	}

}
