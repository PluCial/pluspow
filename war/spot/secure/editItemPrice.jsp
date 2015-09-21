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
Item item = (Item) request.getAttribute("item");
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
		<h4 class="modal-title"><i class="fa fa-jpy"></i> 金額の修正</h4>
	</div><!-- /modal-header -->
    <form id="item-price-submit-form">
		<div class="modal-body">
			<div class="form-group">
				<label id="errorMsg" class="control-label" for="inputError" style="display:none"><i class="fa fa-times-circle-o"></i> 入力した金額が正しくありません。</label>
				<div class="input-group">
					<span class="input-group-addon">¥</span>
					<input type="text" id="price-input" name="price" class="form-control" value="<%=(int)item.getPrice() %>" style="height:40px;text-align: right;" placeholder="税抜き金額" />
					<span class="input-group-addon">.00</span>
				</div>
			</div>
		</div><!-- /modal-body -->

		<div class="modal-footer">
			<button id="item-price-submit-button" type="button" class="btn btn-primary btn-sm">変更</button>
		</div>	<!-- /modal-footer -->
		<input type="hidden" name="spotId" value="<%=spot.getSpotId() %>" />
		<input type="hidden" name="itemId" value="<%=item.getKey().getName() %>" />
	</form>
</body>
</html>