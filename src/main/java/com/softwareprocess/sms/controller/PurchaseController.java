package com.softwareprocess.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.softwareprocess.sms.param.DataTableSendParam;
import com.softwareprocess.sms.service.CommonDatabaseService;
import com.softwareprocess.sms.service.FileUploadService;
import com.softwareprocess.sms.service.PurchaseService;
import com.softwareprocess.sms.tools.IDBuilder;
import com.softwareprocess.sms.tools.JsonUtil;

//采购模块
@Controller
@RequestMapping(value = "purchase")
public class PurchaseController {

	@Resource
	PurchaseService purchaseService;
	
	@Resource
	FileUploadService fileUploadService;
	
	@Resource
	CommonDatabaseService commonDatabaseService;

	IDBuilder idBuilder = new IDBuilder();

	@ResponseBody
	@RequestMapping(value = "getGoodStockList", produces = "application/json; charset=utf-8")
	public String getGoodStockList(HttpServletRequest request) {
		List<Map<String, Object>> resultCount = purchaseService.getGoodStockList(null);
		List<Map<String, Object>> result = purchaseService.getGoodStockList(request);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result).toJSON();
	}

	/**
	 * 添加进货单
	 * @param request
	 * @param pid
	 * @param rbatch
	 * @param rprofit
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addPurchase", produces = "application/json; charset=utf-8")
	public String addPurchase(HttpServletRequest request, 
			@RequestParam(value = "pid", required = false) String pid,
			@RequestParam(value = "rbatch", required = false) String rbatch,
			@RequestParam(value = "rprofit", required = false) String rprofit,
			@RequestParam(value = "files", required = false) MultipartFile file) {
		String resultCode = "error";
		HttpSession session = request.getSession();
		String eid = session.getAttribute("userID").toString();
		Map<String, Object> param = new HashMap<>();
		String rid = idBuilder.getRestockRecordID();
		// 文件处理
		String[] types = { "xls", "xlsx" };
		String savedDir = request.getServletContext().getRealPath("sms/supermarket/");
		String fileStatus = "";
		try {
			fileStatus = fileUploadService.fileSingleUpload(file, "restock", savedDir, 1024000, types);
		} catch (Exception e) {
			return e.getMessage();
		}
		param.put("rid", "'"+rid+"'");
		param.put("eid", "'"+eid+"'");
		param.put("pid", "'"+pid+"'");
		param.put("rbatch", "'"+rbatch+"'");
		param.put("rprofit", "'"+rprofit+"'");
		param.put("filePath", "'"+fileStatus+"'");
		param.put("rstatus", "'0'");
		param.put("rdate", "now()");
		int ist = commonDatabaseService.insertData("restock_record", param);
		if (ist>0) {
			resultCode = "success";
		}
		return JsonUtil.toJSON(resultCode);
	}
	
	/**
	 * 获取进货记录列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getRestockRecordList", produces = "application/json; charset=utf-8")
	public String getRestockRecordList(HttpServletRequest request) {
		List<Map<String, Object>> resultCount = purchaseService.getRestockRecordList(null);
		List<Map<String, Object>> result = purchaseService.getRestockRecordList(request);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result).toJSON();
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "getProviderList", produces = "application/json; charset=utf-8")
	public String getProviderList(HttpServletRequest request) {
		List<Map<String, Object>> result = purchaseService.getProviderList(request);
		return JsonUtil.toJSON(result);
	}

}
