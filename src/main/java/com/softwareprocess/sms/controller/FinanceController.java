package com.softwareprocess.sms.controller;

import java.io.File;
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

import com.softwareprocess.sms.bean.Param;
import com.softwareprocess.sms.param.DataTableSendParam;
import com.softwareprocess.sms.service.CommonDatabaseService;
import com.softwareprocess.sms.service.FileUploadService;
import com.softwareprocess.sms.service.FinanceService;
import com.softwareprocess.sms.tools.IDBuilder;
import com.softwareprocess.sms.tools.JsonUtil;

//财务管理模块
@Controller
@RequestMapping(value = "finance")
public class FinanceController {
	@Resource
	private CommonDatabaseService commonDatabaseService;

	@Resource
	private FinanceService financeService;

	@Resource
	private FileUploadService fileUploadService;

	private IDBuilder idBuilder = new IDBuilder();

	/**
	 * 添加报销
	 * 
	 * @param request
	 * @param bmoney
	 * @param btype
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addBill", produces = "application/json; charset=utf-8")
	public String addBill(HttpServletRequest request, @RequestParam(value = "bmoney", required = false) String bmoney,
			@RequestParam(value = "btype", required = false) String btype) {
		String resultCode = "error";
		String bid = idBuilder.getBillID();
		HttpSession session = request.getSession();
		String userId = session.getAttribute("userID").toString();
		Map<String, Object> param = new HashMap<>();
		param.put("bid", "'" + bid + "'");
		param.put("eid", "'" + userId + "'");
		param.put("btype", "'" + btype + "'");
		param.put("bstatus", "'0'");
		param.put("bmoney", "'" + bmoney + "'");
		param.put("bdate", "now()");
		int ist = commonDatabaseService.insertData("bill", param);
		if (ist > 0) {
			resultCode = "success";
		}
		return JsonUtil.toJSON(resultCode);
	}

	/**
	 * 上传工资单
	 * @param request
	 * @param month
	 * @param count
	 * @param money
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addSalary", produces = "application/json; charset=utf-8")
	public String addSalary(HttpServletRequest request, @RequestParam(value = "month", required = false) String month,
			@RequestParam(value = "count", required = false) String count,
			@RequestParam(value = "money", required = false) String money,
			@RequestParam(value = "files", required = false) MultipartFile file) {
		String resultCode = "error";
		String srid = idBuilder.getSalaryRecordID();
		HttpSession session = request.getSession();
		String userId = session.getAttribute("userID").toString();
		// 文件处理
		String[] types = { "xls", "xlsx" };
		String savedDir = request.getServletContext().getRealPath("sms/supermarket/");
		String fileStatus = "";
		try {
			fileStatus = fileUploadService.fileSingleUpload(file, "salary", savedDir, 1024000, types);
		} catch (Exception e) {
			return e.getMessage();
		}
		System.out.println(money);
		Map<String, Object> param = new HashMap<>();
		param.put("srid", "'" + srid + "'");
		param.put("eid", "'" + userId + "'");
		param.put("count", "'" + count + "'");
		param.put("money", "'" + money + "'");
		param.put("month", "'" + month + "'");
		param.put("filePath", "'" + fileStatus + "'");
		param.put("srstuatus", "'0'");
		param.put("srdata", "now()");
		int ist = commonDatabaseService.insertData("salary_record", param);
		if (ist > 0) {
			resultCode = "success";
		}
		return JsonUtil.toJSON(resultCode);
	}

	/**
	 * 获取工资列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSalary", produces = "application/json; charset=utf-8")
	public String getSalary(HttpServletRequest request) {
		List<Map<String, Object>> resultCount = financeService.getSalary(null);
		List<Map<String, Object>> result = financeService.getSalary(request);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result).toJSON();
	}

//	/**
//	 * 上传工资单
//	 * 
//	 * @param request
//	 * @param file
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "addSalary", produces = "application/json; charset=utf-8")
//	public String addSalary(HttpServletRequest request,
//			@RequestParam(value = "files", required = false) MultipartFile file) {
//		String resultCode = "error";
//		Map<String, Object> param = new HashMap<>();
//		HttpSession session = request.getSession();
//		String eid = session.getAttribute("userID").toString();
//		String wid = idBuilder.getConfirmID();
//		// 文件处理
//		String[] types = { "xls", "xlsx" };
//		// String fileUploadID = idBuilder.getProjectUploadDir();
//		String savedDir = request.getServletContext().getRealPath("sms/supermarket/");
//		String fileStatus = "";
//		try {
//			fileStatus = fileUploadService.fileSingleUpload(file, "salary", savedDir, 1024000, types);
//			System.out.println(fileStatus);
//		} catch (Exception e) {
//			return e.getMessage();
//		}
//		param.put("wid", "'" + wid + "'");
//		param.put("wtype", "'s'");
//		param.put("eid", "'" + eid + "'");
//		param.put("wdate", "now()");
//		param.put("wstatus", "'0'");
//		param.put("filePath", "'" + fileStatus + "'");
//		int ist = commonDatabaseService.insertData("waitconfirm", param);
//		resultCode = "success";
//		return JsonUtil.toJSON(resultCode);
//	}

}
