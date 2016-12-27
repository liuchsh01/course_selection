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

#div1 {

 position:absolute;

 left:338px;

 top:91px;

 width:446px;

 height:294px;

 z-index:1;

 border:solid #7A7A7A 4px; 

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
	$(function(){

   		 $("#div1").hide(); //先让div隐藏
		 
   		 $(".change_request").click(function(){
   			$("#div1").fadeIn("slow"); 
   		 });
   		 
    	 $("#span1").click(function(){

              $("#div1").hide();//淡入淡出效果 隐藏div
              $("#courseInfo").remove();
              $("#confirmButton").remove();
     	 })

 	});
	
	function change(courseId,courseName){
	    var inId = $("#courseId").val();
	    console.log(inId);
		$("#spandiv").after("<div id='courseInfo'>你想交换的课程名:"+courseName+"</div>");
		$("#formdiv").after("<input type='button' id='confirmButton' onclick='confirm("+courseId+")' value='确定' />");
	}
	
	function confirm(out_courseId){
		var in_courseCode = $("#courseCode").val();
		$("#div1").fadeOut("slow");
		
		$.ajax({
			url:"http://localhost:8080/selection/changeCourse/changeRequest.do",
			data:{ 
				     "out_courseId":out_courseId,
				     "in_courseCode":in_courseCode
			},
			dataType:"json",
			scriptCharset:'utf-8',
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			success:function(data){
				console.log(data);
				alert(data.msg);
			}
		});
		
		$("#courseId").val("");
		
		$("#courseInfo").remove();
		$("#confirmButton").remove();
	}
	
</script>


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
			out.print("<td><input type='button' class='change_request' name='change_request' onclick='change("+course.getCourseId()+",\""+course.getCourseName()+"\")' value='交换' /></td>");
			out.print("</tr>");
		}
			
		%>
		
<div id="div1" style="background-color:#Ece3eb">

   <div id="spandiv" align="right" style="background-color:#C70E5C;">
   		<span id="span1" style="cursor:pointer">关闭</span>
   </div>

   <p><p>
   <div id="formdiv" >
   <form>

		想要课程的课程号:<input  type="text" id="courseCode" /><p />


	</form>
	</div>
</div>
		
<center>
	<font color="#0099CC">选课学分： <%=selectedCredit %>分，免听学分： <%=nlistenCredit %>分，总学分： <%=(selectedCredit + nlistenCredit) %>分 </font><font color="#FF00FF" face="楷体">（你的限选学分：<%=user.getLimitCredit() %>分 </font>） 
</center>
</body>
</html>