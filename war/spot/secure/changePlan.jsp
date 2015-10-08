<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%@ page import="com.pluspow.utils.*" %>
<%@ page import="java.text.NumberFormat" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
NumberFormat fPrice = NumberFormat.getNumberInstance();
boolean isLocal = Boolean.valueOf((String) request.getAttribute("isLocal"));

boolean isAlert = false;
PlanLimitType limitType = null;
if (request.getAttribute("isAlert") != null) {
	isAlert = Boolean.valueOf((String) request.getAttribute("isAlert"));
}
if (request.getAttribute("limitType") != null) {
	String limitTypeString = (String) request.getAttribute("limitType");
	
	try {
	limitType = PlanLimitType.valueOf(limitTypeString);
	}catch(Exception e) {}
}
%>
<!DOCTYPE html>
<html>
	<head>
    	<jsp:include page="/client/account/include-parts/html_head.jsp" />
    	<style>
			.content {
				margin-top: 2em;
			}
			/* Pricing table
================================================== */

.pricing{
	padding-bottom: 80px;
}

.plan {
	border: 1px solid #dedede;
	background-color: #fff;
	margin-bottom:2em;
}

.plan-name { 
	display: block; 
	font-size: 24px; 
	line-height: 30px; 
	font-weight: 700;  
	padding: 20px 0; 
	color: #959595;
}

.plan-price { 
	font-size: 48px; 
	padding: 30px 0;
	margin-bottom: 30px;
	position: relative;
	background: #f2f2f2;
}

.plan-price:after{
	bottom: -30px;
	border: solid transparent;
	content: " ";
	position: absolute;
	border-width: 15px;
	border-top-color: #f2f2f2;
	left: 50%;
	margin-left: -16px;
}

.plan.featured .plan-name{
	color: #323232;
}

.plan.featured .plan-price{
	color: #fff;
	background: #ee3b24;
}
.plan.featured .plan-price:after{
	border-top-color: #ee3b24;
}

.plan-price .currency { 
	top: -0.9em;
	font-size: 50%;
	left: -0.01em;
	font-weight: 700;
}

.plan-name small{
	display: block;
	font-size: 12px;
	font-weight: 700;
	line-height: normal;
}

.plan-price strong{
	
}

.plan-price sub{
	font-size: 18px;
	font-weight: 700;
}

.plan ul { 
	background: #fff; 
	padding: 0;
	margin-bottom: 0;
}

.plan ul li {
	border-top: 1px dashed #dedede;
	padding: 12px 29px;
	font-weight: 500;
}

.plan ul li:first-child { 
	border-top: 0 !important;
}

.plan a.btn { 
/* 	color: #323232;
	background: #fff; */
	margin-top: 25px;
	margin-bottom: -20px;
	padding: 8px 30px;
}
.plan a.btn:hover{
	color: #fff;
}

.list-unstyled strong {
	padding-left: 1em;
	font-size: 1.1em;
}
		</style>
	</head>
	<body class="login-page">
		<div class="wrapper">

			<!-- Full Width Column -->
			<div class="container">
				<div class="content">
				
					<%if(isAlert && limitType != null) {%>
					<div class="alert alert-info alert-dismissable">
                    	<h4><i class="icon fa fa-info"></i> <%=limitType.getTitle() %></h4>
                    	<%=limitType.getMessage() %>
					</div>
					<%} %>
                  
					<div class="register-logo">
						<i class="fa fa-paper-plane"></i> プランの変更
					</div>
					<h3 style="text-align: center;">
						<a href="<%=PathUtils.spotPage(spot.getSpotId(), spot.getLangUnit().getLang(), isLocal, true) %>"><b><%=spot.getName() %></b></a>
					</h3>
					
					<div class="row" style="margin-top: 3em;">
						<!-- plan start -->
						<div class="col-md-4 col-md-offset-2 col-sm-6 wow fadeInUp animated" data-wow-delay=".5s" style="visibility: visible;-webkit-animation-delay: .5s; -moz-animation-delay: .5s; animation-delay: .5s;">
							<div class="plan text-center" style="padding-bottom: 43px;">
								<span class="plan-name"><%=ServicePlan.FREE.getPlanName() %> <small>Monthly plan</small></span>
								<p class="plan-price"><strong>無料</strong></p>
								<ul class="list-unstyled">
									<li><span>利用可能言語数:</span><strong><%=spot.getBaseLang().getName() %> + 1か国語</strong></li>
									<li><span>機械翻訳:</span><strong>無料(<%=Lang.values().length %>か国語)</strong></li>
									<li><span>人力翻訳:</span><strong>利用不可</strong></li>
									<li><span>翻訳文字数上限:</span><strong><%=ServicePlan.FREE.getTransCharMaxCount() %>文字</strong></li>
									<li><span>広告:</span><strong>あり</strong></li>
								</ul>
							</div>
						</div><!-- plan end -->

						<!-- plan start -->
						<div class="col-md-4 col-sm-6 wow fadeInUp animated" data-wow-delay="1s" style="visibility: visible;-webkit-animation-delay: 1s; -moz-animation-delay: 1s; animation-delay: 1s;">
							<div class="plan text-center featured">
								<span class="plan-name"><%=ServicePlan.STANDARD.getPlanName() %> <small>Monthly plan</small></span>
								<p class="plan-price">
									<sup class="currency">(年契約)</sup>
									<strong>¥<%=(int)ServicePlan.STANDARD.getMonthlyAmount() %></strong>
									<sub> / 月</sub></p>
								<ul class="list-unstyled">
									<li><span>利用可能言語数:</span><strong><%=spot.getBaseLang().getName() %> + <%=Lang.values().length - 1 %>か国語</strong></li>
									<li><span>機械翻訳:</span><strong>無料(<%=Lang.values().length %>か国語)</strong></li>
									<li><span>人力翻訳:</span><strong>利用可能(6か国語)</strong></li>
									<li><span>翻訳文字数上限:</span><strong>無制限</strong></li>
									<li><span>広告:</span><strong>なし</strong></li>
								</ul>
								<a class="btn btn-primary">プラン変更(現在準備中)</a>
							</div>
						</div><!-- plan end -->
					</div>
				</div>
					
			</div><!-- /.content-wrapper -->
		</div><!-- ./wrapper -->
		
		<jsp:include page="/client/include-parts/main_footer.jsp" />
		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>
