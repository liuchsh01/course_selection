<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>changeCourse测试页面</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
</head>
<body>
<% 
    int a=1;
    int b=2;
    int c=2;
    out.print("<a href='http://localhost:8080/selection/changeCourse/changeCourse.do?out_courseId="+a+"&in_courseId="+b+"&out_userId="+c+"'>交换课程</a>");
  // <a href='http://localhost:8080/selection/changeCourse/changeCourse.do?out_courseId=3 & in_courseId=4 & out_userId=2'>交换课程</a>
 %>
    <a href='http://localhost:8080/selection/changeCourse/changeRequest.do?out_courseId=2&in_courseId=1'>课程交换申请</a>
  	<a href='http://localhost:8080/selection/changeCourse/showChange.do'>测试交换列表</a>
    <button id="change" value="交换" onclick="change_request(1,2)"></button>
    
    <script type="text/javascript">
    	function change_request(out_courseId,in_courseId){
    		$.ajax({
    			url:"http://localhost:8080/selection/changeCourse/changeRequest.do",
    			data:{ 
    				     "out_courseId":out_courseId,
    				     "in_courseId":in_courseId
    			},
    			dataType:"json",
    			scriptCharset:'utf-8',
    			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
    			success:function(data){
    				console.log(data);
    				alert(data.msg);
    			}
    		});
    	}
    </script>
    
   </body>
</html>