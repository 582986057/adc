<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 引入c标签 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!-- 通过taglib指定引入自定义分页标签 -->
<%@taglib prefix="bjlemon" uri="/lemon-pageTag"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>HMS办公管理系统-用户管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
<meta http-equiv="description" content="This is my page" />
<!-- 引入样式文件 -->
<link href="${ctx}/css/pager.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet"
	href="${ctx }/resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript"
	src="${ctx }/resources/jquery/jquery-1.11.0.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/jquery/jquery-migrate-1.2.1.min.js"></script>
<!-- 导入bootStrap的库 -->
<script type="text/javascript"
	src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/easyUI/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="${ctx}/resources/easyUI/easyui.css">
<script type="text/javascript">
	$(function() {
		if ("${message}") {
			$.messager.alert("提示信息", "${message}", "warning");
		}
		//为全选绑定点击事件
		$("#checkAll").click(function() {

			//获取全选的状态
			var flag = $("#ckickAll").prop("checked");

			//获取所有的子checkbox
			$("[id^='box_']").prop("checked".flag);

			//触发数据行的mouseover 或者mouseout事件
			$("[id^='dataTr_']").trigger(flag ? "mouseover" : "mouseout");
		})

		//为所有的字checkbox绑定点击事件
		$("[id^='box_']").click(function() {

			//获取选中的checkbox的个数
			var len1 = $("[id^='box_']").filter(":checked").length;

			var len2 = $("[id^='box_']").length;

			//控制全选的状态
			$("#checkAll").prop("checked", len1 == len2);

		})
		//为数据行绑定 mouseouver 以及mouseout事件 hover集成了mouseover 以及mouseout事件
		$("[id^='dataTr_']").hover(function() {

			//触发mouseouver事件  给行添加背景色
			$(this).css("background", "#eeccff");

		}, function() {
			//如果当前行中的checkbox是选中的则不去掉背景色

			//获取当前tr的id
			var trId = this.id;

			//根据tr的id获取checkbox的id
			var boxId = trId.replace("dataTr", "box");

			if (!$("#" + boxId).prop("checked")) {
				//触发mouseout事件  去掉背景色
				$(this).css("background", "");
			}

		})
		//为绑定用户按钮绑定点击事件
		
	$("#bindUser").click(function() {
							//获取选中的checkbox
							var checkedBox = $("[id^='box_']").filter(":checked");

							if (checkedBox.length == 0) {
								$.messager.alert('提示信息', "请选择需要绑定的用户信息!",
										'waning');
							} else {
								var arr = new Array();
								for (var i = 0; i < checkedBox.length; i++) {
									var val = checkedBox[i].value;
									arr.push(val);
								}
								//根据角色id以及用户id绑定指定的用户与角色的关系
								window.location = "${ctx}/identity/role/bindUser.jspx?userIds="+arr+"&id=${id}&pageIndex=${pageModel.pageIndex}";
							}

						})

	})
</script>
</head>
<body style="overflow: hidden; width: 98%; height: 100%;">
	<!-- 工具按钮区 -->

	<div class="panel panel-primary" style="padding-left: 5px;">
		<div style="padding-top: 4px; padding-bottom: 4px;">
			<a id="bindUser" class="btn btn-success"><span
				class="glyphicon glyphicon-copy"></span>&nbsp;绑定</a>
		</div>
		<div>
			<table class="table table-bordered">
				<thead>
					<tr style="font-size: 12px;" align="center">
						<th style="text-align: center;"><input id="checkAll"
							type="checkbox" /></th>
						<th style="text-align: center;">账户</th>
						<th style="text-align: center;">姓名</th>
						<th style="text-align: center;">性别</th>
						<th style="text-align: center;">部门</th>
						<th style="text-align: center;">职位</th>
						<th style="text-align: center;">手机号码</th>
						<th style="text-align: center;">邮箱</th>
						<th style="text-align: center;">审核人</th>
					</tr>
				</thead>
				<c:forEach items="${requestScope.users}" var="user" varStatus="stat">
					<tr id="dataTr_${stat.index}" align="center">
						<td><input type="checkbox" name="box" id="box_${stat.index}"
							value="${user.userId}" /></td>
						<td>${user.userId}</td>
						<td>${user.name}</td>
						<td>${user.sex == 1 ? '男' : '女' }</td>
						<td>${ user.dept.name}</td>
						<td>${ user.job.name}</td>
						<td>${user.phone}</td>
						<td>${user.email}</td>
						<td>${user.checker.name}</td>
					</tr>
				</c:forEach>
			</table>


		</div>
		<!-- 
		pageIndex；当前页码
		pageSize：每页显示的记录数
 -->
		<bjlemon:pager pageIndex="${pageModel.pageIndex }"
			pageSize="${pageModel.pageSize }"
			totalNum="${pageModel.recordCount }"
			submitUrl="${ctx}/identity/role/showUnBindRoleUser.jspx?pageIndex={0}&id=${id}"
			pageStyle="black">
		</bjlemon:pager>
	</div>

	<div id="divDialog" style="display: none;">
		<!-- 放置一个添加用户的界面  -->
		<iframe id="iframe" frameborder="0" style="width: 100%; height: 100%;"></iframe>
	</div>

</body>
</html>