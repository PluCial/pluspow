<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="com.pluspow.model.TextRes" %>
<%@ page import="java.text.DecimalFormat" %>
<%
Spot spot = (Spot) request.getAttribute("spot");
OfficeHours officeHours = (OfficeHours)request.getAttribute("officeHours");
DecimalFormat officeTimeformat = new DecimalFormat("00");
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
		<h4 class="modal-title"><i class="fa fa-calendar"></i> <%=officeHours != null ? officeHours.getDayOfWeek().getName() : "" %></h4>
	</div><!-- /modal-header -->
    <form id="office-hours-submit-form">
		<div class="modal-body">
			<div class="form-group">
				<div class="radio">
					<label>
						<input type="radio" name="isOpenFlg" value="true" <%=officeHours.isOpen() ? "checked" : "" %> />
                          営業日
				</label>
				</div>
				<div class="radio">
					<label>
						<input type="radio" name="isOpenFlg" value="false" <%=officeHours.isOpen() ? "" : "checked" %> />
						非営業日
					</label>
				</div>
			</div>
			
			<%if(officeHours != null) { %>
			<div id="time-area" class="row" style="<%=officeHours.isOpen() ? "" : "    display: none;" %>">
				<div class="col-xs-6">
					<div class="input-group bootstrap-timepicker timepicker">
						<div class="input-group-addon">
							<i class="fa fa-sun-o"></i>
						</div>
						<input id="openTime" data-open-time="<%=officeTimeformat.format(officeHours.getOpenHour()) + ":" + officeTimeformat.format(officeHours.getOpenMinute()) %>" type="text" name="openTime" class="form-control" />
					</div>
				</div>
				<div class="col-xs-6">
 					<div class="input-group bootstrap-timepicker timepicker">
						<div class="input-group-addon">
							<i class="fa fa-moon-o"></i>
						</div>
						<input id="closeTime" data-close-time="<%=officeTimeformat.format(officeHours.getCloseHour()) + ":" + officeTimeformat.format(officeHours.getCloseMinute()) %>" type="text" name="closeTime" class="form-control timepicker" />
					</div>
				</div>
			</div>
			<%} %>
		</div><!-- /modal-body -->
		<div class="modal-footer">
			<button id="office-hours-submit-button" type="button" class="btn btn-primary btn-sm">変更</button>
		</div>	<!-- /modal-footer -->
		<input type="hidden" name="spotId" value="<%=spot.getSpotId() %>" />
		<input type="hidden" name="dayOfWeek" value="<%=officeHours.getDayOfWeek().toString() %>" />
		<input type="hidden" name="targetBoxId" value="<%=spot.getKey().getId() + "_" +  officeHours.getDayOfWeek().toString() %>" />
	</form>
</body>
</html>