<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pluspow.App" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/include-parts/html_head.jsp" />
	</head>
	<body class="register-page">
		<div class="register-box">
			<jsp:include page="/client/include-parts/main_header.jsp" />

			<div class="register-box-body">
				<p class="login-box-msg">アカウント登録</p>
				
				<form action="/client/registerEntry" method="post">
					<div class="form-group ${f:errorClass('name','has-error')}">
						<%if (errors.containsKey("name")){ %>
						<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.name}</label>
						<%} %>
						<input type="text" class="form-control" ${f:text("name")} placeholder="ユーザー名(会社名、組織名、担当者名)"/>
						
					</div>
					<div class="form-group ${f:errorClass('email','has-error')}">
						<%if (errors.containsKey("email")){ %>
						<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.email}</label>
						<%} %>
						<input type="email" ${f:text("email")} class="form-control" placeholder="メールアドレス" />
					</div>
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
					<div class="checkbox icheck">
						<label>
							<input type="checkbox" ${f:checkbox("agreeTerms")}> <a target="_blank" href="/info/agreement">利用規約</a>及び<a target="_blank" href="/info/privacy">利用規約</a>に同意します。 
						</label>
					</div>
					<%if (errors.containsKey("agreeTerms")){ %>
						<div class="callout callout-danger">
							<h4><i class="icon fa fa-warning"></i></h4>
							<p>${errors.agreeTerms}</p>
						</div>
					<%} %>
					<div>
						<button type="submit" class="btn btn-primary btn-block btn-flat">次へ</button>
					</div>
				</form>

				<div class="social-auth-links text-center">
					<p>- もしくは -</p>
					<a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Facebook アカウントで始める</a>
					<a href="#" class="btn btn-block btn-social btn-google-plus btn-flat"><i class="fa fa-google-plus"></i> Google+ アカウントで始める</a>
				</div>

				<a href="/client/login" class="text-center">既にアカウントをお持ちの方はこちら</a>
			</div><!-- /.form-box -->
			
			
			
		</div><!-- /.register-box -->
		
		
		
		<jsp:include page="/client/include-parts/main_footer.jsp" />
      	
		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
