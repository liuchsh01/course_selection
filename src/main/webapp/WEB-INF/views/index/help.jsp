<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
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
	<CENTER><H3>使用说明</H3></CENTER>
	<PRE>
 1. 顶部窗口是已选课程列表。
 2. 左边窗口是开课单位列表，点击后可在本说明窗口中进行选课操作。
 3. 底部窗口是操作按钮。
    选课时，先在课程表中点选课程号左侧的方框（注意区分“必修”和“选修”），然后按“选课”按钮。
    退课时，先在已选课程列表中点选左侧的方框，然后按“退课”按钮。
   “清除”按钮用于一次性清除所有点选框的标记，并不影响已选的课程。
    选课完成后务必按“退出”按钮，避免他人修改自己的选课结果。
    如需查询课程，可先选择查询类型，再在查询类型右边的输入框中输入查询内容，点击查询按钮即可快速
    从课程列表中找到相应的课程；
    课程表按钮根据你已选的课程输出每周的课程安排;
    选课结果按钮输出你已选的课程号及选课类别；
    个人信息按钮可察看个人选课的状态（是否允许选课）及个人的一些信息通知等；    
 4. 按“使用说明”按钮可再次查看本说明
	</PRE>
</body>
</html>