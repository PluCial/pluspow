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
		<jsp:include page="/client/include-parts/html_head.jsp" />
	</head>
	<body class="register-page">
		<div class="register-box">
			<jsp:include page="/client/include-parts/main_header.jsp" />

			<div class="alert alert-info alert-dismissable">
                    	<h4>お問い合わせいただき誠にありがとうございます。</h4>
                    	後日、担当者が内容を確認し、回答を頂いたメールアドレス宛に送らせて頂きます。
					</div>
			
			
		</div><!-- /.register-box -->
		
		
		
		<jsp:include page="/client/include-parts/main_footer.jsp" />
      	
		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
