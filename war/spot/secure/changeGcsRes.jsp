<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pluspow.utils.*" %>
<%
Client client =(Client) request.getAttribute("client");
Spot spot =(Spot) request.getAttribute("spot");
String role = (String) request.getAttribute("role");
String itemId = (String) request.getAttribute("itemId");
String resourcesKey = (String) request.getAttribute("resourcesKey");
Errors errors =(Errors) request.getAttribute("errors");
boolean isLocal = Boolean.valueOf((String) request.getAttribute("isLocal"));
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/account/include-parts/html_head.jsp" />
	<link href="/plugins/cropper/cropper.min.css" rel="stylesheet">
	<style>
	.content {
    	margin-top: 2em;
	}
	.form-group,  .portfolio-slider{
		margin-bottom: 25px;
	}
	.has-error label.control-label {
		color:#ee3b24;
	}
	input.form-control, textarea.form-control {
		color: #000;
	}
	.icheckbox_square-blue {
    background-position: 0 0;
	}
	.icheckbox_square-blue, .iradio_square-blue {
    display: inline-block;
    vertical-align: middle;
    margin: 0;
    padding: 0;
    width: 22px;
    height: 22px;
    background: url(/plugins/iCheck/square/blue.png) no-repeat;
    border: none;
    cursor: pointer;
}

.icheckbox_square-blue.checked {
    background-position: -48px 0;
}

.img-container {
    height: 400px;
    width: 100%;
    max-width: 750px;
    margin-bottom: 15px;
    box-shadow: inset 0 0 5px rgba(0,0,0,.25);
    background-color: #fcfcfc;
    overflow: hidden;
}

.avatar-upload label {
    display: block;
    float: left;
    clear: left;
    width: 100px;
}

.avatar-upload input {
    display: block;
    margin-left: 110px;
}
	</style>
	</head>
	
	<body class="login-page">
		<div class="wrapper">

			<!-- Full Width Column -->
			<div class="container">
			
				<div class="content">
					<div class="register-logo">
						<a href="<%=PathUtils.spotPage(spot.getSpotId(), spot.getLangUnit().getLang(), isLocal, true) %>"><b><%=spot.getName() %></b></a>
					</div>
					<h3 style="text-align: center;">画像を選択してください。</h3>
					
					<div class="row">
						<div class="col-md-8 col-md-offset-2">
							<form action="/spot/secure/changeGcsResEntry" method="post" enctype="multipart/form-data">
								<div class="box">
									<div class="box-body">
										<%if (errors.containsKey("uploadImage")){ %>
										<div class="${f:errorClass('uploadImage','has-error')}">
											<label class="control-label has-error" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.uploadImage}</label>
										</div>
										<%} %>
						
										<div class="avatar-wrapper"></div>
										<div class="img-container">
											<img src="">
										</div>
										<div class="avatar-upload">
											<label for="avatarInput">Local upload</label>
											<input class="avatar-input" id="inputImage" name="uploadImage" type="file" accept="image/*" />
										</div>

									</div><!-- ./box-body -->
									<div class="box-footer">
										<a class="pull-left btn btn-default" href="<%=PathUtils.spotPage(spot.getSpotId(), spot.getLangUnit().getLang(), isLocal, true) %>">キャンセル</a>
										<button type="submit" class="pull-right btn btn-primary"><i class="fa fa-file-image-o"></i> 変更する</button>
									</div><!-- /.box-footer -->
								
								</div><!-- /.box -->
								<input type="hidden" name="spotId" value="<%=spot.getSpotId() %>">
								<input type="hidden" name="lang" value="<%=spot.getLangUnit().getLang() %>">
								<input type="hidden" name="resourcesKey" value="<%=resourcesKey %>">
								<input type="hidden" name="role" value="<%=role == null ? "": role %>">
								<input type="hidden" name="itemId" value="<%=itemId == null ? "": itemId %>">
								<input type="hidden" name="imageX" id="imageX" value="" />
								<input type="hidden" name="imageY" id="imageY" value="" />
								<input type="hidden" name="imageHeight" id="imageHeight" value="" />
								<input type="hidden" name="imageWidth" id="imageWidth" value="" />
							</form>
						</div>
					</div>
				</div>
					
			</div><!-- /.content-wrapper -->

		</div><!-- ./wrapper -->

		<jsp:include page="/client/include-parts/main_footer.jsp" />
		<jsp:include page="/client/include-parts/html_script.jsp" />
		
		<script src="/plugins/cropper/cropper.min.js" type="text/javascript"></script>
    	<script src="/spot/js/item-add.js" type="text/javascript"></script>
	</body>
</html>
