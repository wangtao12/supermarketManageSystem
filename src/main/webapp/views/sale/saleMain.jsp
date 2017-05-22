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
					style="margin-left: 380px">退货</button>
			</div>
		</form>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<label for="id_select">选择商品分类  :&nbsp;</label> 
				<select id="select_good_kind"
					class="selectpicker" data-live-search="true">
					<option>cow</option>
					<option>bull</option>
					<option>程序填充</option>
				</select>
				</br></br></br>
				<label for="id_select">&nbsp;&nbsp;&nbsp;&nbsp;选择商品&nbsp;  &nbsp;:&nbsp;</label> 
				<select id="select_good"
					class="selectpicker" data-live-search="true">
				</select>
				</br></br></br>
				<label>&nbsp;&nbsp;请输入数量 &nbsp;:&nbsp;</label> 
				<input id="good_count" oninput="cmoney()"  type="text" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')"  />  个（斤）
				</br></br></br>
				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收银 &nbsp;&nbsp;&nbsp;:&nbsp;</label> 
				<input id="good_money"  type="text" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" placeholder='0' readonly />  元
				
				</br></br></br>
				<button type="button" onclick="addgoods()" class="btn btn-primary" >添加</button>
			</div>
			<div class="col-md-8">
				<label for="id_select">订单商品详情  :&nbsp;</label>
				</br> </br>
				<table id="dataTable" style="text-align: center;" class="display"
			cellspacing="0"></table>
				</br></br></br>
				<label>订单收银 &nbsp;&nbsp;&nbsp;:&nbsp;</label> 
				<input id="good_count"  type="text" onkeyup="value=value.replace(/[^\d{1,}\.\d{1,}|\d{1,}]/g,'')" placeholder='0' readonly/>  元
				<button type="button" onclick="addSale()" class="btn btn-primary"
				style="margin-left:30px">提交订单</button>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			fillgood();
   		 });
	    var good_info = {};
		function fillgood(gkid){
			console.log("进入函数");
			$.ajax({
				type : "post",
				url : "<c:url value="/sale/getGoodList"/>",
				dataType : "json",
				async : false,
				data : {goodkind:gkid},
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				success : function(data) {
					good_info = data;
					console.log(good_info);
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
		function cmoney(){
			var price='';
			var count = $("#good_count").val();
			var gid = $("#select_good").val();
			for(var i=0;i<good_info.length;i++){
				var item = good_info[i];
				if(item.gid==gid){
					//console.log("找到gid");
					price = item.gprice;
					//console.log(price);
				}
			}
			var money = price*count;
			$("#good_money").val(money);
		}
		function addgoods(){
			
		}
	
	
	</script>
	
	<script type="text/javascript">
    var datatable;
    $(document).ready(function() {
        dataTablesServerSideInit();
        loadData();
        //日期选择初始化
        $(".formDatetime").datetimepicker({
            minView:"month",
            format: "yyyy-mm-dd",//显示格式
            todayBtn: true,//当前日期的按钮
            todayHighlight:true,//当前日前是否高亮
            language:'zh-CN', //语言选择
            autoclose: true //关闭时间的选择
        });
    });

    var dataTableConfig = {
        "columns": [{
            "title": "姓名",
            "data": "ename"
        }, {
             "title": "性别",
             "data": "esex"
       }, {
            "title": "账户",
            "data": "eaccount"
        }, {
            "title": "密码",
            "data": "epassword"
        }, {
            "title": "入职时间",
            "data": "ehiredate"
        }, {
            "title": "权限",
            "data": "authority"
        }, {
            "title": "基本工资",
            "data": "esalary"
        }, {
            "title": "家庭住址",
            "data": "eaddress"
        }, {
            "title": "联系方式",
            "data": "econtact"
        }, {
            "title" : "操作",
            "data" : null
        }],
        "columnDefs": [
            {
                "render" : function(data, type, row, meta){
                	var button = '<button type="button" onclick="addTemp(\''+data.eid+'\')" class="btn btn-primary">修改</button> <button type="button" onclick="deleteListData(\''+data.eid+'\')" class="btn btn-danger">删除</button> ';
                    return button;
                },
                "targets" : 9
            },
            {
                "render" : function(data, type, row, meta){
                    if(data == "m") {
                        return '男'
                    }else if(data == "f"){
                    	return '女'
                    }else{
                    	//console.log(data);
                    	return ''
                    }
                },
                "targets" : [1]
            },
            {
                "render" : function(data, type, row, meta){
                    if(typeof data == "undefined") {
                        return ''
                    }
                    return data;
                },
                "targets" : [7,8]
            }
            
        ]
    };

    var datatablesData;//当前页查询数据缓存
    /**
     * 加载数据
     */
    function loadData() {
        datatable = $('#dataTable')
            .on('init.dt', function (){
                datatablesData = datatable.data();
            })
            .DataTable(dataTableConfig);
    }

    /**
     * 重新加载数据
     */
    function reloadData() {
        datatable.destroy();
        loadData();
    }
    </script>

</body>
</html>