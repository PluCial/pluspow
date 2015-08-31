<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%
	EntryType entry =(EntryType) request.getAttribute("entry");
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/include-parts/html_head.jsp" />
	</head>
	<body class="login-page">
		<div class="login-box">
			<div class="login-logo">
				<a href="/"><b><%=App.APP_DISPLAY_NAME %></b></a>
			</div><!-- /.login-logo -->
			
			<div class="callout callout-info">
               <h4><%=entry.getTitle() %></h4>
               <p><%=entry.getMessege() %></p>
            </div>
		</div><!-- /.login-box -->
		
		
		
		<jsp:include page="/client/include-parts/main_footer.jsp" />

		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
