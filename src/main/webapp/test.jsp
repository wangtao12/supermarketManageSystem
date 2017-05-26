<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
 <title>zengjia he shancu </title>
 <meta charset="utf-8" />
 <script src="../jsPanel-3.5.0/jquery-3.1.1.min.js"></script>
 <script src="../DataTables/js/js/jquery.dataTables.min.js"></script>
 <link href="../DataTables/js/css/jquery.dataTables.min.css" rel="stylesheet" />
 <script type="text/javascript">
  $(document).ready(function () {
   $("#table").DataTable()
  });
  var i = 0;
  //添加行
  function addRow() {
   i++;
   var rowTem = '<tr class="tr_' + i + '">'
    + '<td><input type="Text" id="txt' + i + '" /></td>'
    + '<td><input type="Text" id="pwd' + i + '"/></td>'
    + '<td><a href="#" onclick=delRow('+i+') >删除</a></td>'
    + '</tr>';
   //var tableHtml = $("#table tbody").html();
   // tableHtml += rowTem;
    $("#table tbody:last").append(rowTem);
   // $("#table tbody").html(tableHtml);
  }
  //删除行
  function delRow(_id) {
   $("#table .tr_"+_id).hide();
   i--;
  }
  //进行测试
  function ceshi() {
   var str1 = '';
   for( var j=1;j<=i;j++){
    var str = $("#" + "txt" + j).val();
    str1 += str;
   }
   alert(str1);
  }
 </script>
</head>
<body>
 <div style="width:500px">
  <table id="table" border="1" width="500px" class="display hover cell-border border-blue-1" >
   <tr width="150px">
    <th width="70px">用户名</th>
    <th width="70px">密码</th>
    <th width="30px">操作</th>
   </tr>
   <tr width="150px">
    <th width="70px">用户名</th>
    <th width="70px">密码</th>
    <th width="30px">操作</th>
   </tr>
  </table>
 </div>
 <input type="button" value="添加行" onclick="addRow();" />
 <input type="button" value="测试数据" onclick="ceshi();" />
</body>
</html>