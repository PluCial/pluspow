<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@page import="com.nipponplus.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="org.slim3.controller.validator.Errors" %>

<%
String status = (String)request.getAttribute("status");
Errors errors = (Errors)request.getAttribute("errors");
String content = (String)request.getAttribute("content");
%>
{
	"status" : "<%=status %>"
	<%if(!status.equals("OK") && errors != null && !errors.isEmpty()) { %>
	,"errorMessage" : "<%=errors.get(0) %>"
	<%} %>
}
