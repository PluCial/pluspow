<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="com.pluspow.utils.*" %>
<%
String spotId = (String) request.getAttribute("spotId");
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/include-parts/html_head.jsp" />
	</head>
	<body class="login-page">
		<div class="login-box">
			<div class="login-logo">
				<a href="/"><b>スポットの削除</b></a>
			</div>
			
			<div class="callout callout-info">
				<h4>本当にこのスポットを削除してよろしいですか？</h4>
				<p>今まで翻訳したコンテンツも含めてすべて削除されます。一度削除したスポットは元に戻すことはできません。</p>
			</div>
			<form action="/client/account/spotDeleteEntry" method="post">
				<input type="hidden" name="spotId" value="<%=spotId %>" />
			
				<div class="text-right">
					<a href="<%=PathUtils.selectSpotPage() %>" class="btn btn-default">キャンセル</a>
					<button type="submit" class="btn btn-danger">削除する</button>
				</div>
			</form>
		</div>
		<jsp:include page="/client/include-parts/main_footer.jsp" />
		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
