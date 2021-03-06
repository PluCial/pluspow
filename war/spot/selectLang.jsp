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
		#selectLangModel strong {
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
			Select Language
		</p>
	</div>
	
	<div class="modal-body">
		<div class="row">
			<%
				for(Lang lang: spot.getLangs()) {
			%>
			<div class="col-xs-6">
				<div class="lang-block row" style="padding: 10px 20px;">
					<%if(lang == localeLang) { %>
					<div class="col-sm-3">
						<img class="img-responsive" style="" src="<%=PathUtils.getCountryFlagUrl(lang) %>"> 
					</div>
					<div class="col-sm-9">
						<strong><%=appProp.getProperty("lang." + lang.toString()) %><br />(<%=lang.getName() %>)</strong>
					</div>
					<%}else { %>
					<div class="col-sm-3">
						<a class="link" href="<%=PathUtils.spotPage(spot.getSpotId(), lang, isLocal, isClientLogged) %>">
							<img class="img-responsive" style="" src="<%=PathUtils.getCountryFlagUrl(lang) %>"> 
						</a>
					</div>
					<div class="col-sm-9">
						<a class="link" href="<%=PathUtils.spotPage(spot.getSpotId(), lang, isLocal, isClientLogged) %>">
							<%=appProp.getProperty("lang." + lang.toString()) %><br />(<%=lang.getName() %>)
						</a>
					</div>
					<%} %>
				</div>
			</div>
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