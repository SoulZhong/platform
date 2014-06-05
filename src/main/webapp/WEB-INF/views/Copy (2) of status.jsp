<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"
%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page session="false"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<title>供水管理系统</title>
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="<c:url value="/resources/jqueryui/1.8/themes/base/jquery.ui.all.css" />" rel="stylesheet" type="text/css" />

<style>
body {
	width: 100%;
}

div.wrapper {
	width: 100%;
	text-align: center;
}

.stationInfo {
	border: 2px solid black;
	display: inline-block;
}
</style>
<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.0.4/angular.min.js"></script>
<script type="text/javascript">
	function command(stationId, number, type, $http) {

		$http.get('/action', {
			params : {
				"stationId" : stationId,
				"no" : number,
				"type" : type
			}
		}).success(function(data) {
			console.log("success:" + data);
		});

	}
</script>
</head>
<body ng-controller="StatusController">
	<div class="wrapper">
		<h1>供水控制系统</h1>
		<div class="stationInfo">
			<table ng-repeat="item in items">
				<tr>
					<td>站点号</td>
					<td>{{item.stationId}}</td>
				</tr>
				<tr>
					<td>网络地址</td>
					<td>{{item.ip}}:{{item.port}}</td>
				</tr>
				<tr>
					<td>电压</td>
					<td>{{item.voltage}}</td>
				</tr>
				<tr>
					<td>电流</td>
					<td>{{item.electricity}}</td>
				</tr>
				<tr>
					<td>水压</td>
					<td>{{item.waterGage}}</td>
				</tr>
				<tr>
					<td>水位</td>
					<td>{{item.waterLevel}}</td>
				</tr>
				<tr>
					<td>更新时间</td>
					<td>{{item.time}}</td>
				</tr>
				<tr>
					<td>主泵</td>
					<td><input type="button" value="开启" onclick="command(1, 1, 1);" /> <input type="button" value="关闭" /></td>
				</tr>
				<tr>
					<td>辅泵</td>
					<td><input type="button" value="开启" /><input type="button" value="关闭" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>