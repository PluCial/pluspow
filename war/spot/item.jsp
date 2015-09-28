<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pluspow.utils.*" %>
<%
Spot spot = (Spot) request.getAttribute("spot");
Item item = (Item) request.getAttribute("item");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
%>
<!DOCTYPE html>
<html lang="<%=spot.getLangUnit().getLang() %>">
<head>
	<jsp:include page="/spot/include-parts/html_head.jsp" />
	<style>
	</style>
</head>
	
<body>

	<div class="body-inner">
		<!-- Header start -->
		<jsp:include page="/spot/include-parts/main_header.jsp" />
		<!-- Header end -->

		<!-- Slider start -->
		<jsp:include page="/spot/include-parts/spot_sub_page_top.jsp" />
    	<!--/ Slider end -->


		<!-- Portfolio item start -->
		<section id="item-detail">
			<div class="container">
				<!-- Portfolio item row start -->
				<div class="row">
					<!-- Portfolio item slider start -->
					<div class="col-lg-7 col-md-7 col-sm-12 col-xs-12">
						<div class="portfolio-slider">
							<div class="flexportfolio flexslider">
								<ul class="slides">
									<li>
										<img src="<%=item.getItemImageUrl() + "=s800" %>" alt="">
										<%if(isOwner) { %>
										<a id="background-image-btn" 
											style="top:30px" 
											class="btn btn-default" 
											href="/spot/secure/changeGcsRes?spotId=<%=spot.getSpotId() %>&lang=<%=spot.getLangUnit().getLang() %>&role=<%=GcsResRole.ITEM_IMAGE %>&itemId=<%=item.getKey().getName() %>&resourcesKey=<%=item.getItemImageResKey() %>">
												<i class="fa fa-file-image-o"></i> 背景画像</a>
										<%} %>
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!-- Portfolio item slider end -->

					<!-- sidebar start -->
				<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
					<div class="sidebar">
						<div class="portfolio-desc">
							
							<h2>
								<span id="<%=item.getNameResKey() %>"><%=item.getName() %></span>
								<%if(isOwner) { %>
								<a data-toggle="modal" 
									data-backdrop="static"
									data-target="#textResModal" 
									style="color:#323232;"
									href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resourcesKey=<%=item.getNameResKey() %>">
									<i class="fa fa-pencil-square-o edit-mode"></i>
								</a>
								<%} %>
							</h2>
							
							<%if(item.getPrice() > 0 || isOwner) { %>
							<div class="price-label">
								<i class="fa fa-jpy"></i> <span id="item-price"><%=item.getPriceString() %></span>
								<%if(isOwner) { %>
								<!-- 修正モード -->
								<a data-toggle="modal" 
									data-backdrop="static"
									data-target="#itemPriceModal" 
									style="color:#333"
									href="/spot/secure/editItemPrice?spotId=<%=spot.getSpotId() %>&itemId=<%=item.getKey().getName() %>">
									<i class="fa fa-pencil-square-o edit-mode"></i>
								</a>
								<%} %>
							</div>
							<%} %>
							
							<p>
								<span id="<%=item.getDetailResKey() %>"><%=Utils.getJspDisplayString(item.getDetail()) %></span>
								<%if(isOwner) { %>
								<a data-toggle="modal" 
									data-backdrop="static"
									data-target="#textResModal" 
									style="color:#6A6A6A;"
									href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resourcesKey=<%=item.getDetailResKey() %>">
									<i class="fa fa-pencil-square-o edit-mode"></i>
								</a>
								<%} %>
							</p>
						</div>
					</div>
				</div>
				<!-- sidebar end -->
				</div><!-- Portfolio item row end -->
			</div><!-- Container end -->
		</section><!-- Portfolio item end -->
		
		<!-- Contact Info start -->
<%-- 		<jsp:include page="/spot/include-parts/spot_contact_info.jsp" /> --%>
		<!--/ Contact Info end -->
		
		<!-- item-list start -->
		<section id="service" class="portfolio portfolio-box">
			<div class="container">
				<div class="row">
					<jsp:include page="/spot/include-parts/spot_item_list.jsp" />
				</div>
			</div>
		</section>
		<!-- item-list end -->
	

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/spot/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	
	<jsp:include page="/spot/include-parts/dialog_modal.jsp">
		<jsp:param name="modelId" value="selectLangModel" />
	</jsp:include>
	
	<%if(isOwner) { %>
	<!-- secure JS start -->
	<jsp:include page="/spot/include-parts/dialog_modal.jsp">
		<jsp:param name="modelId" value="textResModal" />
	</jsp:include>
	<jsp:include page="/spot/include-parts/dialog_modal.jsp">
		<jsp:param name="modelId" value="itemPriceModal" />
	</jsp:include>
	<!-- secure JS end -->
	<%} %>
</body>
</html>
