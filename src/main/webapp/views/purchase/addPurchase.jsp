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
<script type="text/javascript">
	$(function() {
		$("#select_provider").empty(); 
		//$("#select_good_kind").append("<option value='Value'>Text</option>");
		$.ajax({
			type : "post",
			url : "<c:url value="/purchase/getProviderList"/>",
			async : false,
			dataType : "json",
			data : {},
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(data) {
				for (var i = 0; i < data.length; i++) {
					var item = data[i];
					//var op = "<option value="+"'"+item.gkid+"'"+">"+item.gkname+"</option>";
					//console.log(op);
					//var option = "<option  value = "'+item.gkid+'"> '+item.gkname+'</option>";
					//console.log(option);
					var option = $('<option value = "'+item.pid+'">'+item.pname+'</option>');
					//console.log($("#select_good_kind"));
					 //$("#select_good_kind").prepend('<option value="' + item.gkid + '">' + item.gkname + '</option>');
					//$("<option value='"+item.gkid+"'>"+item.gkname+"</option>").appendTo("#select_good_kind"); 
					//$option.appendTo($("#select_good_kind"));
					$("#select_provider").append(option);
					//$("#select_good_kind").append("<option value='Value'>Text</option>");
				}
			}
		});			
		
	});
</script>
</head>
<body>
	<div style="margin: 10px;">
		<h4 style="margin-left: 128px">添加进货：</h4>
		<div class="tab-content">
			<div role="tabpanel" class="tab-pane active" id="basicInfoArea">
				<form class="form-horizontal" style="margin: 20px;"
					id="basicInfoForm">
					<div class="form-group">
						<label class="col-sm-2 control-label">选择供应商</label>
						<div class="col-sm-10">
							<select id="select_provider" class="selectpicker"
								data-live-search="true">
								<option>cow</option>
								<option>bull</option>
								<option>程序填充</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">预计利润</label>
						<div class="col-sm-10">
							<input id="rprofit" type="text"
								onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />元
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">进货批次</label>
						<div class="col-sm-10">
							<input id="rbatch" type="text"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">上传进货单</label>
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
    	formData.append("pid",$("#select_provider option:selected").val());
    	formData.append("rbatch",$("#rbatch").val());
    	formData.append("rprofit",$("#rprofit").val());
    	console.log(formData);
    	$.ajax({
    		processData: false,
    		contentType: false,
            type : "post",
            async: false,  
            url : "<c:url value="/purchase/addPurchase"/>",
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