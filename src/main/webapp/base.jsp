<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<%@ page import="java.util.*" %>

<%
    HttpSession sessionObject = request.getSession();
    String userAuthorityList = String.valueOf(sessionObject.getAttribute("userAuthorityList"));
%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" language="javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" />
    <!-- jqueryui -->
    <script type="text/javascript" src="<c:url value="/resources/jquery-ui/jquery-ui.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/jquery-ui/jquery-ui.chinese.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/jquery-ui/marspal.css"/>">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/icon.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/nav.css"/>" />
    <title>超市管理系统</title>
    <script type="text/javascript">
        $(function(){
            windowSize();
        });

        $(window).resize(function(){
            windowSize();
        });

        function windowSize() {
            var height = $(window).height();
            var width = $(window).width();
            var tabFrameHight = height-80;
            $("#tabs").css("height",tabFrameHight);
        }

        //iframe切换
        function go(url,obj){
            var item=$("div[name=list-item]");
            for(var i=0;i<item.length;i++){
                $(item[i]).removeClass("list-down");
            }
            $(obj).addClass("list-down");
            document.getElementById("mainFrame").src=url;
        }

        //注销
        function exit() {
            if (confirm("您确定要退出系统吗?")) {
                $.ajax({
                    type : "post",
                    url : "logout",
                    dataType : "text",
                    data:'',
                    asyn : false,
                    contentType : "application/x-www-form-urlencoded; charset=utf-8",
                    success : function(data) {
                        var result = data.replace(/(^\s*)|(\s*$)/g, "");
                        if(result == "success"){
                            window.location.href = "index.jsp";
                        } else {
                            custom.Msg.alert("注销失败！")
                        }
                    }
                })
            }
        }

        var tabs;
        $(function() {
            //初始化标签选择
            tabs = $("#tabs").tabs();
            // 关闭图标：当点击时移除标签页
            tabs.delegate("span.closebtn", "click", function() {
                var panelId = $(this).closest("li").attr("subid");
                $(this).closest("li").remove().attr("aria-controls");
                $("#" + panelId).remove();
                tabs.tabs("refresh");
            });
            $('.load').click();

            $('#projectSelect').on('click', function () {
                var text = $('#projectSelect').find('option:selected').html();
                alert(text);
            });
        })

        //新建标签页
        function addTab(label, url ,id) {
            if ($("#" + id).html() == undefined) {//如果已经打开就激活它
                //li中不能自定义id，这里使用subid代替
                var li = "<li subid='"+id+"'><a href='#"+id+"'>"
                    + label
                    + "</a> <span class='closebtn' style='float: left;' role='presentation'></span></li>";
                var tabContentHtml = '<iframe class="mainframe"  src="' + url + '" ></iframe>';
                tabs.find(".ui-tabs-nav").append(li);
                tabs.append( "<div id='" + id + "' class='subTab'>" + tabContentHtml + "</div>" );
                tabs.tabs("refresh");
            }
            existing = tabs.find("[subid='" + id + "']");
            var index = tabs.find(".ui-tabs-nav").find("li").index(existing);
            tabs.tabs("option", "active", index);
        }

        //弹出新窗口
        function addNewPopWindow(url){
            window.open(url);
        }
    </script>
</head>
<body style="overflow: hidden">
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="navbar-brand navlogo"></span>
            <a class="navbar-brand" href="#">超市管理系统</a>
        </div>
        <form class="navbar-form navbar-left" role="search">
            <form class="navbar-form navbar-left" role="search">

            </form>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#" onclick="addTab('个人中心','./views/personCenter/personCenter.jsp','personCenter')">个人中心</a></li>
                    <li><a href="#" onclick="exit()">注销</a></li>
                </ul>
                <!-- <form class="navbar-form navbar-right">
                  <input type="text" class="form-control" placeholder="功能搜索">
                </form> -->
            </div>
        </form>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-2 col-md-1 sidebar">
            <ul class="nav nav-sidebar">
                <% if(userAuthorityList.indexOf("管理") != -1 ) { %>
                <li><a href="#" onclick="addTab('员工管理','./views/manager/managerMain.jsp','managerMain')" >员工管理</a></li>
                <% } %>
                 <% if(userAuthorityList.indexOf("审批") != -1 ) { %>
                <li><a href="#" onclick="addTab('报销审批','./views/approval/approvalMain.jsp','approvalMain')" >报销审批</a></li>
                <li><a href="#" onclick="addTab('工资单审批','./views/approval/approvalSalary.jsp','approvalSalary')" >工资审批</a></li>
                <li><a href="#" onclick="addTab('进货单审批','./views/approval/approvalRestock.jsp','approvalRestock')" >进货审批</a></li>
                <% } %>
                <% if(userAuthorityList.indexOf("财务") != -1 ) { %>
                <li><a href="#" onclick="addTab('添加报销','./views/finance/addBill.jsp','addBill')" >添加报销</a></li>
                <li><a href="#" onclick="addTab('导出工资单','./views/finance/salary.jsp','salary')" >导出工资</a></li>
                <li><a href="#" onclick="addTab('添加工资单','./views/finance/addSalary.jsp','addSalary')" >添加工资</a></li>
                <li><a href="#" onclick="addTab('报销记录','./views/finance/billRecord.jsp','billRecord')">报销记录</a></li>
                <li><a href="#" onclick="addTab('工资单记录','./views/finance/salaryRecord.jsp','salaryRecord')">工资记录</a></li>
                <% } %>
                <% if(userAuthorityList.indexOf("销售")  != -1 ) { %>
                <li><a href="#" onclick="addTab('前台销售','./views/sale/saleMain.jsp','saleMain')">前台销售</a></li>
                <li><a href="#" onclick="addTab('销售记录','./views/sale/saleRecord.jsp','saleRecord')">销售记录</a></li>
                <% } %>
                <% if(userAuthorityList.indexOf("采购")  != -1) { %>
                <li><a href="#" onclick="addTab('查看库存','./views/purchase/purchaseMain.jsp','purchaseMain')" >查看库存</a></li>
                <li><a href="#" onclick="addTab('添加进货','./views/purchase/addPurchase.jsp','addPurchase')" >添加进货</a></li>
                <li><a href="#" onclick="addTab('进货记录','./views/purchase/purchaseRecord.jsp','purchaseRecord')" >进货记录</a></li>
                <li><a href="#" onclick="addTab('供应商管理','./views/purchase/provider.jsp','provider')" >供应商管理</a></li>
                <% } %>
            </ul>

        </div>
        <div class="col-sm-10 col-sm-offset-2 col-md-11 col-md-offset-1 main" style="padding: 0;">
            <div id="tabs" style="height: 100%;">
                <ul>
                    <!-- 初始标签 -->
                    <!-- <li><a href="#tabs0"></a></li> -->
                </ul>
                <!-- 初始标签内容 -->
                <!-- <d+-*iv id="tabs0" class='subTab'>
                    <iframe class="mainframe" src=""></iframe>
                </div> -->
            </div>
        </div>
    </div>
</div>

</body>
</html>