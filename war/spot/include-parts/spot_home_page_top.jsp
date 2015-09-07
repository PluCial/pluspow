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
	<section id="spot-home" class="no-padding" style="background-image:
                  url(<%=spot.getBackgroundImageUrl() %>)">
        
		<div class="row">
			<div id="spot-catch" class="col-md-8 spot-detail-inner"><!-- spot-image -->
			
				<div class="detail">
					<h2>
						<span id="<%=spot.getCatchCopyResKey() %>"><%=spot.getCatchCopy() %></span>
						<%if(isOwner && isEditPage) { %>
						<a data-toggle="modal" 
							data-backdrop="static"
							data-target="#textResModal" 
							style="color:#fff"
							href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resRole=<%=TextResRole.SPOT_CATCH_COPY %>&resourcesKey=<%=spot.getCatchCopyResKey() %>">
							<i class="fa fa-pencil-square-o edit-mode"></i>
						</a>
						<%} %>
					</h2>
				</div>
				<div class="lang-box">
                        
					<!-- langs-select start -->
					<%if(suppertLangList != null && suppertLangList.size() > 0) { %>
						<select id="langs-select" class="" name="lang">
							<%for(SupportLang suppertLang: suppertLangList) {%>
								<option value="<%=suppertLang.toString() %>" <%=spot.getSpotLangInfo().getLang() == suppertLang ? "selected" : "" %>><%=suppertLang.getName() %></option>
							<%} %>
							<%if(isOwner && isEditPage) { %>
								<option value="add">&lt; 追加... &gt;</option>
							<%} %>
						</select>
					<%} %>
				</div>
				
				<div class="cd-slider-nav">
					<nav>
						<ul>
							<li class="selected"><a href="#0"><i class="fa fa-cutlery"></i> 飲食</a></li>
							<li class="selected"><a href="#0"><i class="fa fa-hotel"></i> 宿泊</a></li>
							<li class=""><a href="#0"><i class="fa fa-shopping-cart"></i> 買い物</a></li>
							<li class=""><a href="#0"><i class="fa fa-bicycle"></i> 娯楽</a></li>
							<li class=""><a href="#0"><i class="fa fa-heart-o"></i> 癒し</a></li>
							<li class="selected"><a href="#0"><i class="fa fa-ellipsis-h"></i> その他</a></li>
						</ul>
					</nav> 
				</div>

			</div><!-- spot-catch end-->
			
			<div class="col-md-4 no-padding">
				<div id="spot-nav-tabs" class="full-width-tabs">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#spot-info" data-toggle="tab"><i class="fa fa-map-marker"></i></a></li>
						<li><a href="#spot-detail" data-toggle="tab"><i class="fa fa-meh-o"></i></a></li>
						<li><a href="#spot-detail" data-toggle="tab"><i class="fa fa-calendar"></i></a></li>
					</ul>
				</div>
				<div class="tab-content">
					<div id="spot-info" class="spot-detail-inner tab-pane active">
				
						<div id="area-nav">
							<!-- area-nav start -->
							<ol class="breadcrumb">
								<%if(!StringUtil.isEmpty(spot.getGeoAreaLevel1())) { %>
								<li><a href=""><i class="fa fa-map-marker"></i> <%=spot.getGeoAreaLevel1() %></a></li>
								<%} %>
								<%if(!StringUtil.isEmpty(spot.getGeoAreaLevel2())) { %>
									<li><a href=""><i class="fa fa-map-marker"></i> <%=spot.getGeoAreaLevel2() %></a></li>
								<%} %>
								<%if(!StringUtil.isEmpty(spot.getGeoAreaLevel3())) { %>
								<li><a href=""><i class="fa fa-map-marker"></i> <%=spot.getGeoAreaLevel3() %></a></li>
								<%} %>
								<%if(!StringUtil.isEmpty(spot.getGeoLocality())) { %>
								<li><a href=""><i class="fa fa-map-marker"></i> <%=spot.getGeoLocality() %></a></li>
								<%} %>
								<%if(!StringUtil.isEmpty(spot.getGeoWardLocality())) { %>
								<li><a href=""><i class="fa fa-map-marker"></i> <%=spot.getGeoWardLocality() %></a></li>
								<%} %>
							</ol>
							<!-- area-nav end -->
						</div>
				
						<div class="detail">
							<div id="spot-logo"><i class="fa fa-map-marker"></i></div>
					
							<h1>
								<span id="<%=spot.getNameResKey() %>"><%=spot.getName() %></span>
								<%if(isOwner && isEditPage) { %>
								<!-- 修正モード -->
								<a data-toggle="modal" 
									data-backdrop="static"
									data-target="#textResModal" 
									style="color:#333"
									href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resRole=<%=TextResRole.SPOT_NAME %>&resourcesKey=<%=spot.getNameResKey() %>">
									<i class="fa fa-pencil-square-o edit-mode"></i>
								</a>
								<%} %>
							</h1>
					
							<p><%=spot.getGeoFormattedAddress() %></p>
							<p class="phone"><i class="fa fa-phone"></i> <%=spot.getPhoneNumber() %></p>
						</div>

						<div class="map" id="map"></div>
					</div>
			
					<div id="spot-detail" class="spot-detail-inner tab-pane">
<%-- 						<jsp:include page="/spot/include-parts/spot_office_hours.jsp" /> --%>
						<h1><%=spot.getName() %></h1>
						<p>
							<span id="<%=spot.getDetailResKey() %>"><%=Utils.getJspDisplayString(spot.getDetail()) %></span>
							<%if(isOwner && isEditPage) { %>
							<a data-toggle="modal" 
								data-backdrop="static"
								data-target="#textResModal" 
								class="" 
								style="color:#333"
								href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resRole=<%=TextResRole.SPOT_DETAIL %>&resourcesKey=<%=spot.getDetailResKey() %>">
								<i class="fa fa-pencil-square-o edit-mode"></i>
							</a>
							<%} %>
						</p>
					</div>
			
				</div>
			</div>
				
		</div>
    </section>