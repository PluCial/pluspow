<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.utils.*" %>
<%@ page import="java.util.Properties" %>
<%
Spot spot = (Spot) request.getAttribute("spot");
List<SpotLangUnit> langUnitList = (List<SpotLangUnit>) request.getAttribute("langUnitList");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
Lang localeLang =(Lang) request.getAttribute("localeLang");
Properties appProp = (Properties) request.getAttribute("appProp");

boolean isClientLogged = Boolean.valueOf((String) request.getAttribute("isClientLogged"));
boolean isLocal = Boolean.valueOf((String) request.getAttribute("isLocal"));
String scheme = (String) request.getAttribute("scheme");
%>
<fmt:setLocale value="<%=localeLang.toString() %>" />
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
		<p style="margin-bottom: 0;">
			<strong><fmt:message key="page.spot.select.lang.originalLanguage" /></strong> 
			<img class="align-middle" style="width:32px;vertical-align:middle;" src="<%=PathUtils.getCountryFlagUrl(spot.getBaseLang()) %>"> 
			<a href="<%=isLocal || isClientLogged ? PathUtils.spotPage(spot, spot.getBaseLang()) : PathUtils.spotProductionPage(spot.getSpotId(), spot.getBaseLang()) %>" class="link align-middle">
				<%=appProp.getProperty("lang." + spot.getBaseLang().toString()) %> (<%=spot.getBaseLang().getName() %>)
			</a>
		</p>
	</div>
	
	<div class="modal-body">
		<div class="row">
			<%
				for(Lang lang: spot.getLangs()) {
					if(lang != spot.getBaseLang()) {
			%>
			<div class="col-sm-6 col-xs-12">
				<div class="lang-block" style="padding: 10px 20px;">
					<img class="align-middle" style="width:32px;vertical-align:middle;" src="<%=PathUtils.getCountryFlagUrl(lang) %>"> 
					<a class="link align-middle" href="<%=isLocal || isClientLogged ? PathUtils.spotPage(spot, lang) : PathUtils.spotProductionPage(spot.getSpotId(), lang) %>">
						<%=appProp.getProperty("lang." + lang.toString()) %> (<%=lang.getName() %>)
					</a>
				</div>
			</div>
			<%	} %>
			<%} %>
		</div>
	</div><!-- /modal-body -->
	
	<%if(isOwner) { %>
	<div class="modal-footer">
		<div class="pull-right">
			<%if(spot.getLangUnit().getLang() != spot.getBaseLang()) {%>
			<a href="/spot/secure/trans?spotId=<%=spot.getSpotId() %>&objectType=<%=ObjectType.SPOT %>&transLang=<%=spot.getLangUnit().getLang().toString() %>" class="btn btn-default btn-sm"><i class="fa fa-language"></i> 再翻訳</a>
			<%} %>
			
			<a href="/spot/secure/transSelectLang?spotId=<%=spot.getSpotId() %>" class="btn btn-primary btn-sm"><i class="fa fa-plus"></i> 言語を追加</a>
		</div>
		<%if(spot.getLangUnit().getLang() != spot.getBaseLang()) {%>
		<div class="pull-left">
			<a class="link" style="color:#333" href="/spot/secure/setLangInvalid?spotId=<%=spot.getSpotId() %>&objectType=<%=ObjectType.SPOT %>&lang=<%=spot.getLangUnit().getLang() %>&invalid=true"><i class="fa fa-trash"></i> <%=spot.getLangUnit().getLang().getName() %>ページを削除</a>
		</div>
		<%} %>
	</div>
	<%} %>
</body>
</html>