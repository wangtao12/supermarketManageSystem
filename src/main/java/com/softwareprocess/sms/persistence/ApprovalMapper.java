package com.softwareprocess.sms.persistence;

import java.util.List;
import java.util.Map;

public interface ApprovalMapper {

	List<Map<String, Object>> getApprovalList(Map<String, Object> param);

	List<Map<String, Object>> getApprovalFileList(Map<String, Object> param);

	List<Map<String, Object>> getApprovalInfo(Map<String, Object> param);

	List<Map<String, Object>> getApprovalSalaryList(Map<String, Object> param);

	List<Map<String, Object>> getApprovalRestockList(Map<String, Object> param);

}
