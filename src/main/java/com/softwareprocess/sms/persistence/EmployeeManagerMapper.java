package com.softwareprocess.sms.persistence;

import java.util.List;
import java.util.Map;

public interface EmployeeManagerMapper {

	List<Map<String, Object>> getOperateAuthority(Map<String, Object> param);

	List<Map<String, Object>> getEmployeeList(Map<String, Object> param);

}
