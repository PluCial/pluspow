<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.utils.*" %>
<%
List<Spot> spotList =(List<Spot>) request.getAttribute("spotList");
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/account/include-parts/html_head.jsp" />
		<style>
			.content {
				margin: 8% auto;
			}

			.add-spot {
				text-align: center;
			}

			.spot-modal .modal {
				position: relative;
				top: auto;
				bottom: auto;
				right: auto;
				left: auto;
				display: block;
				z-index: 1;
				width: 100%;
			}

			.spot-modal .modal {
				background: transparent !important;
			}
			
			.modal-title i.fa {
				color: #dd4b39;
			}
			
			.modal-body p {
				font-size: 1.2em;
  				margin-bottom: 1.5em;
			}
		</style>
	</head>
	<body class="skin-blue sidebar-collapse">
		<div class="wrapper">
			<jsp:include page="/client/account/include-parts/main_header.jsp" />
      
      
			<div class="content-wrapper">
				<div class="container">

					<section class="content">
						<div class="login-logo">
							<a href="/client/account/selectSpot"><b><%=App.APP_DISPLAY_NAME %></b></a>
						</div>
						<div class="add-spot">
							<a href="/client/account/addSpotStep1" class="btn btn-danger btn-lg fa fa-plus"> スポットを追加</a>
						</div>
			
						<%for(Spot spot: spotList) { %>
						<div class="spot-modal">
							<div class="modal">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<h3 class="modal-title"><i class="fa fa-map-marker fa-2x"></i> <%=spot.getName() %></h3>
										</div>
										<div class="modal-body">
											<p><i class="fa fa-globe"></i> https://pluspow.com/<b>+<%=spot.getSpotId() %></b></p>
											<p><i class="fa fa-map-marker"></i> <%=spot.getAddress() %></p>
										</div>
										<div class="modal-footer">
											<a href="<%=PathUtils.spotRelativePath(spot, spot.getBaseLang()) %>" class="btn btn-primary"><i class="fa fa-pencil-square-o"></i> ページの修正</a>
											<a href="/client/account/dashboard/?spotId=<%=spot.getSpotId() %>" class="btn btn-default"><i class="fa fa-dashboard"></i> ダッシュボード</a>
										</div>
									</div><!-- /.modal-content -->
								</div><!-- /.modal-dialog -->
							</div><!-- /.modal -->
						</div><!-- /.example-modal -->
            			<%} %>
					</section>
          
				</div><!-- /.container -->
			</div><!-- /.content-wrapper -->
      
			<jsp:include page="/client/account/include-parts/main_footer.jsp" />
		</div><!-- ./wrapper -->

		<jsp:include page="/client/account/include-parts/html_script.jsp" />
	</body>
</html>

