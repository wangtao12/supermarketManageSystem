<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="true"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/theme.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/layout.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/form-page.css"/>">

<%--bootstrap--%>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-2.1.1.min.js"/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrap/css/bootstrap.min.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/bootstrap/js/bootstrap.min.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/bootstrapDatetimepicker/css/bootstrap-datetimepicker.min.css"/>" />
<script type="text/javascript" src="<c:url value="/resources/bootstrapDatetimepicker/js/bootstrap-datetimepicker.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/bootstrapDatetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"/>"  charset="UTF-8"></script>

<%--表格--%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/datatables/media/css/jquery.dataTables.css"/>">
<script type="text/javascript" src="<c:url value="/resources/datatables/media/js/jquery.dataTables.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/datatables/media/js/DataTableUtil.js"/>"></script>

<%--jquery-validation--%>
<script type="text/javascript" src="<c:url value="/resources/jquery-validation/jquery.validate.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/jquery-validation/localization/messages_zh.js"/>"></script>

<%--alert--%>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/sweetalert/sweetalert.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/sweetalert/themes/facebook.css"/>">
<script type="text/javascript" src="<c:url value="/resources/sweetalert/sweetalert.min.js"/>"></script>
<script type="text/javascript">
    window.alert = function(text){
        swal("提示",text);
    }
</script>

<style>
    /* 修正datatable中p元素受bootstrap样式影响 */
    .display p {
        margin: auto !important;
    }
</style>



