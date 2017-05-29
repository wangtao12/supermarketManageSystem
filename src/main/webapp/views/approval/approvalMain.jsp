<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="true"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="../resources.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="<c:url value="/resources/js/jquery.md5.js"/>"></script>
    <title></title>
</head>
<body>
<div class="panel panel-success" style="padding:0px;border:0px;border-bottom:0px;margin: 10px;">
    <div class="panel-body" style="background-color:#FFF;">
        <form class="form1 form-inline" id="searchForm" >
            <div class="form-group">
                <label>审核状态</label>
                <select class="form-control" name="status"  style="width:120px">
                	<option value="" selected="selected">全部</option>
                    <option value="0">待审核</option>
                	<option value="2">否决</option>
                    <option value="1">通过</option>
                </select>
            </div>
            <div class="form-group">
                <button type="button" onclick="reloadData()" class="btn btn-primary" >查询</button>
            </div>
        </form>
    </div>
    <table id="dataTable" style="text-align:center;" class="display" cellspacing="0" ></table>
</div>

<!-- 新增模态框 -->
<div class="modal fade" id="addTempBox" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×</button><h4 class="modal-title" id="myModalLabel">报销审批</h4>
            </div>
            <div style="width: 90%;margin: 10px auto;">
                <form class="form-horizontal" id="addBoxForm">
	                <div class="form-group">
							<label class="col-sm-2 control-label">审核：</label>
							<div class="col-sm-10">
								<select class="DataInput" name="checkMark" id="checkMark">
									<option value="0" selected="selected">待审核</option>
									<option value="2">否决</option>
									<option value="1">通过</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">审核意见</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" id="remark">
							</div>
						</div>
	                <div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" id="bid">
							</div>
					</div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submitAddBoxData()">提交</button>
            </div>
        </div>
    </div>
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
            "url": "<c:url value="/approval/getApprovalList"/>?fresh=" + Math.random(),
            "type": "post",
            "data": function (d){
                setFormDataInObject($('#searchForm'),d);
            }
        },
        "columns": [{
            "title": "员工账户",
            "data": "eid"
        }, {
            "title": "员工姓名",
            "data": "ename"
        }, {
            "title": "时间",
            "data": "bdate"
        }, {
            "title": "说明",
            "data": "btype"
        },{
            "title": "金额",
            "data": "bmoney"
        }, {
            "title": "状态",
            "data": "status"
        }, {
            "title" : "",
            "data" : null
        }],
        "columnDefs": [
        	{
                "render" : function(data, type, row, meta){
                    
                	if(data == "0") {
                        return "未审核";
                    }
                	else if(data == "1") {
                    	return "已通过";
                    }
                	else if(data == "2") {
                    	return "未通过";
                    }
                },
                "targets" : 5
            },
            {
                "render" : function(data, type, row, meta){
                    return '<button type="button" onclick="addTemp(\''+data.bid+'\')" class="btn btn-primary">审核</button>';
                },
                "targets" : 6
            },
            {
                "render" : function(data, type, row, meta){
                    if(typeof data == "undefined") {
                        return ''
                    }
                    return data;
                },
                "targets" : [0,1,2,3,4,5,6]
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

    /**
     * 打开新增编辑框
     */
    function addTemp(bid) {
    	$("#bid").val(bid);
        $('#addTempBox').modal({});
    }

    /**
     * 审批提交
     */
    function submitAddBoxData() {
        var data = {"bid":$("#bid").val(),
        		"status":$('#checkMark option:selected').val(),
        		"remark":$("#remark").val()
        }
        //console.log(data);
        $.ajax({
            type : "post",
            url : "<c:url value="/approval/updateApproval"/>",
            dataType : "json",
            data : data,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            success : function(data) {
            	//console.log(data);
                if(data == 'success') {
                    swal("提示", "提交成功!", "success")
                    reloadData();
                    $('#addTempBox').modal('hide');
                } else {
                    swal("提示","提交失败，请重试!");
                }
            }
        });
    }


</script>
</body>
</html>
