<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
%>
	<section id="map-wrapper" class="no-padding">
		<div class="container">
			<div class="contact-info-inner">
    			<h3><%=spot.getName() %></h3>
	    		<div><i class="fa fa-map-marker pull-left"></i>  
	    			<p><%=spot.getAddress() %></p>
	    		</div>
	    		<div><i class="fa fa-phone pull-left"></i>  
	    			<p><%=spot.getPhoneNumberString() %></p>
	    		</div>

			</div>
	    </div>
		<div class="map" id="map"></div>
	</section>