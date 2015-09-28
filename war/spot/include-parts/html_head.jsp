<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%
String pageTitle = (String) request.getAttribute("pageTitle");
String pageDescription = (String) request.getAttribute("pageDescription");
%>

	<!-- Basic Page Needs
	================================================== -->
	<meta charset="utf-8">
    <title><%=App.APP_DISPLAY_NAME %><%=pageTitle == null ? "" : " | " + pageTitle %></title>
    <meta name="description" content="<%=pageDescription == null ? "" : pageDescription %>">	
	<meta name="author" content="">

	<!-- Mobile Specific Metas
	================================================== -->
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

	<!-- Favicons
	================================================== -->
	<link rel="icon" href="img/favicon/favicon-32x32.png" type="image/x-icon" />
	<link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/favicon/favicon-144x144.png">
	<link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/favicon/favicon-72x72.png">
	<link rel="apple-touch-icon-precomposed" href="img/favicon/favicon-54x54.png">
	
	<!-- CSS
	================================================== -->
	
	<!-- Bootstrap -->
	<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">
	<!-- Template styles-->
	<link rel="stylesheet" href="/spot/css/style.css">
	<!-- Responsive styles-->
	<link rel="stylesheet" href="/spot/css/responsive.css">
	<!-- FontAwesome -->
	<link rel="stylesheet" href="/spot/css/font-awesome.min.css">
	<!-- Animation -->
	<link rel="stylesheet" href="/spot/css/animate.css">
	<!-- Prettyphoto -->
	<link rel="stylesheet" href="/spot/css/prettyPhoto.css">
	<!-- Owl Carousel -->
	<link rel="stylesheet" href="/spot/css/owl.carousel.css">
	<link rel="stylesheet" href="/spot/css/owl.theme.css">
	<!-- Flexslider -->
	<link rel="stylesheet" href="/spot/css/flexslider.css">
	<!-- Flexslider -->
	<link rel="stylesheet" href="/spot/css/cd-hero.css">
	<!-- Style Swicther -->
<!-- 	<link id="style-switch" href="/spot/css/presets/preset3.css" media="screen" rel="stylesheet" type="text/css"> -->
	<!-- Style Custom -->
	<link rel="stylesheet" href="/spot/css/custom2.css">

	<!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

	