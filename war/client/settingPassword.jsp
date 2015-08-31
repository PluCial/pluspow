<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
String entryId = (String) request.getAttribute("entryId");
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/include-parts/html_head.jsp" />
	</head>
	<body class="login-page">
		<div class="login-box">
			<jsp:include page="/client/include-parts/main_header.jsp" />
			
			<div class="login-box-body">
				<p class="login-box-msg">パスワードの再設定</p>
				<form action="/client/settingPasswordEntry" method="post">
					<div class="form-group ${f:errorClass('password','has-error')}">
						<%if (errors.containsKey("password")){ %>
						<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.password}</label>
						<%} %>
						<input type="password" ${f:text("password")} class="form-control" placeholder="パスワード" />
					</div>
					<div class="form-group ${f:errorClass('passwordConfirmation','has-error')}">
						<%if (errors.containsKey("passwordConfirmation")){ %>
						<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.passwordConfirmation}</label>
						<%} %>
						<input type="password" name="passwordConfirmation" class="form-control" placeholder="パスワード確認"/>
					</div>
					<div>
              			<button type="submit" class="btn btn-primary btn-block btn-flat">設定してログイン</button>
            		</div><!-- /.col -->
            		<input type="hidden" name="entryId" value="<%=entryId %>">
				</form>

			</div><!-- /.login-box-body -->
		</div><!-- /.login-box -->
		
		<jsp:include page="/client/include-parts/main_footer.jsp" />

		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
