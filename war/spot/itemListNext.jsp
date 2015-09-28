<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.model.*" %>
<%
Spot spot = (Spot) request.getAttribute("spot");

String cursor = null;
boolean hasNext = false;
if (request.getAttribute("cursor") != null && request.getAttribute("hasNext") != null) {
	cursor = (String) request.getAttribute("cursor");
	hasNext = Boolean.valueOf((String) request.getAttribute("hasNext"));
}
%>
					<jsp:include page="/spot/include-parts/spot_item_list.jsp">
						<jsp:param name="isEditPage" value="true" />
					</jsp:include>
					
 					<%if(hasNext) { %>
					<div class="col-md-12 col-xs-12 text-center listHasNext">
						<a class="btn btn-default nextLink" href="/spot/itemListNext?spotId=<%=spot.getSpotId() %>&lang=<%=spot.getLangUnit().getLang().toString() %>&cursor=<%=cursor %>">もっと見る</a>
					</div>
					<%} %>
