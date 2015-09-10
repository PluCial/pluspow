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
									<li><img src="<%=item.getItemImageUrl() + "=s800" %>" alt=""></li>
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
									href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resRole=<%=TextResRole.ITEM_NAME %>&resourcesKey=<%=item.getNameResKey() %>">
									<i class="fa fa-pencil-square-o edit-mode"></i>
								</a>
								<%} %>
							</h2>
							
							<%if(item.getPrice() > 0) { %>
							<div class="price-label"><i class="fa fa-jpy"></i> <span><%=item.getPriceString() %></span></div>
							<%} %>
							
							<p>
								<span id="<%=item.getDetailResKey() %>"><%=Utils.getJspDisplayString(item.getDetail()) %></span>
								<%if(isOwner) { %>
								<a data-toggle="modal" 
									data-backdrop="static"
									data-target="#textResModal" 
									style="color:#6A6A6A;"
									href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resRole=<%=TextResRole.ITEM_DETAIL %>&resourcesKey=<%=item.getDetailResKey() %>">
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
			<jsp:include page="/spot/include-parts/spot_item_list.jsp" />
		</section>
		<!-- item-list end -->
	

		<!-- Footer start -->
		<jsp:include page="/spot/include-parts/main_footer.jsp" />
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
	<!-- secure JS end -->
	<%} %>
	
	<!-- javaScript Map start -->
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?language=<%=spot.getLangUnit().getLang() %>"></script>
	<script>
	jQuery(function($) {
		  "use strict";
		  
		  $("#map").gmap3({
		        map:{
		            options:{
		               center:[<%=spot.getGeoLat() %>,<%=spot.getGeoLng() %>],
		               zoom: 14,
		               scrollwheel: false
		            }
		        },
		        marker:{
		          values:[
		            {address:"<%=spot.getGeoFormattedAddress() %>", data:" Welcome To <%=spot.getName() %> ! ! ", 
		             options:{icon: "http://themewinter.com/html/marker.png"}}
		          ],
		          options:{
		            draggable: false
		          },
		          events:{
		            mouseover: function(marker, event, context){
		              var map = $(this).gmap3("get"),
		                infowindow = $(this).gmap3({get:{name:"infowindow"}});
		              if (infowindow){
		                infowindow.open(map, marker);
		                infowindow.setContent(context.data);
		              } else {
		                $(this).gmap3({
		                  infowindow:{
		                    anchor:marker, 
		                    options:{content: context.data}
		                  }
		                });
		              }
		            },
		            mouseout: function(){
		              var infowindow = $(this).gmap3({get:{name:"infowindow"}});
		              if (infowindow){
		                infowindow.close();
		              }
		            }
		          }
		        }
		      });
	});
	</script>
	<!-- javaScript Map end -->
</body>
</html>
