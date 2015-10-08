<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.utils.*" %>
<%@ page import="java.util.Properties" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
boolean isLocal = Boolean.valueOf((String) request.getAttribute("isLocal"));
boolean isEditPage = Boolean.valueOf((String) request.getParameter("isEditPage"));
Properties appProp = (Properties) request.getAttribute("appProp");
%>
	<section id="spot-sub-page-top" style="background-image:
                  url(<%=PathUtils.getSpotBackgroundImageUrl(spot) %>)">

		<div id="spot-catch" class="text-center"><!-- spot-image -->
                  
			<div class="detail">
				<h1>
					<a href="<%=PathUtils.spotPage(spot.getSpotId(), spot.getLangUnit().getLang(), isLocal, true) %>"><%=spot.getName() %></a>
				</h1>
				<p><%=spot.getCatchCopy() %></p>
			</div>

		</div><!-- spot-catch end-->
			
		<a id="langs-select-btn"
			data-toggle="modal" 
			data-backdrop="static"
			data-target="#selectLangModel" 
			style="color:#fff"
			href="/spot/selectLang?spotId=<%=spot.getSpotId() %>&lang=<%=spot.getLangUnit().getLang().toString() %>">
			<img class="align-middle" style="width:32px;vertical-align:middle;" src="<%=PathUtils.getCountryFlagUrl(spot.getLangUnit().getLang()) %>"> 
			<%=appProp.getProperty("lang." + spot.getLangUnit().getLang().toString()) %> <i class="fa fa-chevron-down"></i>
		</a>
	</section>