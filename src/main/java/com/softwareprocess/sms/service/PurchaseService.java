package com.softwareprocess.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareprocess.sms.param.DataTableReceiveParam;
import com.softwareprocess.sms.persistence.PurchaseMapper;

@Service
public class PurchaseService {
	@Autowired
	PurchaseMapper purchaseMapper;

	public List<Map<String, Object>> getGoodStockList(HttpServletRequest request) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		return purchaseMapper.getGoodStockList(param);
	}

}
