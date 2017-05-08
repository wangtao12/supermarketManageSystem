package com.softwareprocess.sms.persistence;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public interface CommonDatabaseMapper {

	int updateSingleData(Map<String, Object> param);

	List<Map<String, Object>> getSingleData(Map<String, Object> param);

	int deleteSingleData(Map<String, Object> param);

	int insertStringData(Hashtable param);

	int insertStringDatas(Hashtable param);

	int insertData(Hashtable param);

	int insertDatas(Hashtable param);

	int updateStringData(Hashtable param);

	int updateData(Hashtable param);

}
