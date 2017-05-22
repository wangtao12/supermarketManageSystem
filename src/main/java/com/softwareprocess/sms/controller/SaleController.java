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

import com.softwareprocess.sms.service.CommonDatabaseService;
import com.softwareprocess.sms.service.SaleService;
import com.softwareprocess.sms.tools.IDBuilder;
import com.softwareprocess.sms.tools.JsonUtil;

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
	 * @param request
	 * @param saleList 销售商品列表  包括 商品ID 商品数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "SaleGoods",produces = "application/json; charset=utf-8")
	public  String SaleGoods(HttpServletRequest request,
			@RequestParam(value="saleList",required = false) List<Map<String, Object>> saleList
			){
		String resultCode = "error";
		String snumber = idBuilder.getSaleNumber();
		for (int i = 0; i < saleList.size(); i++) {
			String sid = idBuilder.getSaleRecordID();
			Map<String, Object> item = saleList.get(i);
			item.put("sid", sid);
			item.put("snumber", snumber);
			Date time= new java.sql.Date(new java.util.Date().getTime());
			item.put("sdate", time);
		}
		int insert = commonDatabaseService.insertStringDatas("sale_record", saleList);
		if (insert>0) {
			resultCode = "success";
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

}
