<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
%>
<!DOCTYPE html>
<html>
	<head>
    	<jsp:include page="/client/account/include-parts/html_head.jsp" />
    	<style>
			.content {
				margin-top: 2em;
			}
			
			.table-bordered>tbody>tr>td {
				padding: 15px 8px;
			}
			
		</style>
	</head>
	<body class="login-page">
		<div class="wrapper">

			<!-- Full Width Column -->
			<div class="container">
				<div class="content">
					<div class="register-logo">
						<a href="/+<%=spot.getSpotId() %>"><b><%=spot.getName() %></b></a>
					</div>
					<h3 style="text-align: center;">翻訳言語の選択</h3>
					
					<div class="box">
						<div class="box-header with-border">
							<h3 class="box-title">64カ国語</h3>
						</div><!-- /.box-header -->
						<div class="box-body table-responsive no-padding">
							<table class="table table-bordered">
								<%for(int i=0; i < SupportLang.values().length; i++) {
									SupportLang lang = SupportLang.values()[i];
									boolean istranslated = spot.getSupportLangs().indexOf(lang) >= 0;
								%>
							
								<%if((i + 1)%5 == 1) { %>
								<tr>
								<%} %>
							
									<td style="<%=istranslated ? "" : "" %>">
										<span style="background-image: url(/images/flag/<%=lang.getLangKey().toUpperCase() %>.png);background-repeat:no-repeat;background-position: center left;background-size: 34px;padding-left:50px;" class="">
										<%if(istranslated) { %>
											<span><%=lang.getName() %></span>
										<%}else { %>
											<a href="/spot/secure/trans?spotId=<%=spot.getSpotId() %>&transGroup=<%=ResGroups.SPOT%>&transLang=<%=lang.toString() %>"><%=lang.getName() %></a>
										<%} %>
										</span>
										
										<%if(istranslated) { %>
										<i class="fa fa-check-circle pull-right" style="font-size: 20px;color: #00aacc;"></i>
										<%} %>
									</td>
								
								<%if((i + 1)%5 == 0) { %>
								</tr>
								<%} %>
								<%} %>
							</table>
						</div><!-- /.box-body -->
					</div>
				
					
				</div>
					
			</div><!-- /.content-wrapper -->
		</div><!-- ./wrapper -->
		
		<jsp:include page="/client/include-parts/main_footer.jsp" />
		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
