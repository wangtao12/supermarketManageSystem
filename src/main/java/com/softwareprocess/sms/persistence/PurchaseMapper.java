package com.softwareprocess.sms.persistence;

import java.util.List;
import java.util.Map;

public interface PurchaseMapper {

	List<Map<String, Object>> getGoodStockList(Map<String, Object> param);

	List<Map<String, Object>> getRestockRecordList(Map<String, Object> param);

	List<Map<String, Object>> getProviderList(Map<String, Object> param);

}
