<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%
Spot spot = (Spot) request.getAttribute("spot");
List<SpotLangUnit> langUnitList = (List<SpotLangUnit>) request.getAttribute("langUnitList");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<style>
		#selectLangModel .flag-image {
			width: 32px;
		}
		#selectLangModel a.link,  #selectLangModel .row a{
			color: #069;
			font-size: 14px;
		}
	</style>
</head>
<body>
	<div class="modal-header">
		<div class="pull-right">
			<button class="btn btn-box-tool btn-sm" data-dismiss="modal"><i class="fa fa-times"></i></button>
		</div>
		<p style="margin-bottom: 0;"><strong>元の言語:</strong> 
			<img class="flag-image" src="/images/flag/<%=spot.getBaseLang().toString().toUpperCase() %>.png" />
			<a href="/+<%=spot.getSpotId() %>/l-<%=spot.getBaseLang().toString() %>/" class="link">
				<%=spot.getBaseLang().getName() %>
			</a>
		</p>
	</div>
	
	<div class="modal-body">
		<div class="row">
			<%for(SupportLang lang: spot.getSupportLangs()) {
				if(lang != spot.getBaseLang()) {
			%>
			<div class="col-sm-4 col-xs-6">
				<img class="flag-image" src="/images/flag/<%=lang.toString().toUpperCase() %>.png" />
				<a href="/+<%=spot.getSpotId() %>/l-<%=lang.toString() %>/"><%=lang.getName() %></a>
			</div>
			<%	} %>
			<%} %>
		</div>
	</div><!-- /modal-body -->
	
	<%if(isOwner) { %>
	<div class="modal-footer">
		<div class="pull-right">
			<%if(spot.getLangUnit().getLang() != spot.getBaseLang()) {%>
			<a href="/spot/secure/transSelectLang?spotId=<%=spot.getSpotId() %>" class="btn btn-default btn-sm"><i class="fa fa-language"></i> 再翻訳</a>
			<%} %>
			
			<a href="/spot/secure/transSelectLang?spotId=<%=spot.getSpotId() %>" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i> 言語を追加</a>
		</div>
		<%if(spot.getLangUnit().getLang() != spot.getBaseLang()) {%>
		<div class="pull-left">
			<a class="link" style="color:#333" href="/spot/secure/transSelectLang?spotId=<%=spot.getSpotId() %>"><i class="fa fa-trash"></i> <%=spot.getLangUnit().getLang().getName() %>ページを削除</a>
		</div>
		<%} %>
	</div>
	<%} %>
</body>
</html>