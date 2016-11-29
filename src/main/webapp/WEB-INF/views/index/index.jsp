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
<frameset rows="90%,10%">
	<frameset cols="15%,85%">
		<frame name="dept" src="<%=basePath%>index/college.do" marginwidth="0" marginheight="0">
		<frameset rows="40%,60%">
			<frame name="result" src="<%=basePath%>index/result.do" marginwidth="0" marginheight="0">
			<frame name="course" src="<%=basePath%>index/info.do" marginwidth="0" marginheight="0">
		</frameset>
	</frameset>
	<frame name="button" src="<%=basePath%>index/button.do" marginwidth="0" marginheight="0">
</frameset>
<noframes></noframes>
</html>