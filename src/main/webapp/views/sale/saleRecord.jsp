<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../resources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.md5.js"/>"></script>
<link href="<c:url value="/resources/css/saleMain.css"/>"
	rel="stylesheet" type="text/css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
<div class="panel panel-success"
		style="padding: 0px; border: 0px; border-bottom: 0px; margin: 10px;">
		<div class="panel-body" style="background-color: #FFF;">
			<form class="form1 form-inline" id="searchForm">
				<div class="form-group">
					<label>订单编号</label> <input type="text" class="form-control"
						name="snumber" style="width: 120px">
				</div>
				<div class="form-group">
					<button type="button" onclick="reloadData()"
						class="btn btn-primary" style="margin-top: 18px;">查询</button>
				</div>
			</form>
		</div>
		<table id="dataTable" style="text-align: center;" class="display"
			cellspacing="0"></table>
	</div>
</body>
</html>