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
<%@ page import="com.pluspow.utils.*" %>
<%
List<Spot> spotList =(List<Spot>) request.getAttribute("spotList");
String cursor = null;
boolean hasNext = false;
if (request.getAttribute("cursor") != null && request.getAttribute("hasNext") != null) {
	cursor = (String) request.getAttribute("cursor");
	hasNext = Boolean.valueOf((String) request.getAttribute("hasNext"));
}
Lang localeLang =(Lang) request.getAttribute("localeLang");
boolean isLocal = Boolean.valueOf((String) request.getAttribute("isLocal"));
boolean isClientLogged = Boolean.valueOf((String) request.getAttribute("isClientLogged"));
String[] selectedActivitys =(String[]) request.getAttribute("selectedActivitys");
%>
<fmt:setLocale value="<%=localeLang.toString() %>" />

					<%for(Spot spot: spotList) { %>
					<div class="col-sm-3 col-xs-12">
						<div class="spot-box" style="background-image: url(<%=PathUtils.getSpotBackgroundImageUrl(spot, 700) %>)">
							<div class="contents text-center">
								<strong><%=spot.getName() %></strong>
								<span><%=spot.getCatchCopy() %></span>
							</div>
							<a class="text-center" href="<%=PathUtils.spotPage(spot.getSpotId(), localeLang, isLocal, isClientLogged) %>">
								<%for(SpotActivity activity: spot.getActivitys()) { %>
								<span><i class="<%=activity.getIconClass() %>"></i></span>
								<%} %>
							</a>
						</div>
					</div>
					<%} %>
				
	 				<%if(hasNext) { %>
					<div class="col-md-12 col-xs-12 text-center listHasNext">
						<form class="nextResultsForm" action="/search" method="get">
							<input type="hidden" ${f:hidden("keyword")}/>
							<input type="hidden" ${f:hidden("country")}/>
							<input type="hidden" ${f:hidden("areaLevel1")}/>
							<input type="hidden" ${f:hidden("areaLevel2")}/>
							<input type="hidden" ${f:hidden("areaLevel3")}/>
							<input type="hidden" ${f:hidden("locality")}/>
							<input type="hidden" ${f:hidden("sublocality")}/>
							<input type="hidden" ${f:hidden("wardLocality")}/>
							<%for(String activity: selectedActivitys) { %>
							<input type="hidden" name="activityArray" value="<%=activity %>" />
							<%} %>
							<input type="hidden" name="cursor" value="<%=cursor %>" />
							<input type="hidden" name="lang" value="<%=localeLang.toString() %>"/>
							<button class="btn btn-default nextResults">もっと見る</button>
						</form>
						
					</div>
	 				<%} %>