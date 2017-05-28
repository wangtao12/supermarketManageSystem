<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="../resources.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.md5.js"/>"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/buttons/1.3.1/js/dataTables.buttons.min.js"></script>

<script type="text/javascript" src="<c:url value="/resources/datatables/media/js/buttons.html5.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/datatables/media/js/jszip.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="https://cdn.datatables.net/buttons/1.3.1/css/buttons.dataTables.min.css"/>">
<title></title>
</head>
<body>
	<div class="panel-body" style="background-color: #FFF;">
		<form class="form1 form-inline" id="searchForm">
			<div class="form-group">
				<label>本月工资单</label>
			</div>
			<div class="form-group">
				<label style="margin-left:100px">上传工资单</label>
			</div>
			<div class="form-group">
				<form enctype="multipart/form-data"  class="fileForm">
					<input id="files" class="files" type="file" 
						data-min-file-count="1">
					<button type="submit" class="btn btn-primary" onclick="addFile()">Submit</button>
                  	<button type="reset" class="btn btn-default">Reset</button>
				</form>
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
            "url": "<c:url value="/finance/getSalary"/>?fresh=" + Math.random(),
            "type": "post",
            "data": function (d){
                setFormDataInObject($('#searchForm'),d);
            }
        },
        "columns": [{
            "title": "员工姓名",
            "data": "ename"
        }, {
             "title": "员工账户",
             "data": "eaccount"
       }, {
            "title": "基本工资",
            "data": "esalary"
        }, {
            "title": "提成",
            "data": "extra"
        }, {
            "title": "总工资",
            "data": "money"
        }],
        "columnDefs": [
        	{
                "render" : function(data, type, row, meta){
                    if(typeof data == "undefined") {
                        return '0'
                    }
                    return data;
                },
                "targets" : [2,3,4]
            },
            {
                "render" : function(data, type, row, meta){
                    if(typeof data == "undefined") {
                        return '无'
                    }
                    return data;
                },
                "targets" : [0,1,2,3,4]
            }
        ],
        dom: 'Bfrtip',
        "buttons": [ {
            extend: 'excel',
            'text': '导出为excel',
            'exportOptions': {  
                'modifier': {  
                    'page': 'current'  
                } 
            },
            customize: function( xlsx ) {
                var sheet = xlsx.xl.worksheets['salary.xml'];
                $('row c[r^="C"]', sheet).attr( 's', '2' );
            }
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
    
    function addFile(){
    	//console.log("进入函数");
    	var files = document.getElementById("files").files;
    	var formData = new FormData(); 
    	formData.append("files",files[0]);
    	console.log(formData);
    	var data={
    			"files":files
    	};
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