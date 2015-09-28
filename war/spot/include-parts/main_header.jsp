<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%
Lang localeLang =(Lang) request.getAttribute("localeLang");
Client client =(Client) request.getAttribute("client");
Spot spot =(Spot) request.getAttribute("spot");
boolean isClientLogged = Boolean.valueOf((String) request.getAttribute("isClientLogged"));
%>
<fmt:setLocale value="<%=localeLang.toString() %>" />
	<!-- Header start -->
	<header id="header" class="navbar-fixed-top header6" role="banner">
		<div class="container">
			<div class="row">
				<!-- Logo start -->
				<div class="navbar-header">
				    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
				        <span class="sr-only">Toggle navigation</span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				        <span class="icon-bar"></span>
				    </button>
				    <div class="navbar-brand">
				    	<a href="/" ><%=App.APP_DISPLAY_NAME %><i class="fa fa-map-marker"></i></a>
				    </div>           
				</div><!--/ Logo end -->
				<nav class="collapse navbar-collapse clearfix" role="navigation">
					<ul class="nav navbar-nav navbar-right">
						<%if(isClientLogged) { %>
						<li><a href="/client/account/selectSpot"><i class="fa fa-home"></i> <fmt:message key="mainHeader.nav.myPage" /></a></li>
						<li><a href="/client/account/dashboard/?spotId=<%=spot.getSpotId() %>"><i class="fa fa-dashboard"></i> <fmt:message key="mainHeader.nav.dashboard" /></a></li>
						<li><a href="/client/logout"><i class="fa fa-sign-out"></i> <fmt:message key="mainHeader.nav.logout" /></a></li>
						<%}else { %>
	                    <li><a href="/client/register"><i class="fa fa-plus"></i> <fmt:message key="mainHeader.nav.register" /></a></li>
            			<li><a href="/client/login"><i class="fa fa-sign-in"></i> <fmt:message key="mainHeader.nav.login" /></a></li>
            			<%} %>
                    </ul>
				</nav><!--/ Navigation end -->
			</div><!--/ Row end -->
		</div><!--/ Container end -->
	</header><!--/ Header end -->