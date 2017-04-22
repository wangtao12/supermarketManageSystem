//全局的ajax访问，处理ajax清求时sesion超时
$.ajaxSetup({
    contentType:"application/x-www-form-urlencoded;charset=utf-8",
    complete:function(XMLHttpRequest,textStatus){
        var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，
        if(sessionstatus=="timeout"){
            alert("登录超时，请重新登录！");
            window.location.replace("index.jsp");
        }
    }
});
