<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	isELIgnored="true"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../resources.jsp"%>
<title>项目申报</title>
<%--bootstrap--%>
<script type="text/javascript"
	src="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/js/bootstrap-select.js"></script>
<link rel="stylesheet" type="text/css"
	href="http://cdn.bootcss.com/bootstrap-select/2.0.0-beta1/css/bootstrap-select.css">
<script type="text/javascript">  
        $(window).on('load', function () {  
            $('.selectpicker').selectpicker({  
                'selectedText': 'cat'  
            });  
        });  
</script>
<script type="text/javascript">
	$(function() {
		$("#select_good_kind").empty(); 
		//$("#select_good_kind").append("<option value='Value'>Text</option>");
		$.ajax({
			type : "post",
			url : "<c:url value="/sale/getGoodKindList"/>",
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
					var option = $('<option value = "'+item.gkid+'">'+item.gkname+'</option>');
					//console.log($("#select_good_kind"));
					 //$("#select_good_kind").prepend('<option value="' + item.gkid + '">' + item.gkname + '</option>');
					//$("<option value='"+item.gkid+"'>"+item.gkname+"</option>").appendTo("#select_good_kind"); 
					//$option.appendTo($("#select_good_kind"));
					$("#select_good_kind").append(option);
					//$("#select_good_kind").append("<option value='Value'>Text</option>");
				}
			}
		});			
		
	});
</script>
</head>

