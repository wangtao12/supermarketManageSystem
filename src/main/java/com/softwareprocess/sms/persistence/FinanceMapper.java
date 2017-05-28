package com.softwareprocess.sms.persistence;

import java.util.List;
import java.util.Map;

public interface FinanceMapper {

	List<Map<String, Object>> getSalary(Map<String, Object> param);

}
