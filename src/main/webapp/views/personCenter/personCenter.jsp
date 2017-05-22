<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@include file="../resources.jsp" %>
<title></title>
</head>
<body>
<div  style="margin: 10px;">
    <h4 style = "margin-left:128px">个人资料：</h4>
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="basicInfoArea">
            <form class="form-horizontal" style="margin: 20px;" id="basicInfoForm">
                <div class="form-group">
                    <label class="col-sm-2 control-label">姓名</label>
                    <div class="col-sm-10">
                        <input type="text" class="DataInput form-control" name="ename" style="width: 300px;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">性别</label>
                    <div class="col-sm-10">
                        <select class="DataInput form-control" name="esex" style="width: 300px;">
                            <option value="m">男</option>
                            <option value="f">女</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">密码 (默认为123456)</label>
                    <div class="col-sm-10">
                        <input type="text" class="DataInput form-control" name="epassword" style="width: 300px;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">家庭住址</label>
                    <div class="col-sm-10">
                        <input type="text" class="DataInput form-control" name="eaddress" style="width: 300px;">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">联系方式</label>
                    <div class="col-sm-10">
                        <input type="text" class="DataInput form-control" name="econtact" style="width: 300px;">
                    </div>
                </div>
                
                <div class="form-group">
                    <label class="col-sm-2 control-label">生日</label>
                    <div class="col-sm-10">
                        <input type="text" class="formDatetime DataInput form-control" name="ebirthyear" style="width: 300px;">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-primary" onclick="submitAddBoxData()">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

	<script type="text/javascript">
    var editUserID = "";
    $(document).ready(function() {
        //日期选择初始化
        $(".formDatetime").datetimepicker({
            minView:"month",
            format: "yyyy-mm-dd",//显示格式
            todayBtn: true,//当前日期的按钮
            todayHighlight:true,//当前日前是否高亮
            language:'zh-CN', //语言选择
            autoclose: true //关闭时间的选择
        });
        $.ajax({
            type : "post",
            url : "<c:url value="/getEmployeeInfo"/>",
            dataType : "json",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success : function(data) {
            	//console.log(data);
            	setFormDataFromObj($("#basicInfoForm"),data);
            	editUserID = data.eid;
            }
        });
    });

    /**
     * 提交用户编辑框数据
     */
    function submitAddBoxData() {
    	 var data = {};
    	 setFormDataInObject($("#basicInfoForm"),data);
    	 console.log(data);
    	 $.ajax({
             type : "post",
             url : "<c:url value="/updateEmployeeInfo"/>?eid="+editUserID,//存在ID为修改
             dataType : "json",
             data : data,
             contentType: "application/x-www-form-urlencoded; charset=utf-8",
             success : function(data) {
                 if(data == 'success') {
                     swal("提示", "修改成功!", "success")
                 } else {
                     swal("修改失败，请重试!");
                 }
             }
         });
    }
</script>
</body>
</html>