<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8" import="java.util.List,com.course.selection.entity.vo.SelectedCourse,com.course.selection.entity.User"%>
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
<%
User user = (User) request.getSession().getAttribute("user");
List<SelectedCourse> courses = (List<SelectedCourse>) request.getAttribute("courses");
Double selectedCredit = 0.0;
Double nlistenCredit = 0.0;
%>
<center>
	<font color=#0000FF><%=user.getName() %></font>选课情况<hr>
</center>
<form name="cancel">
	<table border=0>
		<tr align=center><td>退课</td><td><FONT SIZE="COLOR="#0000FF">课程号</FONT></td><td>课程名称</td><td>类别</td><td>学分</td></tr>
		<%
		for(int i = 0; i < courses.size(); i++){
			SelectedCourse course = courses.get(i);
			if(course.getNlisten()){
				nlistenCredit += course.getCredit();
			}else{
				selectedCredit += course.getCredit();
			}
			out.print("<tr>");
			out.print("<td><input type='checkbox' name='course_no' value='" + course.getCourseId() + "'></td>");
			out.print("<td>" + course.getCourseCode() + "</td>");
			out.print("<td>" + course.getCourseName() + "</td>");
			if(course.getCompulsory()){
				out.print("<td>必修</td>");
			}else{
				out.print("<td>选修</td>");
			}
			out.print("<td>" + course.getCredit() + "</td>");
			out.print("</tr>");
		}
		%>
		<tr>
	</table>
</form>
<center>
<font color="#0099CC">选课学分： <%=selectedCredit %>分，免听学分： <%=nlistenCredit %>分，总学分： <%=(selectedCredit + nlistenCredit) %>分 </font><font color="#FF00FF" face="楷体">（你的限选学分：<%=user.getLimitCredit() %>分 </font>） 
</center>
</body>
</html>