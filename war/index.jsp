<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="com.pluspow.utils.*" %>
<%
Lang localeLang =(Lang) request.getAttribute("localeLang");
%>
<fmt:setLocale value="<%=localeLang.toString() %>" />
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/spot/include-parts/html_head.jsp" />
	<!-- Style index -->
	<link rel="stylesheet" href="/css/index.css">
	<link rel="stylesheet" href="/css/country-search.css">
</head>
<body lang="<%=localeLang %>" class="service-top">
	<div class="body-inner">
		<!-- Header start -->
 		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->
		
		<%if(localeLang.isAvailable()) { %>
		<section id="about" class="no-padding">
			<div class="row">
				<div class="col-md-5">
					<div class="contents">
						<h2><fmt:message key="page.index.about.h2" /></h2>
						<h3 class="hidden-xs"><fmt:message key="page.index.about.h3" /></h3>
						<h4 class="hidden-xs"><fmt:message key="page.index.about.h4"><fmt:param><%=Lang.values().length %></fmt:param></fmt:message></h4>
						<div class="text-center action">
							<a href="/info/about" style="margin-right:20px;" class="cd-btn btn btn-default btn-sm solid"><fmt:message key="page.index.about.btn.readMore" /></a>
							<a href="/client/register" class="cd-btn btn btn-primary btn-sm solid"><fmt:message key="page.index.about.btn.StartNow" /></a>
						</div>
						
					</div>
				</div>
				<div class="col-md-7">
					<div class="img-contents">
						<a href="/+<%=App.SAMPLE_SPOT_ID %>/">
							<img class="img-responsive" src="/images/devices_play.png" />
						</a>
					</div>
				</div>
			</div>
		</section>
		<%} %>
		
		<section class="country-search" style="<%=localeLang.isAvailable() ? "" : "padding-top: 50px;" %>">
			<div class="search-wrapper">
				
				<div class="row">
					<div class="col-md-6 text-center">
						<div class="search-form-wapper" style="<%=localeLang.isAvailable() ? "" : "min-height: 98vh;" %>">
							<form action="/<%=Country.JP.toString() %>/search" method="get">
								<h2><fmt:message key="page.index.searchJp.h2" /></h2>
								<p><fmt:message key="page.index.searchJp.p1" /></p>
								<div class="search-area">
									
									<div class="text-search input-group">
		                    			<input type="text" name="keyword" class="form-control" placeholder="<fmt:message key="page.index.search.input.placeholder" />" />
		                    			<span class="input-group-addon"><i class="fa fa-search"></i></span>
		                    			<img class="country-flag" src="/images/flag/flat/JP.png">
		                  			</div>
									
									<div class="select-activity">
										<%for(int i=0; i<SpotActivity.values().length; i++) { %>	
										<input type="checkbox" name="activityArray" value="<%=SpotActivity.values()[i].toString() %>" id="checkbox<%=i %>" checked="checked" />
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
					<video class="visible-lg" autoplay loop muted controls poster="/videos/search_ja_video_default_image.jpg" width="100%">
						<source src="/videos/search_top_ja_superlow.mp4" type="video/mp4" />
						<img src="/videos/search_ja_video_default_image.jpg" width="100%" />
					</video>
				</div>
			</div>
			
		</section>
		
		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
		
	</div>
	
	<jsp:include page="/spot/include-parts/dialog_modal.jsp">
		<jsp:param name="modelId" value="selectLangModel" />
	</jsp:include>
	
	<!-- javaScript start -->
 	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
</body>
</html>
