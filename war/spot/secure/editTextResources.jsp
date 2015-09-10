<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="com.pluspow.model.TextResources" %>
<%
Spot spot = (Spot) request.getAttribute("spot");
String resourcesKey = (String) request.getAttribute("resourcesKey");
TextResources textResources = (TextResources) request.getAttribute("textResources");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<style>
		.modal-body input, .modal-body textarea {
			color: #555;font-size: 16px;
		}
	</style>
</head>
<body>
	<div class="modal-header">
		<div class="pull-right">
			<button class="btn btn-box-tool btn-sm" data-dismiss="modal"><i class="fa fa-times"></i></button>
		</div>
		<h4 class="modal-title"><i class="fa fa-pencil-square-o"></i> <%=textResources != null ? textResources.getRole().getName() : "" %></h4>
	</div><!-- /modal-header -->
    <form id="resources-form">
		<div class="modal-body">
			
			<%if(textResources != null) { %>
			<%if(!textResources.getRole().isLongText()) { %>
			<input type="text" name="content" class="form-control" style="height: 40px;" value="<%=textResources.getContent().getValue() %>" />
			<%}else { %>
			<textarea name="content" class="form-control" rows="10"><%=textResources.getContent().getValue() %></textarea>
			<%} %>
			<%} %>
		</div><!-- /modal-body -->
		<div class="modal-footer">
			<button id="text-resources-submit-button" type="button" class="btn btn-primary btn-sm">変更</button>
		</div>	<!-- /modal-footer -->
		<input type="hidden" name="spotId" value="<%=spot.getSpotId() %>" />
		<input type="hidden" name="resourcesKey" value="<%=resourcesKey %>" />
		<input type="hidden" name="resRole" value="<%=textResources.getRole() %>" />
	</form>
</body>
</html>