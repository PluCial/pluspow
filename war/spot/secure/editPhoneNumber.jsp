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
		<h4 class="modal-title"><i class="fa fa-phone"></i> 電話番号の設定</h4>
	</div><!-- /modal-header -->
    <form id="phone-number-submit-form">
		<div class="modal-body">
			<div class="form-group">
				<div class="radio">
					<label>
						<input type="radio" name="isDisplayFlg" value="true" <%=spot.getLangUnit().isPhoneDisplayFlg() ? "checked" : "" %> />
                          表示
				</label>
				</div>
				<div class="radio">
					<label>
						<input type="radio" name="isDisplayFlg" value="false" <%=spot.getLangUnit().isPhoneDisplayFlg() ? "" : "checked" %> />
						非表示
					</label>
				</div>
			</div>
			
			<div id="phone-number-area" style="<%=spot.getLangUnit().isPhoneDisplayFlg() ? "" : "display: none;" %>">
				<div class="form-group">
					<label id="errorMsg" class="control-label" for="inputError" style="display:none"><i class="fa fa-times-circle-o"></i> 入力した電話番号が正しくありません。</label>
					<div class="input-group">
						<span class="input-group-addon"><i class="fa fa-phone"></i></span>
						<input id="phone-number-input" value="<%=spot.getLangUnit().getPhoneNumber().getNumber() %>" type="text" name="phoneNumber" class="form-control" placeholder="092-1111-1111" />
					</div>
				</div>
			</div>
			
		</div><!-- /modal-body -->
		<div class="modal-footer">
			<button id="phone-number-submit-button" type="button" class="btn btn-primary btn-sm">変更</button>
		</div>	<!-- /modal-footer -->
		<input type="hidden" name="spotId" value="<%=spot.getSpotId() %>" />
		<input type="hidden" name="lang" value="<%=spot.getLangUnit().getLang().toString() %>" />
	</form>
</body>
</html>