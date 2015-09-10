<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.utils.*" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
SupportLang lang =(SupportLang) request.getAttribute("lang");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
boolean isEditPage = Boolean.valueOf((String) request.getParameter("isEditPage"));
List<SupportLang> suppertLangList = (List<SupportLang>) request.getAttribute("suppertLangList");
%>
	<section id="spot-home" style="background-image:
                  url(<%=spot.getBackgroundImageUrl() %>)">
<!-- <div class="parallax-overlay"></div> -->
		<div id="spot-catch" class="spot-detail-inner"><!-- spot-image -->
                  
			<div class="detail">
				<h1>
					<a href="/+<%=spot.getSpotId() %>/l-<%=lang.toString() %>/"><%=spot.getName() %></a>
				</h1>
				<p><%=spot.getCatchCopy() %></p>
			</div>

		</div><!-- spot-catch end-->
			
		<a id="langs-select-btn"
			data-toggle="modal" 
			data-backdrop="static"
			data-target="#selectLangModel" 
			style="color:#fff"
			href="/spot/selectLang?spotId=<%=spot.getSpotId() %>&lang=<%=lang.toString() %>">
			<img style="width:25px;vertical-align: middle;" class="flag-image" src="/images/flag/<%=lang.toString().toUpperCase() %>.png" />
			<%=spot.getLangUnit().getLang().getName() %> <i class="fa fa-chevron-down"></i>
		</a>
	</section>