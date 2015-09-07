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
<%
Spot spot = (Spot) request.getAttribute("spot");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
List<Item> itemList =(List<Item>) request.getAttribute("itemList");
%>
<!DOCTYPE html>
<html lang="<%=spot.getSpotLangInfo().getLang() %>">
<head>
	<jsp:include page="/spot/include-parts/html_head.jsp" />
</head>
	
<body class="home">

	<!-- Style switcher start -->
<%-- 	<jsp:include page="/spot/include-parts/style_switch.jsp" /> --%>
	<!-- Style switcher end -->

	<div class="body-inner">
		<!-- Header start -->
		<jsp:include page="/spot/include-parts/main_header.jsp" />
		<!-- Header end -->
		
		<%if(isOwner) { %>
		<a id="background-image-btn" 
			class="btn btn-default" 
			href="/spot/secure/changeGcsRes?spotId=<%=spot.getSpotId() %>&resGroups=<%=ResGroups.SPOT %>&target=<%=SpotGcsResRole.SPOT_BACKGROUND_IMAGE.toString() %>&resourcesKey=<%=spot.getBackgroundImageResKey() == null ? "" : spot.getBackgroundImageResKey() %>">
			<i class="fa fa-file-image-o"></i> 背景画像</a>
		<%} %>

		<!-- Slider start -->
		<jsp:include page="/spot/include-parts/spot_home_page_top.jsp">
			<jsp:param name="isEditPage" value="true" />
		</jsp:include>
    	<!--/ Slider end -->

		<!-- item-list start -->
		<%if(isOwner || itemList.size() > 0) { %>
		<section id="service" class="portfolio portfolio-box">
			<div class="container">
				<div class="row">
					<div class="col-md-12 heading">
						<span class="title-icon classic pull-left"><i class="fa fa-suitcase"></i></span>
						<h2 class="title classic">Service</h2>
						<%if(isOwner && spot.getSpotLangInfo().getLang() == spot.getBaseLang()) { %>
						<a class="pull-right btn btn-primary" href="/spot/secure/itemAdd?spotId=<%=spot.getSpotId() %>">
							<i class="fa fa-plus"></i> アイテムを追加
						</a>
						<%} %>
					</div>
				</div> <!-- Title row end -->
			</div>
			<jsp:include page="/spot/include-parts/spot_item_list.jsp">
				<jsp:param name="isEditPage" value="true" />
			</jsp:include>
		</section>
		<%} %>
		<!-- item-list end -->
	

		<!-- Footer start -->
		<jsp:include page="/spot/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/spot/include-parts/html_script.jsp" />
	<!-- javaScript end -->

	<%if(isOwner) { %>
	<!-- secure JS start -->
	<jsp:include page="/spot/include-parts/dialog_modal.jsp">
		<jsp:param name="modelId" value="textResModal" />
	</jsp:include>
	<script type="text/javascript" src="/plugins/waiting-dialog/waiting-dialog.js"></script>
	<!-- secure JS end -->
	<%} %>
	
	
	<!-- javaScript Map start -->
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?language=<%=spot.getSpotLangInfo().getLang() %>"></script>
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