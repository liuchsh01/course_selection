<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8" import="com.course.selection.entity.Course"%>
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>课程统计</title>
</head>
<body onblur=window.close()>
	<%
		Object obj = request.getAttribute("course");
		if (obj != null) {
			Course course = (Course) obj;
			out.print("<center><h3><font color=#ff0000>");
			out.print(course.getCourseName());
			out.print("</font>&nbsp;选课情况</h3></center><br>限制人数：");
			out.print(course.getLimitNum());
			out.print("&nbsp;&nbsp;已选人数：");
			out.print(course.getTotalNum());
		}
	%>
</body>
</html>