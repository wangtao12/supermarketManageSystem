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
<script type="text/javascript">
		$(function(){
			$.ajax({
	             type : "post",
	             url : "<c:url value="/manager/getOperateAuthority"/>",
	             dataType : "json",
	             data : {},
	             contentType: "application/x-www-form-urlencoded; charset=utf-8",
	             success : function(data) {
	             	for(var i=0;i<data.length;i++) {
							var item = data[i]
							var check = 'unchecked';
							var $radio = $('<input type="checkbox" autocomplete="off" class="authorityID" value = "'+item.id+'"  '+check+'> '+item.authorityName+'</input>');
							$("#typeRadio").append($radio);
						}
	             }
	         });
		});
	</script>
<title></title>
</head>
<body>
	<div class="panel panel-success"
		style="padding: 0px; border: 0px; border-bottom: 0px; margin: 10px;">
		<div class="panel-body" style="background-color: #FFF;">
			<form class="form1 form-inline" id="searchForm">
				<div class="form-group">
					<label>员工姓名</label> <input type="text" class="form-control"
						name="name" style="width: 120px">
				</div>
				<div class="form-group">
					<label>员工账户</label> <input type="text" class="form-control"
						name="account" style="width: 120px">
				</div>
				<div class="form-group">
					<button type="button" onclick="reloadData()"
						class="btn btn-primary" style="margin-top: 18px;">查询</button>
				</div>
				<div class="form-group">
					<button type="button" onclick="addTemp()" class="btn btn-primary"
						style="margin-top: 18px;">新增</button>
				</div>
			</form>
		</div>
		<table id="dataTable" style="text-align: center;" class="display"
			cellspacing="0"></table>
	</div>




	<!-- 新增模态框 -->
	<div class="modal fade" id="addTempBox" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">编辑员工</h4>
				</div>
				<div style="width: 90%; margin: 10px auto;">
					<form class="form-horizontal" id="addBoxForm">
						<div class="form-group" style="display: none">
							<label class="col-sm-2 control-label">id</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" name="eid">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" name="ename"
									required>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">账户</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control"
									name="eaccount" required>
							</div>
						</div>

						<div class="form-group">
							<label class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control"
									name="epassword" required>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">基本工资</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control" name="esalary"
									required>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">入职日期</label>
							<div class="col-sm-10">
								<input type="text" class="DataInput form-control"
									name="ehiredate">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">权限</label>
							<div class="col-sm-10">
								<div class="btn-group" id="typeRadio">
									<%--程序填充--%>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
						onclick="submitAddBoxData()">保存</button>
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
            "url": "<c:url value="/manager/getEmployeeList"/>?fresh=" + Math.random(),
            "type": "post",
            "data": function (d){
                setFormDataInObject($('#searchForm'),d);
            }
        },
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

    /**
     * 打开新增编辑框
     */
    function addTemp(eid) {
    	setFormDataEmpty($("#addBoxForm"));//清空现有表单
        $('#addTempBox').modal({});
    		var dataObj = getDataObjFromArray(datatablesData,"eid",eid);
    		//console.log(dataObj);
    		setFormDataFromObj($("#addBoxForm"),dataObj);
    }


    /**
     * 删除数据
     * @param id
     */
    
	function deleteListData(eid) {
		swal(
				{
					title : "确认删除?",
					text : "您将删除该用户!",
					type : "warning",
					showCancelButton : true,
					confirmButtonColor : "#DD6B55",
					confirmButtonText : "删除!",
					closeOnConfirm : false
				},
				function() {
					$.ajax({
								type : "post",
								url : "<c:url value="/manager/deleteEmployee"/>",
								dataType : "json",
								data : {
									eid : eid
								},
								contentType : "application/x-www-form-urlencoded; charset=utf-8",
								success : function(data) {
									data = $.trim(data);
									//console.log(data);
									if (data == 'success') {
										swal("提示", "操作成功!", "success")
										reloadData();
										$('#addTempBox').modal('hide');
									} else if (data == 'authorityError') {
										swal("您没有此权限!");
										$('#addTempBox').modal('hide');
									} else {
										swal("删除失败，请重试!");
									}
								}
							});

				});
	}

	/**
	 * 提交用户编辑框数据
	 */

	function submitAddBoxData() {
		//校验
		var eid = '';
		if ($("#addBoxForm").validate().form()) {
			var mdata = {};
			var authorityIDList = '';
			setFormDataInObject($("#addBoxForm"), mdata);
			var obj = $($("#typeRadio").children(".authorityID"));
			var j = 0;
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].checked) {

					if (j == 0) {
						authorityIDList = obj[i].value;
						console.log("进入i=0");
					} else {
						authorityIDList += ("," + obj[i].value);
					}
					j++;
				}
			}
			console.log(authorityIDList);
			mdata.eauthorityIDList = authorityIDList;
			//console.log(mdata);
			eid = $("input[name='eid']").val();
			var murl = '';
			if (eid == '') {
				console.log("eid为空")
				murl = "<c:url value="/manager/addEmployee"/>";
			} else {
				murl = "<c:url value="/manager/updateEmployee"/>";
			}
			$
					.ajax({
						type : "post",
						url : murl,
						dataType : "json",
						data : mdata,
						contentType : "application/x-www-form-urlencoded; charset=utf-8",
						success : function(data) {
							data = $.trim(data);
							console.log(data);
							if (data == 'success') {
								swal("提示", "操作成功!", "success")
								reloadData();
								$('#addTempBox').modal('hide');
							} else if (data == 'authorityError') {
								swal("您没有此权限!");
							} else {
								swal("删除失败，请重试!");
							}
						}
					});
		}
	}
</script>

</body>
</html>