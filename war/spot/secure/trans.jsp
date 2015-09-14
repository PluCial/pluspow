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
	Client client =(Client) request.getAttribute("client");
Spot baseSpot =(Spot) request.getAttribute("spot");
String transLang = (String) request.getAttribute("transLang");
int transCharCount = Integer.parseInt((String)request.getAttribute("transCharCount"));
ResGroups transGroup = (ResGroups) request.getAttribute("transGroup");
Item item =(Item) request.getAttribute("item");
%>
<!DOCTYPE html>
<html>
	<head>
    	<jsp:include page="/client/account/include-parts/html_head.jsp" />
    	<style>
			.content {
				margin-top: 2em;
			}
			
			.nosupport {
				margin-top: 2em;
			}
			
			.table>tbody>tr>th, .table>tbody>tr>td {
				  text-align: center;
			}
			
 			.table>tbody>tr>th, .table>tbody>tr>td {
				  border-top: none;
			}
			
			.table.langs {
				  margin-bottom: 0;
			}
			.table>tbody>tr>td.flag {
				text-align: right;
				padding-right: 0;
			}
			.table>tbody>tr>td.lang {
				text-align: left;
  				padding-left: 0;
			}
			
			.description-block>.description-header {
				margin-top: 0.5em;
				margin-bottom: 1.0em;
			}
		</style>
	</head>
	<body class="login-page">
		<div class="wrapper">

			<!-- Full Width Column -->
			<div class="container">
			
				<div class="content">
					<div class="register-logo">
						<a href="/+<%=baseSpot.getSpotId() %>"><b><%=baseSpot.getName() %></b></a>
					</div>
					<h3 style="text-align: center;">翻訳プランの選択</h3>
					
					<div class="row">
						<div class="col-md-8 col-md-offset-2">

							<div class="box">
								<div class="box-header with-border">
									<div class="row">
										<div class="col-md-6 col-md-offset-3">
											<table class="table langs">
												<tbody>
													<tr>
														<td class="flag">
															<span style="background-image: url(/images/flag/<%=baseSpot.getBaseLang().getLangKey().toUpperCase() %>.png);background-repeat:no-repeat;background-position: center left;background-size: 34px;padding-left:50px;"></span>
														</td>
														<td class="lang"><%=baseSpot.getBaseLang().getName() %></td>
														<td><i class="fa fa-arrow-right"></i></td>
														<td class="flag">
															<span style="background-image: url(/images/flag/<%=transLang.toUpperCase() %>.png);background-repeat:no-repeat;background-position: center left;background-size: 34px;padding-left:50px;"></span>
														</td>
														<td class="lang"><%=Lang.valueOf(transLang).getName()%></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div><!-- /.box-header -->
								<div class="box-body">
									<div class="table-responsive col-md-6 col-md-offset-3">
										<table class="table" style="font-size: 1.5em;margin-top: 20px;">
											<tbody>
												<tr>
													<th>翻訳する文字数</th>
													<th><%=transCharCount%></th>
												</tr>
											</tbody>
										</table>
									</div>
								</div><!-- ./box-body -->
								<div class="box-footer">
									<div class="row">
										<div class="col-sm-3 col-xs-6">
											<div class="description-block border-right">
												<h4 class="description-text"><i class="fa fa-cogs"></i> <%=TransType.MACHINE.getName()%></h4>
												<p>(¥<%=TransType.MACHINE.getPrice()%>/1文字)</p>
												<h5 class="description-header">¥<%=(int)(transCharCount * TransType.MACHINE.getPrice())%></h5>
												<div>
													<a href="/spot/secure/transEntry?spotId=<%=baseSpot.getSpotId()%>&transLang=<%=transLang%>&transGroup=<%=transGroup.toString()%>&itemId=<%=item == null ? "" : item.getKey().getName()%>" class="btn btn-default"><i class="fa fa-language"></i> 翻訳する</a>
												</div>
												
											</div><!-- /.description-block -->
                    					</div><!-- /.col -->
										<div class="col-sm-3 col-xs-6">
											<div class="description-block border-right">
												<h4 class="description-text "><i class="fa fa-user"></i> <%=TransType.HUMAN_STANDARD.getName()%></h4>
												
												<%
																									if(Lang.valueOf(transLang).isHumanTransSupport()) {
																								%>
												<p class="">(¥<%=TransType.HUMAN_STANDARD.getPrice()%>/1文字)</p>
												<h5 class="description-header">¥<%=(int)(transCharCount * TransType.HUMAN_STANDARD.getPrice())%></h5>
												<div>
													<a href="#" class="btn btn-success"><i class="fa fa-language"></i> 翻訳する</a>
												</div>
												<%
													}else {
												%>
												<p class="nosupport">対応していません。</p>
												<%
													}
												%>
											</div><!-- /.description-block -->
										</div><!-- /.col -->
										<div class="col-sm-3 col-xs-6">
											<div class="description-block border-right">
												<h4 class="description-text"><i class="fa fa-user"></i> <%=TransType.HUMAN_STANDARD.getName()%></h4>
												
												<%
																									if(Lang.valueOf(transLang).isHumanTransSupport()) {
																								%>
												<p class="">(¥<%=TransType.HUMAN_BUSINESS.getPrice()%>/1文字)</p>
												<h5 class="description-header">¥<%=(int)(transCharCount * TransType.HUMAN_BUSINESS.getPrice())%></h5>
												<div>
													<a href="#" class="btn btn-primary"><i class="fa fa-language"></i> 翻訳する</a>
												</div>
												<%
													}else {
												%>
												<p class="nosupport">対応していません。</p>
												<%
													}
												%>
											</div><!-- /.description-block -->
										</div><!-- /.col -->
                    
										<div class="col-sm-3 col-xs-6">
											<div class="description-block">
												<h4 class="description-text"><i class="fa fa-user"></i> <%=TransType.HUMAN_ULTRA.getName()%></h4>
												
												<%
																									if(Lang.valueOf(transLang).isHumanTransSupport()) {
																								%>
												<p class="">(¥<%=TransType.HUMAN_ULTRA.getPrice() %>/1文字)</p>
												<h5 class="description-header">¥<%=(int)(transCharCount * TransType.HUMAN_ULTRA.getPrice()) %></h5>
												<div>
													<a href="#" class="btn btn-danger"><i class="fa fa-language"></i> 翻訳する</a>
												</div>
												<%}else { %>
												<p class="nosupport">対応していません。</p>
												<%} %>
											</div><!-- /.description-block -->
										</div>
									</div><!-- /.row -->
								</div><!-- /.box-footer -->
							</div><!-- /.box -->
						</div>
					</div>
				</div>
					
			</div><!-- /.content-wrapper -->

		</div><!-- ./wrapper -->

		<jsp:include page="/client/include-parts/main_footer.jsp" />
		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
