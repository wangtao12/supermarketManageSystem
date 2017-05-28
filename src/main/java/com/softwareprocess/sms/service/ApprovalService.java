package com.softwareprocess.sms.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareprocess.sms.param.DataTableReceiveParam;
import com.softwareprocess.sms.persistence.ApprovalMapper;
import com.softwareprocess.sms.tools.ExcelUtil;
import com.softwareprocess.sms.tools.MapUtil;

@Service
public class ApprovalService {
	@Autowired
	ApprovalMapper approvalMapper;
	
	ExcelUtil excelUtil;

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

	public void opExcelInsert(String wid, String path) {
		Map<String, Object> param = new HashMap<>();
		param.put("wid", wid);
		List<Map<String, Object>> resultList = approvalMapper.getApprovalInfo(param);
		List<Map<String, Object>> insertData = new ArrayList<Map<String,Object>>(); 
		if (resultList!=null && resultList.size()>0) {
			Map<String, Object> resultItem = resultList.get(0);
			String wtype = resultItem.get("wtype").toString();
			String filePath = path+resultItem.get("filePath");
			if ("s".equals(wtype)) {
				try{
					excelUtil = new ExcelUtil(filePath);
					Map<Integer, Map<Integer, Object>> map = excelUtil.readExcelContent();
					for(int i=0;i<map.size();i++){
						Map<String, Object> insertItem = new HashMap<>();
						Map<Integer, Object> resItem = map.get(i);
					}
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
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

}