<body>
	<div class="panel-body" style="background-color: #FFF;">
		<form class="form1 form-inline">
			<div class="form-group">
				<label style="font-size: 18px">选择销售商品</label>
			</div>
			<div class="form-group">
				<button type="button" onclick="backgoods()" class="btn btn-primary"
					style="margin-left: 360px">退货</button>
			</div>
		</form>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<label for="id_select">选择商品分类 :&nbsp;</label> 
				<select
					id="select_good_kind" class="selectpicker" data-live-search="true" onchange="goodKindChange()">
					<option>cow</option>
					<option>bull</option>
					<option>程序填充</option>
				</select> </br> </br> </br> <label for="id_select">&nbsp;&nbsp;&nbsp;&nbsp;选择商品&nbsp;
					&nbsp;:&nbsp;</label> <select id="select_good" class="selectpicker"
					data-live-search="true">
				</select> </br> </br> </br> <label>&nbsp;&nbsp;请输入数量 &nbsp;:&nbsp;</label> <input
					id="good_count" oninput="cmoney()" type="text"
					onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" />
				个（斤） </br> </br> </br> <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收银
					&nbsp;&nbsp;&nbsp;:&nbsp;</label> <input id="good_money" type="text"
					onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"
					placeholder='0' readonly /> 元 </br> </br> </br>
				<button type="button" id="addRow" class="btn btn-primary"
					onclick="addgoods()">添加</button>
			</div>
			<div class="col-md-8">
				<label for="id_select">订单商品详情 :&nbsp;</label> </br> </br>
				<table id="dataTable" style="text-align: center;" class="display"
					cellspacing="0">
					<thead>
						<tr>
							<th>商品名称</th>
							<th>购买数量</th>
							<th>商品单价</th>
							<th>商品收银</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
				</br> </br> </br> <label>订单收银 &nbsp;&nbsp;&nbsp;:&nbsp;</label> <input
					id="sum_money" type="text"
					onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"
					placeholder='0' readonly /> 元
				<button type="button" onclick="addSale()" class="btn btn-primary"
					style="margin-left: 30px">提交订单</button>
			</div>
		</div>
	</div>
	</br></br></br>
	<script type="text/javascript">
		var t;
		var counter = 1;
		$(document).ready(function() {
			t = $('#dataTable').DataTable();
			fillgood(1);
			//$("#select_good").empty();
   		 });
		var good_info = {};
		
		function goodKindChange(){
			var s = $("#select_good_kind option:selected").val();
			$("#select_good").empty();
			fillgood(s);
		}
		
		
		function fillgood(gkid) {
			//console.log("进入函数");
				$.ajax({
					type : "post",
					url : "<c:url value="/sale/getGoodList"/>",
					dataType : "json",
					async : false,
					data : {
						goodkind : gkid
					},
					contentType : "application/x-www-form-urlencoded; charset=utf-8",
					success : function(data) {
						$("#select_good").empty();
						good_info = data;
						//console.log(data);
						for (var i = 0; i < data.length; i++) {
							var item = data[i];
							var option = $('<option value = "'+item.gid+'">'
									+ item.gname + '</option>');
							$("#select_good").append(option);
								//$("#select_good_kind").append("<option value='Value'>Text</option>");
						}
					}
				});
		}
		function cmoney() {
			var price = '';
			var scount = $("#good_count").val();
			var gid = $("#select_good").val();
			for (var i = 0; i < good_info.length; i++) {
				var item = good_info[i];
				if (item.gid == gid) {
					//console.log("找到gid");
					price = item.gprice;
					//console.log(price);
				}
			}
			var money = price * scount;
			$("#good_money").val(money);
		}
		var datalist = new Array();
		var data = {};
		var sum_money = 0;
		function addgoods() {
			var gid = $("#select_good").val();
			var gname;
			var gprice;
			var scount = $("#good_count").val();
			var money = $("#good_money").val();
			for (var i = 0; i < good_info.length; i++) {
				var item = good_info[i];
				if (item.gid == gid) {
					//console.log("找到gid");
					gname = item.gname;
					gprice = item.gprice;
					//console.log(price);
				}
			}
			data = {
				"gid":gid,	
				"gname":gname,
				"scount":scount,
				"gprice":gprice,
				"money":money,
				"counter":counter
			};
			datalist.push(data);
			counter++;
			loaddata();
		}
		function deleteListData(counter){			
			for(var i=0; i<datalist.length; i++) {
			    if(datalist[i].counter == counter) {
			    	//console.log("删除");
			    	datalist.splice(i, 1);
			      	break;
			    }
			  }
			
			//console.log(datalist);
			loaddata();
		}
		function loaddata(){
			sum_money = 0;
			$('#dataTable').dataTable().fnDeleteRow($(this).closest('tr')[0]);
			
			for(var i=0;i<datalist.length;i++){
				var op = '<button type="button" onclick="deleteListData(\''
					+ datalist[i].counter + '\')" class="btn btn-danger">删除</button> ';
				//console.log(datalist.length);
				sum_money = Number(sum_money)+Number(datalist[i].money);
				t.row.add([ 
					datalist[i].gname, 
					datalist[i].scount,
					datalist[i].gprice, 
					datalist[i].money,
					op
					 ]).draw();
				
			}
			$("#sum_money").val(sum_money);
			$("#good_money").val("");
			$("#good_count").val("");
			
		}
		var snumber;
		function addSale(){
			var saleList = JSON.stringify(datalist);
			$.ajax({
				type : "post",
				url : "<c:url value="/sale/SaleGoods"/>",
				dataType : "json",
				async : false,
				data : {
					saleList : saleList
				},
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				success : function(result) {
					if(result=="error"){
						swal("提示","请重试");
					}else{
						snumber=result;
						console.log(result);
						$("#snumber").val(result);
						var detail = "商品名"+"	"+"销售数量"+"		"+"单价"+"			"+"收银"+"\r\n";
						for(var i=0;i<datalist.length;i++){
							var item = datalist[i].gname+"   	"+datalist[i].scount+"        		"+datalist[i].gprice+"       		"+datalist[i].money+"\r\n";
							detail+=item;
						}
						 console.log(detail);
						 $("#detail").val(detail);
						 $("#money").val(sum_money);
						 $('#confirmSaleBox').modal({});
					};
				}
			});
		}
		
		function submitConfirmSaleBoxData(){
			$.ajax({
				type : "post",
				url : "<c:url value="/sale/confirmSale"/>",
				dataType : "json",
				data : {
					snumber : snumber
				},
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				success : function(mdata) {
					console.log(mdata);
					if(mdata=="error"){
						swal("提示","请重试");
					}else if(mdata=="failure"){
						swal("提示","订单已失效");
						$('#confirmSaleBox').modal('hide');
					}else{
						swal("提示","提交成功");
						$('#confirmSaleBox').modal('hide');
						datalist.splice(0,datalist.length);
						loaddata();
					}
				},
				error: function(mdata){
					console.log("shibai"+mdata);
				}
			});
		}
		
		function submitCancelSaleBoxData(){
			$.ajax({
				type : "post",
				url : "<c:url value="/sale/cancelSale"/>",
				dataType : "json",
				data : {
					snumber : snumber
				},
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				success : function(mdata) {
					//console.log(mdata);
					if(mdata=="error"){
						swal("提示","请重试");
					}else if(mdata=="success"){
						swal("提示","订单已取消");
						$('#confirmSaleBox').modal('hide');
						datalist.splice(0,datalist.length);
						loaddata();
					}
				},
				error: function(mdata){
					//console.log("shibai"+mdata);
				}
			});
		}
		
	</script>
	
	<!-- 确认支付模态框 -->
	<div class="modal fade" id="confirmSaleBox" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">确认订单</h4>
				</div>
				<div style="width: 90%; margin: 10px auto;">
					<form class="form-horizontal" id="confirmSaleBoxForm">
						<div class="form-group">
							<label class="col-sm-2 control-label">订单编号</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" id="snumber">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">订单详情</label>
							<div class="col-sm-10">
								<textarea style="height:80px"  type="text" class="DataInput form-control" id="detail" ></textarea>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-2 control-label">总收银</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control"
									id="money" >
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="submitCancelSaleBoxData()">取消订单</button>
					<button type="button" class="btn btn-primary"
						onclick="submitConfirmSaleBoxData()">确认收银</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 退货模态框 -->
	<div class="modal fade" id="backGoodBox" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">退货</h4>
				</div>
				<div style="width: 90%; margin: 10px auto;">
					<form class="form-horizontal" id="confirmSaleBoxForm">
						<div class="form-group">
							<label class="col-sm-2 control-label">订单编号</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" id="back_snumber">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">商品名称</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" id="back_goodname">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">商品数量</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" id="back_goodcount">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">退货说明</label>
							<div class="col-sm-10">
								<textarea style="height:80px"  type="text" class="DataInput form-control" id="back_remark" ></textarea>
							</div>
						</div>	
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="submitBackGoodBoxData()">确认退货</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<script type="text/javascript">
		function backgoods(){
			$("#back_snumber").val("");
			$("#back_goodname").val("");
			$("#back_goodcount").val("");
			$("#back_remark").val("");
			$('#backGoodBox').modal({});
		}
		function submitBackGoodBoxData(){
			var data = {
				"snumber":$("#back_snumber").val(),
				"gname":$("#back_goodname").val(),
				"count":$("#back_goodcount").val(),
				"remark":$("#back_remark").val(),
			};
			$.ajax({
				type : "post",
				url : "<c:url value="/sale/BackGoods"/>",
				dataType : "json",
				data : data,
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				success : function(mdata) {
					//console.log(mdata);
					if(mdata=="error"){
						swal("提示","请重试");
					}else{
						swal("提示","退货成功！退款金额："+mdata);
						$('#backGoodBox').modal('hide');
					}
				},
				error: function(mdata){
					//console.log("shibai"+mdata);
				}
			});
		}
	</script>

</body>
</html>