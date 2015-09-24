<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pluspow.App" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
String message =(String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/include-parts/html_head.jsp" />
</head>
	
<body class="info">

	<div class="body-inner">
		<!-- Header start -->
 		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->
    	
    	<!-- Main container start -->
		<section id="main-container">
			<article class="container">
				<header>
					<div>
						<span class="title-icon classic pull-left"><i class="fa fa-envelope-o"></i></span>
						<h2 class="title classic">お問い合わせ</h2>
						| <%=App.APP_DISPLAY_NAME %>
					</div>	
				</header>
				
				<div class="row">
					<div class="col-md-12 col-sm-12">
						<div class="">
							<form action="/info/contactEntry" method="post">
								<div class="row">
									<div class="col-md-6">
								<div class="form-group has-feedback ${f:errorClass('name','has-error')}">
									<%if (errors.containsKey("name")){ %>
									<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.name}</label>
									<%} %>
									<input type="text" class="form-control" ${f:text("name")} placeholder="お名前(会社名、組織名、担当者名)" required />
									<span class="glyphicon glyphicon-user form-control-feedback"></span>
								</div>
								<div class="form-group has-feedback ${f:errorClass('email','has-error')}">
									<%if (errors.containsKey("email")){ %>
									<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.email}</label>
									<%} %>
									<input type="email" ${f:text("email")} class="form-control" placeholder="返信用メールアドレス" required />
									<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
								</div>
								<div class="form-group has-feedback ${f:errorClass('password','has-error')}">
									<%if (errors.containsKey("phoneNumber")){ %>
									<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.phoneNumber}</label>
									<%} %>
									<input type="text" ${f:text("phoneNumber")} class="form-control" placeholder="092-1111-1111" required />
									<span class="glyphicon glyphicon-earphone form-control-feedback"></span>
								</div>
								
								</div>
								<div class="col-md-6">
									<div class="form-group ${f:errorClass('subject','has-error')}">
										<%if (errors.containsKey("subject")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.subject}</label>
										<%} %>
										<input class="form-control" ${f:text("subject")} placeholder="件名" required>
									</div>
											
									<div class="form-group ${f:errorClass('message','has-error')}">
										<%if (errors.containsKey("message")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.message}</label>
										<%} %>
										<textarea class="form-control" name="message" id="message" placeholder="本文" rows="10" required><%=message == null ? "" : message %></textarea>
									</div>
								</div>
								</div>
								
								<div class="modal-footer">
									<button type="submit" class="btn btn-primary">送信する</button>
								</div>
								
							</form>
							
						</div>
					</div><!-- End col-md-6 -->
				</div><!-- Content row  end -->

			</article><!--/ container end -->
	
		</section><!--/ Main container end -->
	

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	
</body>
</html>
