package com.softwareprocess.sms.service;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softwareprocess.sms.persistence.PublicMapper;

@Service
public class PublicService {
	
	@Autowired
	public PublicMapper publicMapper;
	
	/**
	 * 验证登录，获取登录用户信息
	 * @param userName  用户名
	 * @param password	密码
	 * @return
	 */
	public List<Map<String,Object>> checkLogin(String userName,String password){
		Hashtable<String, Object> param = new Hashtable<>();
		param.put("userName", userName);
		param.put("password", password);
		
		return publicMapper.checkLogin(param);
		
	}

	public List<Map<String, Object>> getUserAuthorityList(String userID) {
		// TODO Auto-generated method stub
		return publicMapper.getUserAuthorityList(userID);
	}

}
