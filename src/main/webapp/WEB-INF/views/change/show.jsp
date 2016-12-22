<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"  import="java.util.List,com.course.selection.entity.ChangList"%>
    
<%
	String path = request.getContextPath();
	String basePath = path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>课程交换列表</title>
</head>
<body marginwidth="0" marginheight="0" style="font-size:14px">
	<table border="1" cellspacing="0" align="center">
		<thead>
			<tr>
				<td>课程号（出）</td>
				<td>课程名（出）</td>
				<td>上课时间（出）</td>
				<td>课程号（进）</td>
				<td>课程号（进）</td>
				<td>上课时间（进）</td>
			</tr>
		</thead>
		
		<%
			List<ChangList> list = (List<ChangList>)request.getAttribute("changeInfo");
			for(int i=0; i<list.size(); i++){
				out.print("<tr><td>"+list.get(i).getOut_courseCode()+"</td>");
				out.print("<td>"+list.get(i).getOut_courseName()+"</td>");
				out.print("<td>"+list.get(i).getOut_time());
				out.print("<td>"+list.get(i).getin_courseCode());
				out.print("<td>"+list.get(i).getin_courseName());
				out.print("<td>"+list.get(i).getin_time());
				out.print("<td><input type='button' value='交换'  onclick='change("+list.get(i).getOut_courseId()+","+list.get(i).getin_courseId()+","+list.get(i).getOut_userId()+")' /></tr>");
			}
		%>
	</table>

<script type="text/javascript">
	function change(out_courseId, in_courseId, out_userId){
		$.ajax({
			url:"http://localhost:8080/selection/changeCourse/changeCourse.do",
			data:{
				"out_courseId":out_courseId,
				"in_courseId":in_courseId,
				"out_userId":out_userId
			},
			success:function(data){
				if(data.msg == "课程冲突"){
					alert(data.msg);
				}
				if(data.msg == "课程交换成功"){
					alert(data.msg);
					location.reload(true);
					parent.result.location.reload(true);
				}
			}
		});
	}
</script>

</body>
</html>