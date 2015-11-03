<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.utils.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Properties" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
Lang lang =(Lang) request.getAttribute("lang");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
boolean isEditPage = Boolean.valueOf((String) request.getParameter("isEditPage"));
List<Lang> suppertLangList = (List<Lang>) request.getAttribute("suppertLangList");
Map<String, OfficeHours> officeHoursMap = (Map<String, OfficeHours>) request.getAttribute("officeHoursMap");
DecimalFormat officeTimeformat = new DecimalFormat("00");
Properties appProp = (Properties) request.getAttribute("appProp");
Lang localeLang =(Lang) request.getAttribute("localeLang");
%>
<fmt:setLocale value="<%=localeLang.toString() %>" />
	<section id="spot-home" class="no-padding">
        <div class="contents css-table">
        
			<div class="left-content text-center" style="background-image:
                  url(<%=PathUtils.getSpotBackgroundImageUrl(spot) %>)">
			

				<h2>
					<span id="<%=spot.getCatchCopyResKey() %>"><%=spot.getCatchCopy() %></span>
					<%if(isOwner && isEditPage) { %>
					<a data-toggle="modal" 
						data-backdrop="static"
						data-target="#textResModal" 
						style="color:#fff"
						href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resourcesKey=<%=spot.getCatchCopyResKey() %>">
						<i class="fa fa-pencil-square-o edit-mode"></i>
					</a>
					<%} %>
				</h2>

				
				<%if(isOwner) { %>
				<a id="background-image-btn" 
					class="btn btn-default" 
					href="/spot/secure/changeGcsRes?spotId=<%=spot.getSpotId() %>&lang=<%=spot.getLangUnit().getLang() %>&role=<%=GcsResRole.SPOT_BACKGROUND_IMAGE %>&resourcesKey=<%=spot.getBackgroundImageResKey() == null ? "" : spot.getBackgroundImageResKey() %>">
					<i class="fa fa-file-image-o"></i> 背景画像</a>
				<%} %>
				
				<a id="langs-select-btn"
					data-toggle="modal" 
					data-backdrop="static"
					data-target="#selectLangModel" 
					style="color:#fff"
					href="/spot/selectLang?spotId=<%=spot.getSpotId() %>&lang=<%=lang.toString() %>">
					<img class="align-middle" style="width:32px;vertical-align:middle;" src="<%=PathUtils.getCountryFlagUrl(spot.getLangUnit().getLang()) %>"> 
					<span class="align-middle">
						<%=appProp.getProperty("lang." + spot.getLangUnit().getLang().toString()) %> <i class="fa fa-chevron-down"></i>
					</span>
				</a>

			</div><!-- spot-catch end-->
			
			<div class="right-content no-padding">
				<div id="spot-nav-tabs" class="full-width-tabs">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#spot-info" data-toggle="tab"><i class="fa fa-map-marker"></i></a></li>
						<li><a href="#spot-detail" data-toggle="tab"><i class="fa fa-meh-o"></i></a></li>
						<li><a href="#spot-office-hours" data-toggle="tab"><i class="fa fa-calendar"></i></a></li>
					</ul>
				</div>
				<div class="tab-content">
					<div id="spot-info" class="tab-pane active">
					
						<div class="contents">
							<div id="area-nav">
								<!-- area-nav start -->
								<ol class="breadcrumb">
									<%if(!StringUtil.isEmpty(spot.getGeoAreaLevel1())) { %>
									<li><a href="/<%=spot.getCountry().toString() %>/search?areaLevel1=<%=spot.getGeoAreaLevel1() %>"><%=spot.getGeoAreaLevel1() %></a></li>
									<%} %>
									<%if(!StringUtil.isEmpty(spot.getGeoAreaLevel2())) { %>
										<li><a href="/<%=spot.getCountry().toString() %>/search?areaLevel2=<%=spot.getGeoAreaLevel2() %>"><%=spot.getGeoAreaLevel2() %></a></li>
									<%} %>
									<%if(!StringUtil.isEmpty(spot.getGeoAreaLevel3())) { %>
									<li><a href="/<%=spot.getCountry().toString() %>/search?areaLevel3=<%=spot.getGeoAreaLevel3() %>"><%=spot.getGeoAreaLevel3() %></a></li>
									<%} %>
									<%if(!StringUtil.isEmpty(spot.getGeoLocality())) { %>
									<li><a href="/<%=spot.getCountry().toString() %>/search?locality=<%=spot.getGeoLocality() %>"><%=spot.getGeoLocality() %></a></li>
									<%} %>
									<%if(!StringUtil.isEmpty(spot.getGeoWardLocality())) { %>
									<li><a href="/<%=spot.getCountry().toString() %>/search?wardLocality=<%=spot.getGeoWardLocality() %>"><%=spot.getGeoWardLocality() %></a></li>
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
										href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resourcesKey=<%=spot.getNameResKey() %>">
										<i class="fa fa-pencil-square-o edit-mode"></i>
									</a>
									<%} %>
								</h1>
						
								<p>
									<span id="address"><%=spot.getDisplayAddress() %> <%=spot.getFloor() %>F</span>
								</p>
								<p class="phone">
									<i class="fa fa-phone" style="<%=!isOwner && !spot.isPhoneDisplayFlg() ? "display: none;" : ""%>"></i> 
									<span id="phone-number"><%=spot.isPhoneDisplayFlg() ? "+" + spot.getPhoneCountry().getInterCallCode() + " " +spot.getPhoneNumber() : ""%></span>
									<%if(isOwner && isEditPage) { %>
									<!-- 修正モード -->
									<a data-toggle="modal" 
										data-backdrop="static"
										data-target="#phoneNumberModal" 
										style="color:#333"
										href="/spot/secure/editPhoneNumber?spotId=<%=spot.getSpotId() %>&lang=<%=spot.getLangUnit().getLang().toString() %>">
										<i class="fa fa-pencil-square-o edit-mode"></i>
									</a>
									<%} %>
								</p>
							</div>

							<div class="map" id="map"></div>
						</div>
					</div>
			
					<div id="spot-detail" class="tab-pane">
						<div class="contents">
							<h1><%=spot.getName() %></h1>
							<p>
								<span id="<%=spot.getDetailResKey() %>"><%=Utils.getJspDisplayString(spot.getDetail()) %></span>
								<%if(isOwner && isEditPage) { %>
								<a data-toggle="modal" 
									data-backdrop="static"
									data-target="#textResModal" 
									class="" 
									style="color:#333"
									href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resourcesKey=<%=spot.getDetailResKey() %>">
									<i class="fa fa-pencil-square-o edit-mode"></i>
								</a>
								<%} %>
							</p>
						</div>
					</div>
					
					<div id="spot-office-hours" class="tab-pane">
						<%for(DayOfWeek dayOfWeek: DayOfWeek.values()) {
							OfficeHours officeHours = officeHoursMap.get(dayOfWeek.toString());
							if(isOwner || officeHours.isOpen()) {
						%>
						<div id="<%=officeHours.getKey().getName() %>" class="plan css-table text-center">
							<div class="plan-name">
								<%=appProp.getProperty("dayOfWeek." + officeHours.getDayOfWeek().toString()) %>
								<%if(isOwner && isEditPage) { %>
								<a data-toggle="modal" 
									data-backdrop="static"
									data-target="#editOfficeHoursModal" 
									style="color:#333"
									href="/spot/secure/editOfficeHours?spotId=<%=spot.getSpotId() %>&dayOfWeek=<%=officeHours.getDayOfWeek() %>">
									<i class="fa fa-pencil-square-o edit-mode"></i>
								</a>
								<%} %>
							</div>
							<div class="plan-price spot-office-hours-area" style="<%=officeHours.isOpen() ? "" : "display:none" %>">
								<sup class="currency">
									<i class="fa fa-sun-o"></i>
								</sup>
								<strong><span class="office-open-hour"><%=officeTimeformat.format(officeHours.getOpenHour()) %></span>:<span class="office-open-minute"><%=officeTimeformat.format(officeHours.getOpenMinute()) %></span></strong> 
								<sub><i class="fa fa-moon-o"></i><span class="office-close-hour"><%=officeTimeformat.format(officeHours.getCloseHour()) %></span>:<span class="office-close-minute"><%=officeTimeformat.format(officeHours.getCloseMinute()) %></span></sub>
							</div>
							
						</div>
							<%} %>
						<%} %>
					</div>
			
				</div>
			</div>
				
		</div>
<!-- 		
		</div> -->
    </section>