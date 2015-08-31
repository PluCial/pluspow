<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%
Client client =(Client) request.getAttribute("client");
%>

		<header class="main-header">
			<!-- Logo -->
			<a href="/client/account/selectSpot" class="logo">
				<span class="logo-lg"><b><%=App.APP_DISPLAY_NAME %></b></span>
			</a>

			<nav class="navbar navbar-static-top">

				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
              
						<li class="dropdown notifications-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-user"></i>
								<span class="hidden-xs"><%=client.getName() %></span>
							</a>
							<ul class="dropdown-menu">
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
										<li>
											<a href="/client/account/addSpotStep1">
												<i class="fa fa-plus"></i> スポットの追加
											</a>
										</li>
<!-- 										<li>
											<a href="#">
												<i class="fa fa-credit-card"></i> クレジットカードの設定
											</a>
										</li> -->
									</ul>
								</li>
								<li class="footer">
									<a href="/client/logout">ログアウト</a>
								</li>
							</ul>
						</li>

					</ul>
				</div>
			</nav>
		</header>