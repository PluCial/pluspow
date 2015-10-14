<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
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
%>
	<section id="spot-home" class="no-padding" style="background-image:
                  url(<%=PathUtils.getSpotBackgroundImageUrl(spot) %>)">
        
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
							href="/spot/secure/editTextResources?spotId=<%=spot.getSpotId() %>&resourcesKey=<%=spot.getCatchCopyResKey() %>">
							<i class="fa fa-pencil-square-o edit-mode"></i>
						</a>
						<%} %>
					</h2>
				</div>
				
				<a id="langs-select-btn"
					data-toggle="modal" 
					data-backdrop="static"
					data-target="#selectLangModel" 
					style="color:#fff"
					href="/spot/selectLang?spotId=<%=spot.getSpotId() %> %>">
					<img class="align-middle" style="width:32px;vertical-align:middle;" src="<%=PathUtils.getCountryFlagUrl(spot.getLangUnit().getLang()) %>"> 
					<span class="align-middle"><%=spot.getLangUnit().getLang().getName() %> <i class="fa fa-chevron-down"></i></span>
				</a>

			</div><!-- spot-catch end-->
			
			<div class="col-md-4 no-padding">
				<div id="spot-nav-tabs" class="full-width-tabs">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#spot-info" data-toggle="tab"><i class="fa fa-map-marker"></i></a></li>
						<li><a href="#spot-detail" data-toggle="tab"><i class="fa fa-meh-o"></i></a></li>
						<li><a href="#spot-office-hours" data-toggle="tab"><i class="fa fa-calendar"></i></a></li>
					</ul>
				</div>
				<div class="tab-content">
					<div id="spot-info" class="spot-detail-inner tab-pane active">
				
						<%-- <div id="area-nav">
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
						</div> --%>
				
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
								<span id="phone-number">+<%=spot.getPhoneCountry().getInterCallCode() %> <%=spot.isPhoneDisplayFlg() ? spot.getPhoneNumber() : ""%></span>
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
			
					<div id="spot-detail" class="spot-detail-inner tab-pane">
						<div>
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
					
					<div id="spot-office-hours" class="spot-detail-inner tab-pane">
						<%for(DayOfWeek dayOfWeek: DayOfWeek.values()) {
							OfficeHours officeHours = officeHoursMap.get(dayOfWeek.toString());
						%>
						<div id="<%=officeHours.getKey().getName() %>" class="plan text-center row">
							<div class="plan-name col-md-4">
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
							<div class="plan-price col-md-8 spot-office-hours-area" style="<%=officeHours.isOpen() ? "" : "display:none" %>">
								<sup class="currency">
									<i class="fa fa-sun-o"></i>
								</sup>
								<strong><span class="office-open-hour"><%=officeTimeformat.format(officeHours.getOpenHour()) %></span>:<span class="office-open-minute"><%=officeTimeformat.format(officeHours.getOpenMinute()) %></span></strong> 
								<sub><i class="fa fa-moon-o"></i><span class="office-close-hour"><%=officeTimeformat.format(officeHours.getCloseHour()) %></span>:<span class="office-close-minute"><%=officeTimeformat.format(officeHours.getCloseMinute()) %></span></sub>
							</div>
						</div>
						<%} %>
					</div>
			
				</div>
			</div>
				
		</div>
    </section>