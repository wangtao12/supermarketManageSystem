package com.softwareprocess.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareprocess.sms.param.DataTableReceiveParam;
import com.softwareprocess.sms.persistence.EmployeeManagerMapper;
import com.softwareprocess.sms.tools.MapUtil;

@Service
public class EmployeeManagerService {
	
	@Autowired
	private EmployeeManagerMapper employeeManagerMapper;
	/**
	 * 获取用户可操作权限
	 * @param userID 用户ID
	 * @return
	 */
	public List<Map<String, Object>> getOperateAuthority(String userID) {
		Map<String,Object> param = new HashMap<>();
		param.put("userID", userID);
		List<Map<String, Object>> operateAuthorityList = employeeManagerMapper.getOperateAuthority(param);
		return operateAuthorityList;
	}
	public void deleteAuthority(String eid) {
		
		
	}
	public List<Map<String, Object>> getEmployeeList(HttpServletRequest request, String name, String account) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "name", name);
		MapUtil.putMapParaEmpty(param, "account", account);
		
		return employeeManagerMapper.getEmployeeList(param);
	}

}
