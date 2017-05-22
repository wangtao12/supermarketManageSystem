<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../resources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.md5.js"/>"></script>
<link href="<c:url value="/resources/css/purchaseMain.css"/>"
	rel="stylesheet" type="text/css" media="screen" />
<title></title>
</head>
<body>
	<div class="panel-body" style="background-color: #FFF;">
		<form class="form1 form-inline" id="searchForm">
			<div class="form-group">
				<label>商品库存表</label>
			</div>
		</form>
	</div>
	<table id="dataTable" style="text-align: center;" class="display"
		cellspacing="0"></table>
	</div>
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
        "ajax": {
            "url": "<c:url value="/purchase/getGoodStockList"/>?fresh=" + Math.random(),
            "type": "post",
            "data": function (d){
                setFormDataInObject($('#searchForm'),d);
            }
        },
        "columns": [{
            "title": "商品名称",
            "data": "gname"
        }, {
             "title": "商品种类",
             "data": "gkind"
       }, {
            "title": "商品库存",
            "data": "gstock"
        }, {
            "title": "商品售价",
            "data": "gprice"
        }, {
            "title": "商品销售量（当月）",
            "data": "scount"
        }, {
            "title": "商品退货记录",
            "data": "gbackrecord"
        }],
        "columnDefs": [
            {
                "render" : function(data, type, row, meta){
                    if(typeof data == "undefined") {
                        return '无'
                    }
                    return data;
                },
                "targets" : [5]
            },
            {
                "render" : function(data, type, row, meta){
                    if(typeof data == "undefined") {
                        return ''
                    }
                    return data;
                },
                "targets" : [1]
            },
            {
                "render" : function(data, type, row, meta){
                    if(typeof data == "undefined") {
                        return '0'
                    }
                    return data;
                },
                "targets" : [2,4]
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