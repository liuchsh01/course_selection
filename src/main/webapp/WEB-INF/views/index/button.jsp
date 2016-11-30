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
	<form name="bt" onsubmit="check_search()">
		<table border="0" cellspacing="5" cellpadding="0">
			<td><select name="querytype" size="1">
					<option value="1">按课程号查</option>
					<option value="2">按课程名查</option>
					<option value="3">按主讲教师查</option>
					<option value="4">按主讲班级查</option>
					<option value="5">查有剩余学位的课</option>
					<option value="6">按上课时间查</option>
			</select>&nbsp;<input type="text" name="kch" value="" size=10></td>
			<td><input type="button" name="action0" value="课程查询" onclick="check_search()" style="color: #008000"></td>
			<td><input type="button" name="action1" value="免听查询" onclick="mtxs()" style="color: #008000"></td>
			<td><input type="button" name="action2" value="课程表" onclick="get_sche()" style="color: #008000"></td>
			<td><input type="button" name="action3" value="选课结果" onclick="get_list()" style="color: #008000"></td>
			<td><input type="button" name="action4" value="退课" onclick="check_revoke()" style="color: #ff0080"></td>
			<td><input type="button" name="action5" value="选课" onclick="check_selected()" style="color: #008000"></td>
			<td><input type="button" name="action6" value="清除" onclick="clear_selected()" style="color: #008000"></td>
			<td><input type="button" value="黄牌预警" onclick="javascript:window.open('hpyj.htm','newwin','width=860,height=600,scrollbars=yes');" style="color: #008000"></td>
			<td><input type="button" name="action7" value="个人信息" onclick="info()" style="color: #008000"></td>
			<td><input type="button" name="action8" value="ʹ使用说明" onclick="op_help()" style="color: #008000"></td>
			<td><input type="button" name="action9" value="退出" onclick="quit_sys()" style="color: #008000"></td>
		</table>
	</form>
</body>
</html>