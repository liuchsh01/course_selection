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

<style type="text/css">
#changeDiv {
	position:absolute;
	left:338px;
	top:55px;
	width:446px;
	height:130px;
	z-index:1;
	border:solid #7A7A7A 2px
}
</style>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
</head>
<body>
<%
User user = (User) request.getSession().getAttribute("user");
List<SelectedCourse> courses = (List<SelectedCourse>) request.getAttribute("courses");
Double selectedCredit = 0.0;
Double nlistenCredit = 0.0;
%>
<script type="text/javascript">
	function change(courseId,courseName){
		$("#changeBody").html("<table border='0'><tr><td>交换的课程名：</td><td>" + courseName + "</td></tr>" + 
				"<tr><td>想要课程的课程号：</td><td><input type='text' id='courseCode'/></td></tr>" + 
				"<tr><td colspan='2' style='text-align:center'><input type='button' onclick='confirm(" + courseId + ")' value='确定'/></td></tr></table>");
		$("#changeDiv").show();
	}
	
	function confirm(out_courseId){
		var in_courseCode = $("#courseCode").val();
		if(in_courseCode == null || in_courseCode.length == 0){
			alert("请输入课程号");
			return;
		}
		$.ajax({
			url:"<%=basePath %>changeCourse/changeRequest.do",
			data:{ 
				"in_courseCode":in_courseCode,
				"out_courseId":out_courseId
			},
			dataType:"json",
			scriptCharset:'utf-8',
			contentType: "application/x-www-form-urlencoded; charset=utf-8",
			success:function(data){
				hideChangediv();
				alert(data.msg);
			}
		});
	}
	
	function hideChangediv(){
		$("#changeDiv").hide();
        $("changeBody").html("");
	}
</script>

<center>
	<font color="#0000FF"><%=user.getName() %></font>选课情况<hr>
</center>
<table border="0">
	<tr align="center"><td>退课</td><td><FONT COLOR="#0000FF">课程号</FONT></td><td>课程名称</td><td>类别</td><td>学分</td></tr>
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
			out.print("<td><input type='button' onclick='change("+course.getCourseId()+",\""+course.getCourseName()+"\")' value='交换'/></td>");
			out.print("</tr>");
		}
			
		%>
</table>

<div id="changeDiv" style="background-color:#Ece3eb;display:none">
	<div id="spandiv" align="right" style="background-color:#AEEEEE;">
		<a href="javascript:void(0)" onclick="hideChangediv()">关闭</a>
	</div>
	<div id="changeBody" style="padding:10px;width:100%"></div>
</div>

<center>
	<font color="#0099CC">选课学分： <%=selectedCredit %>分，免听学分： <%=nlistenCredit %>分，总学分： <%=(selectedCredit + nlistenCredit) %>分 </font><font color="#FF00FF" face="楷体">（你的限选学分：<%=user.getLimitCredit() %>分 </font>） 
</center>
</body>
</html>