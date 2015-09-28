<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pluspow.utils.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Properties" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
Properties appProp = (Properties) request.getAttribute("appProp");
%>
<!DOCTYPE html>
<html>
	<head>
    	<jsp:include page="/include-parts/html_head.jsp" />
    	<style>
.content {
	margin-top: 2em;
}

h2 {
	margin-bottom: 2em;
}

.lang-box {
	background-color: #fff;
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, .26);
	padding: 0 10px;
	line-height: 30px;
	font-weight: 700;
	display: table;
	width: 100%;
	margin-bottom: 2em;
}

.lang-box.support {
	background-color: #fff;
}

.lang-box>* {
	display: table-cell;
	vertical-align: middle;
	min-height: 100px;
}
.lang-box .flg {
}
.lang-box .flg img {
	width: 64px;
}
.lang-box .lang-name {
	width: 100%;
    padding: 0 10px;
}
.lang-box .support-icon {
	color: #00aacc;
	display: none;
}
.lang-box.support .support-icon {
	display: table-cell;
}

a.lang-box {
	color: #6A6A6A;
}
a.lang-box:hover {
	background-color: #fff;
}
a.lang-box:hover .support-icon {
	display: table-cell;
	color: #333;
}
</style>
	</head>
	<body class="info">
	
		<div class="body-inner">
			<!-- Header start -->
 			<jsp:include page="/include-parts/main_header.jsp" />
			<!-- Header end -->

			<section id="main-container">
				<!-- Full Width Column -->
				<div class="container">
					<div class="content">
					
 						<h2 style="text-align: center;">
							<i class="fa fa-map-marker"></i> 翻訳する言語の選択
						</h2>
					
						<div>
							<div class="row">
							
								<%for(int i=0; i < Lang.values().length; i++) {
									Lang lang = Lang.values()[i];
									boolean isTranslated = spot.getLangs().indexOf(lang) >= 0; %>
								<div class="col-sm-4 col-xs-12">
									<%if(isTranslated) { %>
									<div class="lang-box <%=isTranslated ? "support" : "" %>">
										<span class="flg">
											<img src="<%=PathUtils.getCountryFlagUrl(lang) %>">
										</span>
										<span class="lang-name"><%=lang.getName() %></span>
										<span class="support-icon">
											<i class="fa fa-check-circle"></i>
										</span>
									</div>
									<%}else { %>
									<a href="/spot/secure/trans?spotId=<%=spot.getSpotId() %>&objectType=<%=ObjectType.SPOT%>&transLang=<%=lang.toString() %>" class="lang-box <%=isTranslated ? "support" : "" %>">
										<span class="flg">
											<img src="<%=PathUtils.getCountryFlagUrl(lang) %>">
										</span>
										<span class="lang-name">
											<%=lang.getName() %><br />
											(<%=appProp.getProperty("lang." + lang.toString()) %>)
										</span>
										<span class="support-icon">
											<i class="fa fa-exchange"></i>
										</span>
									</a>
									<%} %>
								</div>
								<%} %>

							</div>
						</div>
					</div>
					
				</div><!-- /.content-wrapper -->
			</section>
			
		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
		
		</div><!-- ./wrapper -->

	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	</body>
</html>
