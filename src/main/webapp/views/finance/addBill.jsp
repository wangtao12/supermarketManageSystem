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
    <h4 style = "margin-left:128px">添加报销：</h4>
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="basicInfoArea">
            <form class="form-horizontal" style="margin: 20px;" id="basicInfoForm">
                <div class="form-group">
                    <label class="col-sm-2 control-label">报销金额（单位为元）</label>
                    <div class="col-sm-10">
                        <input type="text" class="DataInput form-control" name="bmoney" style="width: 300px;">
                    </div>
                    
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">报销说明</label>
                    <div class="col-sm-10">
                        <textarea type="text" class="DataInput form-control" name="btype" style="width: 300px;height:90px"></textarea>
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

    /**
     * 提交用户编辑框数据
     */
    function submitAddBoxData() {
    	 var data = {};
    	 setFormDataInObject($("#basicInfoForm"),data);
    	 console.log(data);
    	 $.ajax({
             type : "post",
             url : "<c:url value="/finance/addBill"/>",
             dataType : "json",
             data : data,
             contentType: "application/x-www-form-urlencoded; charset=utf-8",
             success : function(data) {
                 if(data == 'success') {
                     swal("提示", "提交成功!", "success")
                 } else {
                     swal("修改失败，请重试!");
                 }
             }
         });
    }
</script>
</body>
</html>