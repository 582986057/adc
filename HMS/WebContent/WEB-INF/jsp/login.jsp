<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html> 
<html lang="en"> 
<head> 
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1"> 
    <title>捷途软件--智能办公</title> 
    
       
    <link href="${ctx}/css/base.css" rel="stylesheet">
    <link href="${ctx}/css/login.css" rel="stylesheet">
    <link href="${ctx}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx }/resources/bootstrap/js/bootstrap.min.js"></script>
     <script type="text/javascript" src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
     <script type="text/javascript" src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
     <link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
	 <script type="text/javascript">
		$(function(){
			
			//如果当前 的登陆页面不是最顶层页面
			if(window.location!=top.window.location){
				//将当前登陆页面作为最顶层页面处理
				top.window.location = window.location;
			}
			
			if("${message}"){
				$.messager.alert('提示信息',"${message}",'warning');
			}
			//为‘验证码’绑定事件
			$("#vimg").click(function(){
				//改变图片的src属性，如果src属性值  不发生变化 那么请求不会发至后台，因此此处会给url地址后带入一个随机数
				$("#vimg").prop("src","${ctx}/createCode.jspx?date="+Math.random());
			})
			  
			
			$("#login_id").click(function(){
				//获取账号密码验证码
				var name=$("#userId").val();
				
				var passWord=$("#passWord").val();
				
				var vcode=$("#vcode").val();
				
				//通过正则表达式校验账号格式是否正确、
				if(!/^[a-zA-Z0-9_]{5,16}$/.test(name)){
					$.messager.alert('提示信息','账号格式不正确！','warning')
				}else if(!/^[a-zA-Z0-9_]{5,16}$/.test(passWord)){
					$.messager.alert('提示信息','密码格式不正确！','warning')
				}else if(!/^[a-zA-Z0-9_]{4}$/.test(vcode)){
					$.messager.alert('提示信息','验证码格式不正确！','warning')
				}else{
					 $.ajax({
		                url :"${ctx}/identity/user/ajaxLogin.jspx",//指定请求的地址
		                data:"loginName="+name+"&password="+passWord+"&vCode="+vcode, //指定传递至后台的参数信息
		                type :"POST",//指定请求方式
		                dataType :"text",
		                success : function (msg){//succcess：后台响应成功时的回调函数 error：后台响应失败时的回调函数
		                	
		                	//隐形式boolean类型
		                	if(msg){
		                		//给用户提示信息
		                		$.messager.alert('提示信息','msg','warning')
		                		//刷新验证码
		        				$("#vimg").prop("src","${ctx}/createCode.jspx?date="+Math.random());
		                	}else{
		                		//跳转至首页
		                		window.location="${ctx}/main.jspx";
		                	}
		                },
		                error:function(){
		                	$.messager.alert('提示信息','网络异常','warning')
		                }
		            }) 
				}
			})
			$(window).keydown(function(event){
				//event.keyCode == 13 表示用户按了回车键
				  if(event.keyCode == 13){
					  //触发 登陆按钮的点击事件
					  $("#login_id").trigger("click");
					
				  }
				});
		});
	 </script>
</head> 
<body>
	<div class="login-hd">
		<div class="left-bg"></div>
		<div class="right-bg"></div>
		<div class="hd-inner">
			<span class="logo"></span>
			<span class="split"></span>
			<span class="sys-name">智能办公平台</span>
		</div>
	</div>
	<div class="login-bd">
		<div class="bd-inner">
			<div class="inner-wrap">
				<div class="lg-zone">
					<div class="lg-box">
						<div class="panel-heading" style="background-color: #11a9e2;">
							<h3 class="panel-title" style="color: #FFFFFF;font-style: italic;">用户登陆</h3>
						</div>
						<form id="loginForm">
						   <div class="form-horizontal" style="padding-top: 20px;padding-bottom: 30px; padding-left: 20px;">
								
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<input class="form-control" id="userId" name="userId" type="text" placeholder="账号/邮箱">
									</div>
								</div>
				
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<input  class="form-control"  id="passWord" name="passWord" type="password" placeholder="请输入密码">
									</div>
								</div>
								
				
								<div class="form-group" style="padding: 5px;">
									<div class="col-md-11">
										<div class="input-group">
										<input class="form-control " id="vcode" name="vcode" maxlength="4" type="text" placeholder="验证码">
										<span class="input-group-addon" id="basic-addon2"><img class="check-code" id="vimg"  style="cursor:pointer" alt="" src="${ctx}/createCode.jspx"></span>
										</div>
									</div>
								</div>
				
						</div>
							<div class="tips clearfix">
											<label><input type="checkbox" checked="checked">记住用户名</label>
											<a href="javascript:;" class="register">忘记密码？</a>
										</div>
							<div class="enter">
								<a href="javascript:;" id="login_id" class="purchaser" >登录</a>
								<a href="javascript:;" class="supplier" onClick="javascript:window.location='main.html'">重 置</a>
							</div>
						</form>
					</div>
				</div>
				<div class="lg-poster"></div>
			</div>
	</div>
	</div>
	<div class="login-ft">
		<div class="ft-inner">
			<div class="about-us">
				<a href="javascript:;">关于我们</a>
				<a href="javascript:;">法律声明</a>
				<a href="javascript:;">服务条款</a>
				<a href="javascript:;">联系方式</a>
			</div>
			<div class="address">
			地址：广州市天河区
			&nbsp;邮编：510000&nbsp;&nbsp;
			分享知识，传递希望&nbsp;版权所有</div>
			<div class="other-info">
			建议使用火狐、谷歌浏览器，不建议使用IE浏览器！</div>
			
		</div>
	</div>
</body> 
</html>
