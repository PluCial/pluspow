<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.utils.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%
	Client client =(Client) request.getAttribute("client");
Spot spot =(Spot) request.getAttribute("spot");
List<SpotLangUnit> spotLangUnitList =(List<SpotLangUnit>) request.getAttribute("spotLangUnitList");
List<TransHistory> transHistoryList =(List<TransHistory>) request.getAttribute("transHistoryList");
NumberFormat fPrice = NumberFormat.getNumberInstance();
/* ServicePlan planEnum = baseSpot.getPlan().getPlan(); */
Errors errors =(Errors) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html>
	<head>
    	<jsp:include page="/client/account/dashboard/include-parts/html_head.jsp" />
    	<style>
			
			#chat-box tr td {
				text-align: left;
  				vertical-align: middle;
			}
			
			#chat-box tr td:first-child {
				width: 50px;
			}
			
			#chat-box tr td:first-child img {
				width: 40px;
				height: 30px
			}
			
			#chat-box tr td.action {
				width: 20px;
			}
			#chat-box tr td.action a i {
				font-size: 1.5em;
			}
			#chat-box li.item {
				padding: 10px;
			}
			#controls .info-box .info-box-icon {
				background-color: #fff;
			}
			
			#controls .info-box .info-box-text {
				margin-bottom: 0.5em;
			}
		</style>
	</head>
	<body class="skin-blue sidebar-mini">
		<div class="wrapper">
      
			<jsp:include page="/client/account/dashboard/include-parts/main_header.jsp" />
			
			<jsp:include page="/client/account/dashboard/include-parts/main_sidebar.jsp" />

			<!-- Full Width Column -->
			<div class="content-wrapper">
					<!-- Content Header (Page header) -->
					<section class="content-header">
						<h1>
							<%=spot.getName() %>
							<small>ダッシュボード</small>
						</h1>
						<ol class="breadcrumb">
							<li><a href="/client/account/selectSpot"><i class="fa fa-home"></i> ホーム</a></li>
							<li class="active"><i class="fa fa-map-marker"></i> <%=spot.getName() %></li>
						</ol>
					</section>
 
					<div class="content">

						<div class="row">
							
							<div class="col-md-8 col-xs-12">

								<section class="box box-solid">
 									<div class="box-header">
										<i class="fa fa-globe"></i>
										<h3 class="box-title">グローバル化</h3>
										<div style="text-align: right;"><b><span style="font-size:1.4em;color:red"><%=spot.getLangs().size() %></span> / <%=Lang.values().length%></b> 国語</div>
										<div class="progress progress-xs">
											<div class="progress-bar" style="width: <%=((float)spot.getLangs().size() / (float)Lang.values().length) * 100%>%"></div>
										</div>
									</div>
									<!-- /.box-header-->

									<div class="box-body chat" id="chat-box">
										<table class="table table-hover table-striped">
											<%
												for(Lang langInfo: spot.getLangs()) {
											%>
											<tr>
												<td class="action">
													<span style="background-image: url(/images/flag/<%=langInfo.getLangKey().toUpperCase() %>.png);background-repeat:no-repeat;background-position: center left;background-size: 34px;padding-left:50px;"></span>
												</td>
												<td><%=langInfo.getName() %></td>
												<td class="action"><a href="/+<%=spot.getSpotId() %>/l-<%=langInfo.toString() %>/" class="btn btn-box-tool"><i class="fa fa-external-link"></i></a></td>
											</tr>
											<%} %>
										</table>
										
									</div><!-- /.box-body -->
									
								</section>
							</div>
							
							<div id="" class="col-md-4 col-xs-12">
							
								<%if(spot.getPlan() == ServicePlan.FREE) { %>
								<section class="small-box bg-yellow">
									<div class="inner">
										<h3><%=spot.getPlan().getPlanName() %></h3>
										<p><%=fPrice.format((int)spot.getPlan().getMonthlyAmount()) %> / 月</p>
									</div>
									<div class="icon">
										<i class="fa fa-paper-plane"></i>
									</div>
									<a href="<%=PathUtils.changePlanPage(spot, false) %>" class="small-box-footer">プランの変更 <i class="fa fa-arrow-circle-right"></i>
										 <!-- <i class="fa fa-arrow-circle-right"></i> -->
									</a>
								</section>
								<%} %>
								
								<section class="info-box bg-aqua">
									<span class="info-box-icon"><i class="fa fa-language"></i></span>
									<div class="info-box-content">
										<span class="info-box-text">翻訳した文字数</span>
										<span class="info-box-number"><%=spot.getTransAcc().getTransCharCount() %> 文字</span>
										<div class="progress">
											<div class="progress-bar" style="width: <%=((float)spot.getTransAcc().getTransCharCount() / (float)spot.getPlan().getTransCharMaxCount()) * 100 %>%"></div>
										</div>
										<span class="progress-description">フリープランでは<%=spot.getPlan().getTransCharMaxCount() %>文字まで翻訳できます。</span>
									</div><!-- /.info-box-content -->
								</section><!-- /.info-box -->
							
							</div>

						</div>
				
						<section class="box box-primary">
 							<div class="box-header with-border">
								<h3 class="box-title"><i class="fa fa-language"></i> 翻訳履歴</h3>
                			</div>
                			<!-- /.box-header -->
                
							<div class="box-body">
								<div class="table-responsive">
									<table class="table table-hover table-striped">
										<thead>
											<tr>
												<th>翻訳日時</th>
												<td>翻訳対象</td>
												<th>翻訳言語</th>
												<th>翻訳方法</th>
												<th>翻訳文字数</th>
												<th>コスト</th>
												<th>ステータス</th>	
											</tr>
										</thead>
										<tbody>
											<%for(TransHistory history: transHistoryList) { 
											%>
											<tr>
												<td><%=DateUtils.dateToString(history.getCreateDate(), "yyyy/MM/dd HH:mm:ss") %></td>
												<td><%=history.getObjectType().getName() %></td>
												<%if(history.getObjectType() == ObjectType.SPOT) { %>
												<td>
													<a href="<%=PathUtils.spotPage(spot, history.getBaseLang())  %>">
														<%=history.getBaseLang().getName() %>
													</a> → 
													<a href="<%=PathUtils.spotPage(spot, history.getTransLang())  %>">
														<%=history.getTransLang().getName() %>
													</a>
												</td>
												<%}else if(history.getObjectType() == ObjectType.ITEM) { %>
												<td>
													<a href="<%=PathUtils.itemPage(spot, history.getItemRef().getKey().getName(), history.getBaseLang()) %>">
														<%=history.getBaseLang().getName() %>
													</a> → 
													<a href="<%=PathUtils.itemPage(spot, history.getItemRef().getKey().getName(), history.getTransLang()) %>">
														<%=history.getTransLang().getName() %>
													</a>
												</td>
												<%} %>
												<td><%=history.getTransType().getName() %>(¥ <%=history.getCharUnitPrice() %>/1文字)</td>
												<td><%=history.getTransCharCount() %></td>
												<td>¥<%=(int)history.getTransCost() %></td>
												<td><span class="label label-success"><%=history.getTransStatus().getName() %></span></td>
											</tr>
											<%} %>
										</tbody>
									</table>
								</div><!-- /.table-responsive -->
							</div><!-- /.box-body -->
							<!-- <div class="box-footer clearfix">
								<a href="javascript::;" class="btn btn-sm btn-default btn-flat pull-right">もっと見る</a>
							</div> --><!-- /.box-footer -->
						</section>
					
				</div>
					
					

					
					
					
			</div><!-- /.content-wrapper -->
      
			<jsp:include page="/client/account/dashboard/include-parts/main_footer.jsp" />
		</div><!-- ./wrapper -->

		<jsp:include page="/client/account/dashboard/include-parts/html_script.jsp" />
	</body>
</html>
