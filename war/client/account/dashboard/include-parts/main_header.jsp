<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%
Client client =(Client) request.getAttribute("client");
Spot baseSpot =(Spot) request.getAttribute("spot");
%>
		<header class="main-header">
			<!-- Logo -->
			<a href="/client/account/selectSpot" class="logo">
				<span class="logo-mini"><b>N+</b></span>
				<span class="logo-lg"><b><%=App.APP_DISPLAY_NAME %></b></span>
			</a>

			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
          		<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            		<span class="sr-only">Toggle navigation</span>
          		</a>
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<!-- Messages: style can be found in dropdown.less-->
						<li class="dropdown messages-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-envelope-o"></i>
								<span class="label label-success">0</span>
							</a>
							<ul class="dropdown-menu">
								<li class="header">未読のお問い合わせ 0 件</li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
                      
									</ul>
								</li>
								<!-- <li class="footer"><a href="#">See All Messages</a></li> -->
							</ul>
						</li>
              
              			<!-- Notifications: style can be found in dropdown.less -->
						<li class="dropdown notifications-menu">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="fa fa-bell-o"></i>
								<span class="label label-warning">0</span>
							</a>
							<ul class="dropdown-menu">
								<li class="header">未読のお知らせ 0 件</li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
                      
									</ul>
								</li>
<!--                   			<li class="footer"><a href="#">View all</a></li> -->
							</ul>
						</li>
              
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
											<a href="/client/account/selectSpot">
												<i class="fa fa-home"></i> ホームへ
											</a>
										</li>
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