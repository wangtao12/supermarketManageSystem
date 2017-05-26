package com.softwareprocess.sms.controller;

import java.util.Date;
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

import com.softwareprocess.sms.param.DataTableSendParam;
import com.softwareprocess.sms.service.CommonDatabaseService;
import com.softwareprocess.sms.service.SaleService;
import com.softwareprocess.sms.tools.IDBuilder;
import com.softwareprocess.sms.tools.JsonUtil;
import com.softwareprocess.sms.tools.StringUtil;

//销售模块
@Controller
@RequestMapping(value = "sale")
public class SaleController {
	@Resource
	private SaleService saleService;
	@Resource
	private CommonDatabaseService commonDatabaseService;
	
	private IDBuilder idBuilder = new IDBuilder();
	
	
	
	/**
	 * 获取商品名称列表
	 * @param request
	 * @param goodname  商品名称  模糊匹配
	 * @param goodkind  商品种类ID
	 * @return  
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodList",produces = "application/json; charset=utf-8")
	public  String getGoodList(HttpServletRequest request,
			@RequestParam(value="goodkind",required = false) String goodkind
			){
		System.out.println("goodkind"+goodkind);
		List<Map<String, Object>> result = saleService.getGoodList(goodkind);
		return JsonUtil.toJSON(result);
	}
	
	/**
	 * 获取商品种类列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodKindList",produces = "application/json; charset=utf-8")
	public  String getGoodKindList(HttpServletRequest request
			){
		List<Map<String, Object>> result = saleService.getGoodKindList();
		return JsonUtil.toJSON(result);
	}
	
	/**
	 * 销售商品
	 * @param request
	 * @param saleList 销售商品列表  包括 商品ID 商品数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "SaleGoods",produces = "application/json; charset=utf-8")
	public  String SaleGoods(HttpServletRequest request,
			@RequestParam(value="saleList",required = false) String saleList
			){
		String resultCode = "error";
		HttpSession session = request.getSession();
		String eid = session.getAttribute("userID").toString();
		String snumber = idBuilder.getSaleNumber();  //单号
		List<Map<String, Object>> lSaleList;
		if (saleList==null) {
			return JsonUtil.toJSON(resultCode);
		}else{
			lSaleList = StringUtil.JsonToList(saleList); 
			for(Map<String,Object> m:lSaleList){    //取list集合里的那一条Map集合
	            for(String s:m.keySet()){    //取map集合里的String类型的key，
	                System.out.println(m.get(s));//根据key迭代输出value
	            }
	        }
		}
		for (int i = 0; i < lSaleList.size(); i++) {
			String sid = idBuilder.getSaleRecordID(); //销售记录id
			Map<String, Object> item = lSaleList.get(i);
			item.put("eid", eid);
			item.put("sid", sid);
			item.remove("gname");
			item.remove("counter");
			item.put("snumber", snumber);
			//item.put("money", money);
			Date time= new java.sql.Date(new java.util.Date().getTime());
			System.out.println(time);
			item.put("sdate", time.toString());
		}
		int insert = commonDatabaseService.insertStringDatas("sell_record", lSaleList);
		if (insert>0) {
			resultCode = snumber;
		}
		return JsonUtil.toJSON(resultCode);
	}
	
	
	/**
	 * 退货
	 * @param request
	 * @param snumber  销售单号
	 * @param gname	   商品名称
	 * @param count	 商品数量
	 * @param remark 退货说明
	 * @return  返回应退还金额 ，添加退货记录
	 */
	@ResponseBody
	@RequestMapping(value = "BackGoods",produces = "application/json; charset=utf-8")
	public  String BackGoods(HttpServletRequest request,
			@RequestParam(value="snumber",required = false) String snumber,
			@RequestParam(value="gname",required = false) String gname,
			@RequestParam(value="count",required = false) String count,
			@RequestParam(value="remark",required = false) String remark
			){
		String resultCode = "error";
		HttpSession session = request.getSession();
		String eid = session.getAttribute("userID").toString();
		String brid = idBuilder.getBackRecordID();
		float brmoney = 0;
		String gid = null;
		int brcount = Integer.valueOf(count);
		Date time= new java.sql.Date(new java.util.Date().getTime());
		List<Map<String, Object>> goodInfo = saleService.getGoodInfo(gname);
		if (goodInfo!= null && goodInfo.size()>0) {
			Map<String, Object> goodItem = goodInfo.get(0);
			float price = (float) goodItem.get("gprice");
			gid = goodItem.get("gid").toString();
			brmoney = brcount*price;
		}
		Map<String, Object> param = new HashMap<>();
		param.put("brid", brid);
		param.put("eid", eid);
		param.put("brmoney", brmoney);
		param.put("gid", gid);
		param.put("brdate", time);
		param.put("brnumber", snumber);
		param.put("brcount", brcount);
		param.put("brremark", remark);
		int  insert  = commonDatabaseService.insertStringData("back_record", param);
		if (insert>0) {
			resultCode = brmoney + "";
		}
		return JsonUtil.toJSON(resultCode);
	}
	/**
	 * 确认支付订单
	 * @param request
	 * @param snumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "confirmSale",produces = "application/json; charset=utf-8")
	public  String confirmSale(HttpServletRequest request,
			@RequestParam(value="snumber",required = false) String snumber){
		String resultCode = "error";
		int update = commonDatabaseService.updateSingleData("sell_record", "snumber", snumber, "status", "1");
		if (update>0) {
			
			resultCode = "success";
		}else if (update==0) {
			resultCode = "failure";
		}
		return JsonUtil.toJSON(resultCode);
	}
	
	/**
	 * 获取销售记录列表
	 * @param request
	 * @param snumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getSaleRecordList",produces = "application/json; charset=utf-8")
	public  String getSaleRecordList(HttpServletRequest request,
			@RequestParam(value="snumber",required = false) String snumber){
		HttpSession session = request.getSession();
		String userID = session.getAttribute("userID").toString();
		List<Map<String, Object>> resultCount = saleService.getSaleRecordList(null, userID,snumber);
		List<Map<String, Object>> result = saleService.getSaleRecordList(request, userID,snumber);
		return new DataTableSendParam(resultCount.get(0).get("sum"), resultCount.get(0).get("sum"), result)
				.toJSON();
	}
	
	
	/**
	 * 取消订单
	 * @param request
	 * @param snumber
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cancelSale",produces = "application/json; charset=utf-8")
	public  String cancelSale(HttpServletRequest request,
			@RequestParam(value="snumber",required = false) String snumber){
		String resultCode = "error";
		int del = commonDatabaseService.deleteSingleData("sell_record", "snumber", snumber);
		if (del>0) {
			resultCode = "success";
		}
		return JsonUtil.toJSON(resultCode);
	}
	


}
