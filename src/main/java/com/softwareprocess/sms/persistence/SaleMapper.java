package com.softwareprocess.sms.persistence;

import java.util.List;
import java.util.Map;

public interface SaleMapper {

	List<Map<String, Object>> getGoodList(Map<String, Object> param);

	List<Map<String, Object>> getGoodKindList();

	List<Map<String, Object>> getGoodInfo(Map<String, Object> param);

}
