<%@ page contentType="application/json;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json"%>
<json:array items="${stations}" var="station">
	<json:object>
		<json:property name="stationId" value="${station.stationId}" />
		<json:property name="ip" value="${station.lastDataFrame.ip}" />
		<json:property name="port" value="${station.lastDataFrame.port}" />
		<json:property name="master" value="${station.masterOn}" />
		<json:property name="secondary" value="${station.secondaryOn}" />
		<json:object name="lastDataFrame">
			<json:property name="voltage" value="${station.lastDataFrame.voltage}" />
			<json:property name="electricity" value="${station.lastDataFrame.electricity}" />
			<json:property name="waterGage" value="${station.lastDataFrame.waterGage}" />
			<json:property name="waterLevel" value="${station.lastDataFrame.waterLevel}" />
			<json:property name="time" value="${station.lastDataFrame.time}" />
		</json:object>
	</json:object>
</json:array>
<%--
<c:out value='<fmt:formatDate value="${station.lastDataFrame.time}" pattern="yyyy-MM-dd HH:mm:ss" />'/>
--%>