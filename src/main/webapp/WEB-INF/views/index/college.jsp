<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8" import="java.util.List,com.course.selection.entity.College"%>
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
<body bgcolor="#006782">
	<table noborder>
		<%
		List<College> list = (List<College>) request.getAttribute("colleges");
		for(int i = 0; i < list.size(); i++){
			College college = list.get(i);
			out.print("<tr><td><a href='" + basePath + "search/college.do?id=" + college.getCollegeId() + "' target='course'>");
			out.print("<font color='#FFFFFF'>" + college.getName() + "</font></a></td></tr>");
		}
		%>
	</table>
</body>
</html>