package com.softwareprocess.sms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareprocess.sms.persistence.SaleMapper;

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
	
	
	
	

}
