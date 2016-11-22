<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>选课子系统</title>
<style type="text/css">
.centerDiv{margin:0 auto;display:table}
</style>
</head>
<body>
	<div style="margin-top:65px;margin-bottom:15px" class="centerDiv">
		<form id="loginForm">
			<font size="3">
			请输入学号
			<input type="text" name="stu_no" size="10" maxlength="10">
			请输入密码
			<input type="password" name="passwd" size="10" maxlength="20">
			校验码：
			<input name="GetCode" type="text" size="6">
			<img src="" alt="看不清？点击更换" onclick="this.src=this.src">
			</font>
		</form>
	</div>
	<div style="margin-bottom:70px" class="centerDiv">
		<table>
			<tr>
				<td>
					<input type="button" value="选 课/退 课" onClick="hint()">
				</td>
				<td>
					<input type="button" value="修 改 密 码" onClick="hint()">
				</td>
				<td>
					<input type="button" value="选 课 指 南" onClick="window.open('notice.htm','hint','scrollbars=yes,width=800,height=460');">
				</td>
				<td>
					<input type="button" value="退 出 系 统" onClick="window.opener=null;window.open('','_self');window.close();">
				</td>
			</tr>
		</table>
	</div>
	<hr color="#006782">
	<div style="margin-bottom:40px" class="centerDiv">
		<font color="#F7556D" size="4"><b>重要事项</b></font>
	</div>
	<font size="2">
		<p align="left">
			1. 为获得良好的视觉效果，建议使用<FONT COLOR="#FF00FF">IE</FONT>浏览器，并请将屏幕区域设置为<FONT COLOR="#8000FF">1024x768</FONT>方式，方法如下 (鼠标操作顺序)： 开始-->设置(<U>S</U>)-->控制面板(<U>C</U>)-->显示-->设置-->屏幕区域-->1024x768象素-->确定
		</p>
		<p align="left">
			2. 为保证每次操作结果正常显示，<font color="#FF00FF" size="3"><B><U>最好</U></B>将浏览器的缓冲(Cache)文件检查方式设置为“每次查验”，设置方法如下(鼠标操作顺序)： IE浏览器：查看(<U>V</U>)-->Internet 选项(<U>O</U>)-->Internet临时文件-->设置(<U>S</U>)--> 每次访问此页时检查(<U>E</U>)-->确定-->确定</font>
		</p>
		<p align="left">
			3. 如果错误窗口无法弹出，<font color="#FF00FF" size="3">请在IE设置的兼容性视图设置中将选课地址192.168.240.168添加进去</font>
		</p>
		<p align="left">
			4. 如忘记选课密码，请带上证件到所在学院教务秘书处查询
		</p>
		<p align="left">
			5. <em><font color="#408080">如选课中出现问题，可拨打电话或发送email；Tel:26536174/26535419,email:taorui@szu.edu.cn</font></em>
		</p>
	</font>
	<hr color="#006782">
</body>
</html>