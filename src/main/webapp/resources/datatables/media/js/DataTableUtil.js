/**
 * Datatables 真分页初始化
 */
function dataTablesServerSideInit() {
    $.extend(true, $.fn.dataTable.defaults,{
        "processing" : true,
        "serverSide" : true,
        "ordering" : false,
        "searching" : false,
        "language" : {
            "decimal" : "",
            "emptyTable" : "No data available in table",
            "info" : "第_START_条到第_END_条,共 _TOTAL_条",
            "infoEmpty" : "Showing 0 to 0 of 0 entries",
            "infoFiltered" : "(filtered from _MAX_ total entries)",
            "infoPostFix" : "",
            "thousands" : ",",
            "lengthMenu" : "每页显示_MENU_条",
            "loadingRecords" : "加载中...",
            "processing" : "数据处理中...",
            "search" : "Search:",
            "zeroRecords" : "没有查询到数据，请重新设置查询条件",
            "paginate" : {
                "first" : "第一页",
                "last" : "最后一页",
                "next" : "下一页",
                "previous" : "上一页"
            },
            "aria" : {
                "sortAscending" : ": activate to sort column ascending",
                "sortDescending" : ": activate to sort column descending"
            }
        }
    });
}

/**
 * Datatables 假分页初始化
 */
function dataTablesPageSideInit() {
    $.extend(true, $.fn.dataTable.defaults,{
        "processing" : true,
        "serverSide" : false,
        "ordering" : false,
        "searching" : false,
        "language" : {
            "decimal" : "",
            "emptyTable" : "No data available in table",
            "info" : "第_START_条到第_END_条,共 _TOTAL_条",
            "infoEmpty" : "Showing 0 to 0 of 0 entries",
            "infoFiltered" : "(filtered from _MAX_ total entries)",
            "infoPostFix" : "",
            "thousands" : ",",
            "lengthMenu" : "每页显示_MENU_条",
            "loadingRecords" : "加载中...",
            "processing" : "数据处理中...",
            "search" : "Search:",
            "zeroRecords" : "没有查询到数据，请重新设置查询条件",
            "paginate" : {
                "first" : "第一页",
                "last" : "最后一页",
                "next" : "下一页",
                "previous" : "上一页"
            },
            "aria" : {
                "sortAscending" : ": activate to sort column ascending",
                "sortDescending" : ": activate to sort column descending"
            }
        }
    });
}

/**
 * 将表单提交值放入对象obj
 * @param from 表单jQuery对象
 * @param obj 输入对象
 */
function setFormDataInObject(form,obj) {
    var formObj = form.serializeArray();
    for(var i=0;i<formObj.length;i++) {
        obj[formObj[i].name] = formObj[i].value;
    }
}

/**
 * 将obj中key-value填入表单 class带DataInput
 * @param from 表单jQuery对象
 * @param obj 输入对象
 */
function setFormDataFromObj(form,obj) {
    form.find(".DataInput").each(function(index,element){
        for(var key in obj) {
            if($(element).attr("name") == key) {
                var tagName = $(element).get(0).tagName;
                if(tagName == 'SPAN') {
                    $(element).html(obj[key]);
                } else {
                    $(element).val(obj[key]);
                }
            }
        }
    });
}

/**
 * 清空表单数值
 */
function setFormDataEmpty(form) {
    form.find(".DataInput").each(function(index,element){
        $(element).val("");
    });
}

/**
 * 获取数组对象
 * @param array 数组
 * @param field 字段名
 * @param value 字段值
 */
function getDataObjFromArray(array,field,value) {
    for(var i = 0;i<array.length;i++) {
        if(array[i][field] == value) {
            return array[i];
        }
    }
    return {};
}

/**
 * 页面跳转
 * @param url
 */
function goPage(url){
    self.location=url;
}


String.prototype.toDate = function (fmt) {
    if (fmt == null) fmt = 'yyyy-MM-dd hh:mm:ss';
    var str = this;
    //fmt为日期格式,str为日期字符串
    var reg = /([yMdhmsS]+)/g;//日期格式中的字符
    var key = {};
    var tmpkeys = fmt.match(reg);
    for (var i = 0 ; i < tmpkeys.length; i++) {
        key[tmpkeys[i].substr(0, 1)] = i + 1;
    }
    var r = str.match(fmt.replace(reg, "(\\d+)"));
    return new Date(r[key["y"]], r[key["M"]] - 1, r[key["d"]], r[key["h"]], r[key["m"]], r[key["s"]], r[key["S"]]);
}

