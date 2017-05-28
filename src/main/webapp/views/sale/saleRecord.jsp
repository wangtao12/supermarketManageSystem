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
					<label>订单编号</label> 
					<input type="text" class="form-control"
						name="snumber" style="width: 120px">
				</div>
				<div class="form-group">
					<button type="button" onclick="reloadData()"
						class="btn btn-primary">查询</button>
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
            "url": "<c:url value="/sale/getSaleRecordList"/>?fresh=" + Math.random(),
            "type": "post",
            "data": function (d){
                setFormDataInObject($('#searchForm'),d);
            }
        },
        "columns": [{
            "title": "姓名",
            "data": "ename"
        }, {
            "title": "账户",
            "data": "eaccount"
        }, {
            "title": "销售单号",
            "data": "snumber"
        }, {
            "title": "销售时间",
            "data": "sdate"
        }, {
            "title": "销售金额",
            "data": "money"
        }, {
            "title" : "详情",
            "data" : null
        }],
        "columnDefs": [
            {
                "render" : function(data, type, row, meta){
                	var button = '<button type="button" onclick="addTemp(\''+data.snumber+'\')" class="btn btn-primary">订单详情</button>';
                    return button;
                },
                "targets" : 5
            },
            {
                "render" : function(data, type, row, meta){
                    if(typeof data == "undefined") {
                        return ''
                    }
                    return data;
                },
                "targets" : [0,1,2,3,4,5]
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
    
    function addTemp(snumber){
    	$.ajax({
			type : "post",
			url : "<c:url value="/sale/getSaleDetail"/>",
			dataType : "json",
			data : {
				snumber : snumber
			},
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			success : function(mdata) {
				var detail = "商品名"+"	"+"销售数量"+"		"+"单价"+"			"+"收银"+"\r\n";
				var money=0;
				for(var i=0;i<mdata.length;i++){
					var item = mdata[i].gname+"   	"+mdata[i].scount+"        		"+mdata[i].gprice+"       		"+mdata[i].money+"\r\n";
					detail+=item;
					money =Number(money)+ Number(mdata[i].money);
				}
				$("#m_snumber").val(snumber);
				$("#m_detail").val(detail);
				$("#m_money").val(money);
				$('#saleDetailBox').modal({});
			},
			error: function(mdata){
				console.log("shibai"+mdata);
			}
		});
    }
    </script>
    <!-- 订单详情模态框 -->
	<div class="modal fade" id="saleDetailBox" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">订单详情</h4>
				</div>
				<div style="width: 90%; margin: 10px auto;">
					<form class="form-horizontal" id="confirmSaleBoxForm">
						<div class="form-group">
							<label class="col-sm-2 control-label">订单编号</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" id="m_snumber">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">订单详情</label>
							<div class="col-sm-10">
								<textarea style="height:80px"  type="text" class="DataInput form-control" id="m_detail" ></textarea>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-2 control-label">总收银</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control"
									id="m_money" >
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
    
    
	
</body>
</html>