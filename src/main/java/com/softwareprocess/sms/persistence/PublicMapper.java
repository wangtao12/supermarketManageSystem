package com.softwareprocess.sms.persistence;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface PublicMapper {
	List<Map<String,Object>> checkLogin(Hashtable<String,Object> param);
	
	List<Map<String,Object>> getUserAuthorityList(String userID);

	Map<String, Object> getEmployeeInfo(Map<String, Object> param);

}
