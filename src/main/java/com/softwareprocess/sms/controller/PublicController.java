package com.softwareprocess.sms.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softwareprocess.sms.service.PublicService;
import com.softwareprocess.sms.tools.StringUtil;

@Controller
public class PublicController {
	
	@Resource
	public PublicService publicService;
    /**
     * 验证登录账号密码
     * @param request
     * @param userName   用户名
     * @param password 	   密码
     * @return
     * @throws Exception
     */
    @ResponseBody
	@RequestMapping(value = "loginCheck",produces = "application/json; charset=utf-8")
	public String LoginCheck(HttpServletRequest request, 
			@RequestParam(value="userName") String userName, 
    		@RequestParam(value="password") String password)throws Exception{
    	String resultCode = "error";
    	List<Map<String,Object>> result = publicService.checkLogin(userName, password);
    	if (result != null && result.size()>0) {
    		resultCode = "success";
			HttpSession session = request.getSession();
			
			String userID = result.get(0).get("eid").toString();
			//写入session
			session.setAttribute("userID", userID);
			session.setAttribute("userName", result.get(0).get("ename").toString());
			//获取用户权限
			List<Map<String,Object>> userAuthorityList = publicService.getUserAuthorityList(userID);
			StringUtil stringUtil = new StringUtil();
			String authorityString = stringUtil.convertListMapToString(userAuthorityList, "aname", ",");
			session.setAttribute("userAuthorityList", authorityString);
		}
		return resultCode;
	}
    
    

}
