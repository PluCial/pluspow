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
<%@ page import="com.pluspow.App" %>
<%
Lang localeLang =(Lang) request.getAttribute("localeLang");
Properties appProp = (Properties) request.getAttribute("appProp");
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
	</div>
	
	<div class="modal-body">
		<div class="row">
			<%
				for(Lang lang: Lang.values()) {
			%>
			<div class="col-sm-6 col-xs-12">
				<div class="lang-block" style="padding: 10px 20px;">
					<img class="align-middle" style="width:32px;vertical-align:middle;" src="<%=PathUtils.getCountryFlagUrl(lang) %>"> 
					<a class="link align-middle" href="<%=App.APP_BASE_LANG == lang ? "/" : "/l-" + lang + "/" %>">
						<%=appProp.getProperty("lang." + lang.toString()) %> (<%=lang.getName() %>)
					</a>
				</div>
			</div>
			<%} %>
		</div>
	</div><!-- /modal-body -->

</body>
</html>