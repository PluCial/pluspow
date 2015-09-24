<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%
Client client =(Client) request.getAttribute("client");
boolean isClientLogged = Boolean.valueOf((String) request.getAttribute("isClientLogged"));
%>
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
						<li><a href="/client/account/selectSpot"><i class="fa fa-home"></i> マイページ</a></li>
						<li><a href="/client/logout"><i class="fa fa-sign-out"></i> ログアウト</a></li>
						<%}else { %>
	                    <li><a href="/client/register"><i class="fa fa-plus"></i> アカウント作成</a></li>
            			<li><a href="/client/login"><i class="fa fa-sign-in"></i> ログイン</a></li>
            			<%} %>
                    </ul>
				</nav><!--/ Navigation end -->
			</div><!--/ Row end -->
		</div><!--/ Container end -->
	</header><!--/ Header end -->