package com.softwareprocess.sms.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//审批模块
import org.springframework.web.bind.annotation.ResponseBody;

import com.softwareprocess.sms.bean.Param;
import com.softwareprocess.sms.param.DataTableSendParam;
import com.softwareprocess.sms.service.ApprovalService;
import com.softwareprocess.sms.service.CommonDatabaseService;
import com.softwareprocess.sms.tools.JsonUtil;

@Controller
@RequestMapping(value = "approval")
public class ApprovalController {
	
	@Resource
	ApprovalService approvalService;
	
	@Resource
	CommonDatabaseService commonDatabaseService;
	
	com.softwareprocess.sms.tools.FileOptUtil fileOptUtil = new com.softwareprocess.sms.tools.FileOptUtil();
	
	
	
	/**
	 * 获取审批列表
	 * @param request
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getApprovalList",produces = "application/json; charset=utf-8")
	public String getApprovalList(HttpServletRequest request,
			@RequestParam(value="status",required = false) String status
			){
		List<Map<String, Object>> resultCount = approvalService.getApprovalList(null, status);
		List<Map<String, Object>> result = approvalService.getApprovalList(request, status);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result)
				.toJSON();
		
	}
	
	/**
	 * 获取工资审批列表
	 * @param request
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getApprovalSalaryList",produces = "application/json; charset=utf-8")
	public String getApprovalSalaryList(HttpServletRequest request,
			@RequestParam(value="status",required = false) String status
			){
		List<Map<String, Object>> resultCount = approvalService.getApprovalSalaryList(null, status);
		List<Map<String, Object>> result = approvalService.getApprovalSalaryList(request, status);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result)
				.toJSON();
		
	}
	@ResponseBody
	@RequestMapping(value = "getApprovalRestockList",produces = "application/json; charset=utf-8")
	public String getApprovalRestockList(HttpServletRequest request,
			@RequestParam(value="status",required = false) String status
			){
		List<Map<String, Object>> resultCount = approvalService.getApprovalRestockList(null, status);
		List<Map<String, Object>> result = approvalService.getApprovalRestockList(request, status);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result)
				.toJSON();
	}
	
	
	/**
	 * 获取文件审批列表
	 * @param request
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getApprovalFileList",produces = "application/json; charset=utf-8")
	public String getApprovalFileList(HttpServletRequest request,
			@RequestParam(value="status",required = false) String status
			){
		List<Map<String, Object>> resultCount = approvalService.getApprovalFileList(null, status);
		List<Map<String, Object>> result = approvalService.getApprovalFileList(request, status);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result)
				.toJSON();
		
	}
	
	
	/**
	 * 审核报销
	 * @param request
	 * @param bid
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "updateApproval",produces = "application/json; charset=utf-8")
	public String updateApproval(HttpServletRequest request,
			@RequestParam(value="bid",required = false) String bid,
			@RequestParam(value="remark",required = false) String remark,
			@RequestParam(value="status",required = false) String status
			){
		String resultCode = "error";
		Map<String, Object> param = new HashMap<>();
		param.put("remark", remark);
		param.put("status", status);
		int udt = commonDatabaseService.updateStringData("bill", "bid", bid, param);
		if (udt>0) {
			resultCode = "success";
		}
		return JsonUtil.toJSON(resultCode);
	}
	
	
	/**
	 * 审批工资单
	 * @param request
	 * @param srid
	 * @param remark
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "approvalSalary",produces = "application/json; charset=utf-8")
	public String approvalSalary(HttpServletRequest request,
			@RequestParam(value="srid",required = false) String srid,
			@RequestParam(value="remark",required = false) String remark,
			@RequestParam(value="status",required = false) String status
			){
		String resultCode = "error";
		Map<String, Object> param = new HashMap<>();
		param.put("remark", remark);
		param.put("srstatus", status);
		int udt = commonDatabaseService.updateStringData("salary_record", "srid", srid, param);
		if (udt>0) {
			resultCode = "success";
		}
		return JsonUtil.toJSON(resultCode);
	}
	
	/**
	 * 审批进货单
	 * @param request
	 * @param srid
	 * @param remark
	 * @param status
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "approvalRestock",produces = "application/json; charset=utf-8")
	public String approvalRestock(HttpServletRequest request,
			@RequestParam(value="rid",required = false) String rid,
			@RequestParam(value="remark",required = false) String remark,
			@RequestParam(value="status",required = false) String status
			){
		String resultCode = "error";
		Map<String, Object> param = new HashMap<>();
		param.put("remark", remark);
		param.put("srstatus", status);
		int udt = commonDatabaseService.updateStringData("restock_record", "rid", rid, param);
		if (udt>0) {
			resultCode = "success";
		}
		if ("1".equals(status)) {
			String path = request.getServletContext().getRealPath("sms/supermarket/");
			approvalService.opExcelInsert(rid, path);
		}
		return JsonUtil.toJSON(resultCode);
	}
//	/**
//	 * 审核文件
//	 * @param request
//	 * @param bid
//	 * @param status
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "updateApprovalFile",produces = "application/json; charset=utf-8")
//	public String updateApprovalFile(HttpServletRequest request,
//			@RequestParam(value="wid",required = false) String wid,
//			@RequestParam(value="status",required = false) String status
//			){
//		String resultCode = "error";
//		int udt = commonDatabaseService.updateSingleData("waitconfirm", "wid", wid, "wstatus", status);
//		if (udt>0) {
//			resultCode = "success";
//		}
//		if ("1".equals(status)) {
//			String path = request.getServletContext().getRealPath("sms/supermarket/");
//			approvalService.opExcelInsert(wid,path);
//		}
//		return JsonUtil.toJSON(resultCode);
//	}
	
	
	
//	/**
//     * 下载工资单
//     * @param request
//     * @param response
//     * @throws Exception
//     */
//    //@ResponseBody
//    //@RequestMapping(value = "downloadApprovalFile", produces = "application/json; charset=utf-8",method = RequestMethod.POST)
//    public void downloadApprovalFile(HttpServletRequest request,
//                                     HttpServletResponse response,
//                                     @RequestParam(value = "filePath") String filePath
//                                     )throws Exception {
//    	System.out.println(filePath);
//    	String[] temp = filePath.split("/");
//    	String savedDir = request.getServletContext().getRealPath("sms/supermarket/");
//    	//String savedDir = "http://localhost:8080/supermarketManagementSystem/";
//    	String path = savedDir+temp[0];
//    	System.out.println(path);
//    	String name = temp[1];
//    	System.out.println(name);
//        fileOptUtil.download(response,request,path,name);
//    }
    
    /**
     * 下载文件
     * @param request
     * @param filePath
     * @param model
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value="downloadApprovalFile")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
    		@RequestParam(value = "filePath") String filePath,
            Model model)throws Exception {
       //下载文件路径
    	//System.out.println(filePath);
    	String mString = filePath.substring(1, filePath.length()-1);
    	//System.out.println(mString);
       String[] temp = mString.split("/");
       String path = request.getServletContext().getRealPath("sms/supermarket/")+temp[0];
      // System.out.println(path);
       File file = new File(path + File.separator + temp[1]);
       HttpHeaders headers = new HttpHeaders();  
       //下载显示的文件名，解决中文名称乱码问题  
       String downloadFielName = new String(temp[1].getBytes("UTF-8"),"iso-8859-1");
       System.out.println(downloadFielName);
       //通知浏览器以attachment（下载方式）打开图片
       headers.setContentDispositionFormData("attachment", downloadFielName); 
       //application/octet-stream ： 二进制流数据（最常见的文件下载）。
       headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
       return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
               headers, HttpStatus.CREATED);  
    }
	
	

}
