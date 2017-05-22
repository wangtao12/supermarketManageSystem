package com.softwareprocess.sms.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softwareprocess.sms.param.DataTableSendParam;
import com.softwareprocess.sms.service.PurchaseService;
//采购模块
@Controller
@RequestMapping(value = "purchase")
public class PurchaseController {
	
	@Resource
	PurchaseService purchaseService;
	
	@ResponseBody
	@RequestMapping(value = "getGoodStockList",produces = "application/json; charset=utf-8")
	public  String getGoodStockList(HttpServletRequest request
			){
		List<Map<String, Object>> resultCount = purchaseService.getGoodStockList(null);
		List<Map<String, Object>> result = purchaseService.getGoodStockList(request);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result)
				.toJSON();
	}
	

}
