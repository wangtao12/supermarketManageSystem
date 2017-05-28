package com.softwareprocess.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareprocess.sms.param.DataTableReceiveParam;
import com.softwareprocess.sms.persistence.SaleMapper;
import com.softwareprocess.sms.tools.MapUtil;

@Service
public class SaleService {
	@Autowired
	private SaleMapper saleMapper;

	public List<Map<String, Object>> getGoodList( String goodkind) {
		// TODO Auto-generated method stub
		Map<String, Object> param  =  new HashMap<>();
		if (goodkind!=null) {
			param.put("gkID", goodkind);
		}else {
			param.put("gkID", "");
		}
		
		return saleMapper.getGoodList(param);
	}

	public List<Map<String, Object>> getGoodKindList() {

		return saleMapper.getGoodKindList();
	}

	public List<Map<String, Object>> getGoodInfo(String gname) {
		Map<String, Object> param  =  new HashMap<>();
		param.put("goodName", gname);
		return saleMapper.getGoodInfo(param);
	}

	public List<Map<String, Object>> getSaleRecordList(HttpServletRequest request, String userID, String snumber) {
		Map<String, Object> param = new HashMap<String, Object>();
		new DataTableReceiveParam(request).setDatabaseQuery(param);
		MapUtil.putMapParaEmpty(param, "userID", userID);
		MapUtil.putMapParaEmpty(param, "snumber", snumber);
		//MapUtil.putMapParaEmpty(param, "account", account);
		
		return saleMapper.getSaleRecordList(param);
	}

	public List<Map<String, Object>> getSaleDetail(String snumber) {
		// TODO Auto-generated method stub
		Map<String, Object> param = new HashMap<>();
		param.put("snumber", snumber);
		return saleMapper.getSaleDetail(param);
	}
	
	
	
	

}
