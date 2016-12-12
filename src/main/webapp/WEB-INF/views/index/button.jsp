<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>主页</title>
</head>
<body>
	<form id="searchForm" onsubmit="check_search()" method="post" action="<%=basePath %>search/condition.do" target="course">
		<table border="0" cellspacing="5" cellpadding="0">
			<td><select name="searchType" size="1">
					<option value="1">按课程号查</option>
					<option value="2">按课程名查</option>
					<option value="3">按主讲教师查</option>
					<option value="4">按主讲班级查</option>
					<option value="5">查有剩余学位的课</option>
					<option value="6">按上课时间查</option>
			</select>&nbsp;<input type="text" name="searchText" size=10></td>
			<td><input type="button" name="action0" value="课程查询" onclick="check_search()" style="color: #008000"></td>
			<td><input type="button" name="action1" value="免听查询" onclick="mtxs()" style="color: #008000"></td>
			<td><input type="button" name="action2" value="课程表" onclick="get_sche()" style="color: #008000"></td>
			<td><input type="button" name="action3" value="选课结果" onclick="get_list()" style="color: #008000"></td>
			<td><input type="button" name="action4" value="退课" onclick="check_revoke()" style="color: #ff0080"></td>
			<td><input type="button" name="action5" value="选课" onclick="check_select()" style="color: #008000"></td>
			<td><input type="button" name="action6" value="清除" onclick="clear_selected()" style="color: #008000"></td>
			<td><input type="button" value="黄牌预警" onclick="javascript:window.open('hpyj.htm','newwin','width=860,height=600,scrollbars=yes');" style="color: #008000"></td>
			<td><input type="button" name="action7" value="个人信息" onclick="info()" style="color: #008000"></td>
			<td><input type="button" name="action8" value="ʹ使用说明" onclick="op_help()" style="color: #008000"></td>
			<td><input type="button" name="action9" value="退出" onclick="quit_sys()" style="color: #008000"></td>
		</table>
	</form>
</body>
<script type="text/javascript">
function check_search(){
	var type = document.getElementsByName("searchType")[0].value;
	var text = document.getElementsByName("searchText")[0].value;
	if(text != ""){
		document.getElementById("searchForm").submit();
	}
}
function check_select(){
	var arr = parent.course.document.getElementsByName("no_type");
	var data = new Array();
	for(var i = 0; 2 * i < arr.length; i++){
		if(arr[2 * i].checked && arr[2 * i + 1].checked){
			alert('输入格式有错，一门课程不可同时选择"选修"及"必修"！');
			return;
		}else if(arr[2 * i].checked){
			data.push(arr[2 * i].value);
		}else if(arr[2 * i + 1].checked){
			data.push(arr[2 * i + 1].value);
		}
	}
	if(data.length > 0){
		parent.result.location.href="<%=basePath %>select/selectCourse.do?data=" + data.toString();
	}
}
function check_revoke(){
	var arr = parent.result.document.getElementsByName("course_no");
	var data = new Array();
	for(var i = 0; i < arr.length; i++){
		if(arr[i].checked){
			data.push(arr[i].value);
		}
	}
	if(data.length > 0){
		parent.result.location.href="<%=basePath %>select/disselectCourse.do?data=" + data.toString();
	}
}
</script>
</html>