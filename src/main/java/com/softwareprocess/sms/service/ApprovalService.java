package com.softwareprocess.sms.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareprocess.sms.param.DataTableReceiveParam;
import com.softwareprocess.sms.persistence.ApprovalMapper;
import com.softwareprocess.sms.tools.ExcelUtil;
import com.softwareprocess.sms.tools.IDBuilder;
import com.softwareprocess.sms.tools.MapUtil;

@Service
public class ApprovalService {
	@Autowired
	ApprovalMapper approvalMapper;
	
	@Autowired
	CommonDatabaseService commonDatabaseService;
	
	IDBuilder idBuilder = new IDBuilder();

	ExcelUtil excelUtil;

	public List<Map<String, Object>> getApprovalList(HttpServletRequest request, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "status", status);
		return approvalMapper.getApprovalList(param);
	}

	public List<Map<String, Object>> getApprovalFileList(HttpServletRequest request, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "status", status);
		return approvalMapper.getApprovalFileList(param);
	}

	public void opExcelInsert(String wid, String path) {
		Map<String, Object> param = new HashMap<>();
		param.put("wid", wid);
		List<Map<String, Object>> resultList = approvalMapper.getApprovalInfo(param);
		if (resultList != null && resultList.size() > 0) {
			Map<String, Object> resultItem = resultList.get(0);
			String wtype = resultItem.get("wtype").toString();
			String filePath = path + resultItem.get("filePath");
			try {
				excelUtil = new ExcelUtil(filePath);
				Map<Integer, Map<Integer, Object>> map = excelUtil.readExcelContent();
				for (int i = 0; i < map.size(); i++) {
					Map<Integer, Object> resItem = map.get(i);
					String gname = resItem.get(0).toString();
					String type = resItem.get(1).toString();
					float price = (float) resItem.get(2);
					int count = (int) resItem.get(3);
					String pddate = resItem.get(4).toString();
					int epdate = (int) resItem.get(5);
					float profit = (float) resItem.get(6);
					String gid;
					int gstock = 0;
					float orprice;
					float nprice;
					int nstock;
					List<Map<String, Object>> goodList = approvalMapper.getGoodIdByName(gname);
					if (goodList!=null && goodList.size()>0) {
						Map<String, Object> goodItem = goodList.get(0);
						gid = goodItem.get("gid").toString();
						orprice = (float) goodItem.get("gprice");
						gstock = (int) goodItem.get("gstock");
						nprice = ((orprice*gstock)+profit)/(gstock+count);
						nstock = gstock+count;
					}else{
						String gkid="";
						if (approvalMapper.getGkidByName(type)!=null) {
							gkid = approvalMapper.getGkidByName(type);
						}
						gid = idBuilder.getGoodID();
						nprice = price/count;
						nstock = count;
						Map<String, Object> ggkr = new HashMap<>();
						ggkr.put("gid", gid);
						ggkr.put("gkid", gkid);
						commonDatabaseService.insertStringData("p_gk_relation", ggkr);
					}
					Map<String, Object> insertGood = new HashMap<>();
					insertGood.put("gid", gid);
					insertGood.put("gname", gname);
					insertGood.put("gstock", nstock);
					insertGood.put("gprice", nprice);
					commonDatabaseService.insertStringData("good", insertGood);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public List<Map<String, Object>> getApprovalSalaryList(HttpServletRequest request, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "status", status);
		return approvalMapper.getApprovalSalaryList(param);
	}

	public List<Map<String, Object>> getApprovalRestockList(HttpServletRequest request, String status) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "status", status);
		return approvalMapper.getApprovalRestockList(param);
	}

}
