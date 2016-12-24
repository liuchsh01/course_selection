<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8" import="java.util.List,com.course.selection.entity.Course,java.util.Map,com.course.selection.entity.TimePlace"%>
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
<body marginwidth="0" marginheight="0" style="font-size:14px">
	<table border="1" cellspacing="0">
		<thead>
			<tr>
				<td>必<br>修</td>
				<td>选<br>修</td>
				<td align="center">课程号</td>
				<td align="center">课程名称</td>
				<td align="center">主选班级</td>
				<td align="center">主讲教师</td>
				<td>学分</td>
				<td>考核<br>方式</td
				><td>选课<br>人数</td>
				<td>课时教室</td>
				<td>上课时间段</td>
				<td>学分类别</td>
				<td>备注</td>
			</tr>
		</thead>
		<%
		String[] weekdays = {"周日","周一","周二","周三","周四","周五","周六"};
		List<Course> courses = (List<Course>) request.getAttribute("courses");
		Map<Integer, List<TimePlace>> timePlaces = (Map<Integer, List<TimePlace>>) request.getAttribute("timePlaces");
		for (Course course : courses){
			out.print("<tr>");
			out.print("<td><input type='checkbox' name='no_type' value='" + course.getCourseId() + ":0'></td>");
			out.print("<td><input type='checkbox' name='no_type' value='" + course.getCourseId() + ":1'></td>");
			out.print("<td>" + course.getCourseCode() + "</td>");
			out.print("<td>" + course.getCourseName() + "</td>");
			out.print("<td>" + course.getMajorChooser() + "</td>");

			out.print("<td>" + course.getTeacher() + "</td>");
			out.print("<td>" + course.getCredit() + "</td>");
			if(course.getEvaluationMode()!= null && course.getEvaluationMode()){
				out.print("<td>期末考试为主型</td>");
			}else{
				out.print("<td>过程考核为主型</td>");
			}
			out.print("<td><img src='" + basePath + "static/img/ifind.jpg' onclick='window.open(&quot;" + basePath + "search/courseCount.do?id=" + course.getCourseId() + "&quot;,&quot;count&quot;,&quot;width=420,height=200&quot;)'></td>");
			out.print("<td>");
			
			List<TimePlace> list = timePlaces.get(course.getCourseId());
			if(list != null && list.size() != 0){
				for (int i = 0; i < list.size(); i++){
					TimePlace timePlace = list.get(i);
					if(i != 0){
						out.print(";");
					}
					out.print(weekdays[timePlace.getWeekDay() % 7]);
					for (int j = 0; j < timePlace.getNum(); j++){
						if(j != 0){
							out.print(",");
						}
						out.print(timePlace.getClassNo() + j);
					}
					out.print("(" + timePlace.getPlace() + ")");
				}
			}
			
			out.print("</td>");

			out.print("<td>" + course.getWeekBlock() + "</td>");
			if(course.getCreditType() != null && course.getCreditType()){
				out.print("<td>理科学分</td>");
			}else{
				out.print("<td>文科学分</td>");
			}
			out.print("<td>" + (course.getComment() == null ? "" : course.getComment()) + "</td>");
			out.print("</tr>");
		}
		%>
	</table>
</body>
</html>