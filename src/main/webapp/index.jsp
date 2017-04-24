<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
<link href="<c:url value="/resources/css/reset.css"/>"
	rel="stylesheet" type="text/css" media="screen" />
<link href="<c:url value="/resources/css/main.css"/>"
	rel="stylesheet" type="text/css" media="screen" />
	
<script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js"/>"></script>

<script src="http://api.asilu.com/cdn/jquery.js,jquery.backstretch.min.js" type="text/javascript"></script>


</head>
<body>
<body>
<script type="text/javascript">
$.backstretch([
		"<c:url value="/resources/images/login_bg3.jpg"/>"
	], {
		fade : 1000, // 动画时长
		duration : 2000 // 切换延时
});

function login(){
    var userName = $("#userName").val();
    var password = $("#password").val();
    if(userName==""||password==""){
        alert("登录信息不能为空!");
        return false;
    }
    $.ajax({
        type : "post",
        url : "loginCheck",
        dataType : "text",
        async : false,
        data : {
            "userName":userName,
            "password":password
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success : function(data) {
        	console.log(data)
            data=$.trim(data);
            if(data == 'success') {
                self.location.href = "base.jsp";
                alert("跳转中。。。");
            } else if(data == 'error') {
                alert("身份信息验证错误。");
            }
        }
    });
    
}
//回车事件
document.onkeydown=function(event){
    e = event ? event :(window.event ? window.event : null);
    if(e.keyCode==13){
        //执行的方法
        login();
    }
}
</script>
<div class="headerBar">
	<div class="logoBar login_logo">
		<div class="comWidth">
			<div class="logo fl">
				<a href="#"><img src="<c:url value="/resources/images/logo.png"/>" alt="超市管理系统"></a>
			</div>
			<h3 class="welcome_title">欢迎登陆超市管理系统</h3>
			
			萨达所大所大所大所多
		</div>
	</div>
</div>
<form>
<div class="loginBox">	
	<div class="login_cont">
		<ul class="login">
			<li class="l_tit">用户名 :</li>
			<li class="mb_10"><input type="text" id="userName" class="login_input user_icon"></li>
			<li class="l_tit">密 码 :</li>
			<li class="mb_10"><input type="password" id="password" class="login_input user_icon"></li>
			<li><input type="submit" onclick="login()" value="登录" class="login_btn" style="font-size:18px;text-align:center;background:#1e5566AA;color:#ffffff"></li>
		</ul>	
	</div>	
</div>
</form>
</body>
</html>