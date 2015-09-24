<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/include-parts/html_head.jsp" />
	</head>
	<body class="lockscreen">
		<div class="lockscreen-wrapper">
			<jsp:include page="/client/include-parts/main_header.jsp" />
			

      		<div class="lockscreen-name">パスワードリセット</div>
      
			<!-- START LOCK SCREEN ITEM -->
      <div class="lockscreen-item">
        

        <!-- lockscreen credentials (contains the form) -->
        <form action="/client/resetPasswordEntry" method="post">
          <div class="input-group">
            <input type="email" ${f:text("email")} class="form-control" placeholder="メールアドレス" />
            
            <div class="input-group-btn">
              <button class="btn" type="submit"><i class="fa fa-arrow-right text-muted"></i></button>
            </div>
          </div>
        </form><!-- /.lockscreen credentials -->

      </div><!-- /.lockscreen-item -->
      <%if (errors.containsKey("email")){ %>
      	<div class="callout callout-danger">
			<h4><i class="icon fa fa-warning"></i> ${errors.email}</h4>
		</div>
	<%} %>
      <div class="help-block text-center">
        <p>パスワードリセットメールをお送りします。<br />メールの指示に従ってパスワードのリセットを行ってください。</p>
      </div>
      <div class='text-center'>
        <a href="/client/login">ログインページに戻る</a>
      </div>
			
		</div><!-- /.login-box -->
		
		<jsp:include page="/client/include-parts/main_footer.jsp" />

		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
