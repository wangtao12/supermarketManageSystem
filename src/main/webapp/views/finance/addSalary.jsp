<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../resources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.md5.js"/>"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.3.1/js/dataTables.buttons.min.js"></script>

<script type="text/javascript"
	src="<c:url value="/resources/datatables/media/js/buttons.html5.min.js"/>"></script>
<script type="text/javascript"
	src="<c:url value="/resources/datatables/media/js/jszip.min.js"/>"></script>
<link rel="stylesheet" type="text/css"
	href="<c:url value="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css"/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value="https://cdn.datatables.net/buttons/1.3.1/css/buttons.dataTables.min.css"/>">
<title></title>
</head>
<body>
	<div style="margin: 10px;">
		<h4 style="margin-left: 128px">添加进货：</h4>
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="basicInfoArea">
				<form class="form-horizontal" style="margin: 20px;"
					id="basicInfoForm">
					<div class="form-group">
						<label class="col-sm-2 control-label">工资月份</label>
						<div class="col-sm-10">
							<input id="month" type="text"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">人数：</label>
						<div class="col-sm-10">
							<input id="count" type="text"
								onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />人
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">总金额：</label>
						<div class="col-sm-10">
							<input id="money" type="text"
								onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />人
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">上传工资单</label>
						<div class="col-sm-10">
							<form enctype="multipart/form-data" class="fileForm">
								<input id="files" class="files" type="file" data-min-file-count="1">
								<button type="reset" class="btn btn-default" style="margin-top:20px">重置文件</button>
							</form>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button type="button" class="btn btn-primary"
								onclick="submitAddBoxData()">提交</button>
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
    	var files = document.getElementById("files").files;
    	var formData = new FormData(); 
    	formData.append("files",files[0]);
    	formData.append("month",$("#month").val());
    	formData.append("count",$("#count").val());
    	formData.append("money",$("#money").val());
    	console.log(formData);
    	$.ajax({
    		processData: false,
    		contentType: false,
            type : "post",
            async: false,  
            url : "<c:url value="/finance/addSalary"/>",
            dataType : "json",
            data : formData,  
            cache: false,  
            success : function(data) {
            	swal("提示","上传成功");
            },
            error: function(data){
            	swal("提示","请重试");
            }
        });
    }
</script>
</body>
</html>