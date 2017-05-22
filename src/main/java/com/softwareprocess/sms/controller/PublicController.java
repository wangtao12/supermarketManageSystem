package com.softwareprocess.sms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softwareprocess.sms.service.CommonDatabaseService;
import com.softwareprocess.sms.service.PublicService;
import com.softwareprocess.sms.tools.JsonUtil;
import com.softwareprocess.sms.tools.StringUtil;

@Controller
public class PublicController {
	
	@Resource
	public PublicService publicService;
	@Resource
	CommonDatabaseService commonDatebaseService;
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
    
    /**
     * 用户注销
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return "success";
    }
    
    
    
    /**
     * 获取用户信息
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getEmployeeInfo")
    public String getEmployeeInfo(HttpServletRequest request, HttpServletResponse response) {
    	HttpSession session  = request.getSession();
    	System.out.println("进入控制器");
    	String eid = session.getAttribute("userID").toString();
    	Map<String, Object> result = publicService.getEmployeeInfo(eid);
        return JsonUtil.toJSON(result);
    }
    
    /**
     * 更新员工个人信息
     * @param request
     * @param response
     * @param eid  员工id
     * @param ename 员工姓名
     * @param esex 员工性别
     * @param epassword 员工密码
     * @param eaddress 员工地址
     * @param econtact 员工联系方式
     * @param ebirthyear 员工出生日期
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateEmployeeInfo")
    public String updateEmployeeInfo(HttpServletRequest request, HttpServletResponse response,
    		@RequestParam(value="eid") String eid,
    		@RequestParam(value="ename") String ename,
    		@RequestParam(value="esex") String esex,
    		@RequestParam(value="epassword") String epassword,
    		@RequestParam(value="eaddress") String eaddress,
    		@RequestParam(value="econtact") String econtact,
    		@RequestParam(value="ebirthyear") String ebirthyear){
    	String resultCode = "error";
    	Map<String,Object> param = new HashMap<>();
    	param.put("ename", ename);
    	param.put("esex", esex);
    	param.put("epassword", epassword);
    	param.put("eaddress", eaddress);
    	param.put("econtact", econtact);
    	param.put("ebirthyear", ebirthyear);
    	int update = commonDatebaseService.updateStringData("employee", "eid", eid, param);
    	if (update>0) {
			resultCode = "success";
		}
    	return JsonUtil.toJSON(resultCode);
    	
    }

}
