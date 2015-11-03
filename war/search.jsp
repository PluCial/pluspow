<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.pluspow.utils.*" %>
<%
Lang localeLang =(Lang) request.getAttribute("localeLang");
String[] selectedActivitys =(String[]) request.getAttribute("selectedActivitys");
String countryString = (String)request.getAttribute("country");
Country country = Country.valueOf(countryString);
%>
<fmt:setLocale value="<%=localeLang.toString() %>" />
<!DOCTYPE html>
<html lang="<%=localeLang.toString() %>">
<head>
	<jsp:include page="/spot/include-parts/html_head.jsp" />
	<link rel="stylesheet" href="/css/search.css">
	<link rel="stylesheet" href="/css/country-search.css">
</head>
	
<body class="home">

	<div class="body-inner">
		<!-- Header start -->
		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->
		
		<section class="country-search" style="padding-top: 50px;">
			<div class="search-wrapper">
				
				<div class="row">
					<div class="col-md-6 col-md-offset-3 text-center">
						<div class="search-form-wapper" style="padding: 30px 0;">
							<form action="/<%=country.toString() %>/search" method="get">
								<div class="search-area">
									
									<div class="text-search input-group">
		                    			<input type="text" ${f:text("keyword")} class="form-control" placeholder="<fmt:message key="page.index.search.input.placeholder" />" />
		                    			<span class="input-group-addon"><i class="fa fa-search"></i></span>
		                    			<img class="country-flag" src="/images/flag/flat/JP.png">
		                  			</div>
									
									<div class="select-activity">
										<%for(int i=0; i<SpotActivity.values().length; i++) { %>	
										<input type="checkbox" name="activityArray" value="<%=SpotActivity.values()[i].toString() %>" id="checkbox<%=i %>" <%=selectedActivitys != null && Arrays.asList(selectedActivitys).contains(SpotActivity.values()[i].toString()) ? "checked=\"checked\"" : "" %> />
										<label for="checkbox<%=i %>" class="checkbox"><i class="<%=SpotActivity.values()[i].getIconClass() %>"></i> <%=SpotActivity.values()[i].getName() %></label>
										<%} %>
									</div>
									
									<button type="submit" class="search-btn btn btn-default">検索</button>
								</div>
							</form>
						</div>
					</div>
				</div>
				
				<div class="video-wrapper" data-video="videos/video" style="background-image: url(/videos/search_ja_video_default_image.jpg)">
				</div>
			</div>
			
		</section>

		<!-- spot-list start -->
		<section id="service" class="portfolio portfolio-box">

			<div class="search-results-container">
				<div class="row results-row">
					<!-- search results start -->
					<jsp:include page="/include-parts/search_results.jsp" />
					<!--/ search results end -->
				</div>
			</div>
			
		</section>
		<!-- spot-list end -->
	

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	
	<!-- waiting dialog -->
	<script type="text/javascript" src="/plugins/waiting-dialog/waiting-dialog.js"></script>
	<!-- /waiting dialog -->
	
	<jsp:include page="/spot/include-parts/dialog_modal.jsp">
		<jsp:param name="modelId" value="selectLangModel" />
	</jsp:include>

</body>
</html>
