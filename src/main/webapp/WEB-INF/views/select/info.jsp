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
	<br>
	<br>
	<div align="center">
		<table width="75%" border="0">
			<tr>
				<td><div align="center">
				<%
				String msg = (String) request.getAttribute("msg");
				if(msg != null){
					out.print(msg);
				}
				%>
				</div></td>
			</tr>
		</table>
	</div>
</body>
</html>