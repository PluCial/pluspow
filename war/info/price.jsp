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
NumberFormat fPrice = NumberFormat.getNumberInstance();
%>
<!DOCTYPE html>
<html>
	<head>
    	<jsp:include page="/include-parts/html_head.jsp" />
    	<style>
.content {
	margin-top: 2em;
}
/* Pricing table
================================================== */
.pricing {
	padding-bottom: 80px;
}

.plan {
	border: 1px solid #dedede;
	background-color: #fff;
	margin-bottom: 2em;
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

.plan-price:after {
	bottom: -30px;
	border: solid transparent;
	content: " ";
	position: absolute;
	border-width: 15px;
	border-top-color: #f2f2f2;
	left: 50%;
	margin-left: -16px;
}

.plan.featured .plan-name {
	color: #323232;
}

.plan.featured .plan-price {
	color: #fff;
	background: #ee3b24;
}

.plan.featured .plan-price:after {
	border-top-color: #ee3b24;
}

.plan-price .currency {
	top: -0.9em;
	font-size: 50%;
	left: -0.01em;
	font-weight: 700;
}

.plan-name small {
	display: block;
	font-size: 12px;
	font-weight: 700;
	line-height: normal;
}

.plan-price strong {
	
}

.plan-price sub {
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

.plan a.btn:hover {
	color: #fff;
}

.list-unstyled strong {
	padding-left: 1em;
	font-size: 1.1em;
}

.trans-price .plan-price:after {
	display: none;
}

.trans-price .plan-price {
	margin-bottom: 0;
}

ul.nav.nav-tabs {
	border-bottom: 0;
}

ul.nav.nav-tabs li {
	display: inline-block;
	float: none;
	padding: 15px 0;
}

ul.nav.nav-tabs li a {
	color: #555;
	font-weight: 700;
}

ul.nav.nav-tabs li.active {
	border-bottom: 5px solid #ee3b24;
}

ul.nav.nav-tabs li a,ul.nav.nav-tabs li.active a {
	background-color: transparent;
	border: 0;
	width: 200px;
    text-align: center;
}
ul.nav.nav-tabs li.active a {
	color: #323232;
}

</style>
	</head>
	<body class="info">
	
		<div class="body-inner">
			<!-- Header start -->
 			<jsp:include page="/include-parts/main_header.jsp" />
			<!-- Header end -->

			<section id="main-container">
				<!-- Full Width Column -->
				<div class="container">
					<div class="content">
					
 						<h2 style="text-align: center;">
							<i class="fa fa-paper-plane"></i> 料金プラン
						</h2>
						
						<div class="text-center">
							<ul class="nav nav-tabs text-center">
								<li class="active"><a href="#service-plan" data-toggle="tab">サービスプラン</a></li>
								<li><a href="#trans-plan" data-toggle="tab">翻訳プラン</a></li>
							</ul>
						</div>
					
						<div class="tab-content">
							<div class="tab-pane active" id="service-plan">
								<div class="row" style="margin-top: 3em;">
									<!-- plan start -->
									<div class="col-md-4 col-md-offset-2 col-sm-6 wow fadeInUp animated" data-wow-delay=".5s">
										<div class="plan text-center">
											<span class="plan-name"><%=ServicePlan.FREE.getPlanName() %> <small>Monthly plan</small></span>
											<p class="plan-price"><strong>無料</strong></p>
											<ul class="list-unstyled">
												<li><span>利用可能言語数:</span><strong>母国語 + 1か国語</strong></li>
												<li><span>機械翻訳:</span><strong>無料(<%=Lang.values().length %>か国語)</strong></li>
												<li><span>人力翻訳:</span><strong>利用不可</strong></li>
												<li><span>翻訳文字数上限:</span><strong><%=ServicePlan.FREE.getTransCharMaxCount() %>文字</strong></li>
												<li><span>広告:</span><strong>あり</strong></li>
											</ul>
											<a class="btn btn-primary" href="/client/register">今すぐ始める</a>
										</div>
									</div><!-- plan end -->

									<!-- plan start -->
									<div class="col-md-4 col-sm-6 wow fadeInUp animated" data-wow-delay="1s">
										<div class="plan text-center featured" style="padding-bottom: 43px;">
											<span class="plan-name"><%=ServicePlan.STANDARD.getPlanName() %> <small>Monthly plan</small></span>
											<p class="plan-price">
												<sup class="currency">(準備中)</sup>
												<strong>¥<%=(int)ServicePlan.STANDARD.getMonthlyAmount() %></strong>
												<sub> / 月</sub>
											</p>
											<ul class="list-unstyled">
												<li><span>利用可能言語数:</span><strong>母国語 + <%=Lang.values().length - 1 %>か国語</strong></li>
												<li><span>機械翻訳:</span><strong>無料(<%=Lang.values().length %>か国語)</strong></li>
												<li><span>人力翻訳:</span><strong>利用可能(6か国語)</strong></li>
												<li><span>翻訳文字数上限:</span><strong>無制限</strong></li>
												<li><span>広告:</span><strong>なし</strong></li>
											</ul>
										</div>
									</div><!-- plan end -->
								</div>
							</div>

							<div class="tab-pane" id="trans-plan">
								<div class="row" style="margin-top: 3em;">
									<!-- plan start -->
									<div class="col-md-3 col-sm-6 wow fadeInUp animated" data-wow-delay=".5s">
										<div class="plan text-center trans-price">
											<span class="plan-name">
												<i class="fa fa-cogs"></i> 
												<%=TransType.MACHINE.getName() %>
												<small>62か国語</small>
											</span>
											<p class="plan-price">
												<sup class="currency"><i class="fa fa-language"></i></sup>
												<strong>無料</strong>
											</p>
										</div>
									</div><!-- plan end -->

									<!-- plan start -->
									<div class="col-md-3 col-sm-6 wow fadeInUp animated" data-wow-delay="1s">
										<div class="plan text-center featured trans-price">
											<span class="plan-name">
												<i class="fa fa-users"></i> 
												<%=TransType.HUMAN_STANDARD.getName() %> <small>6か国語</small>
											</span>
											<p class="plan-price">
												<sup class="currency"><i class="fa fa-language"></i></sup>
												<strong>¥<%=(int)TransType.HUMAN_STANDARD.getPrice() %></strong>
												<sub> / 1文字</sub>
											</p>
										</div>
									</div><!-- plan end -->
							
									<!-- plan start -->
									<div class="col-md-3 col-sm-6 wow fadeInUp animated" data-wow-delay="1.4s">
										<div class="plan text-center trans-price">
											<span class="plan-name">
												<i class="fa fa-users"></i> 
												<%=TransType.HUMAN_BUSINESS.getName() %> <small>6か国語</small>
											</span>
											<p class="plan-price">
												<sup class="currency"><i class="fa fa-language"></i></sup>
												<strong>¥<%=(int)TransType.HUMAN_BUSINESS.getPrice() %></strong>
												<sub> / 1文字</sub>
											</p>
										</div>
									</div><!-- plan end -->
							
									<!-- plan start -->
									<div class="col-md-3 col-sm-6 wow fadeInUp animated" data-wow-delay="1.6s">
										<div class="plan text-center trans-price">
											<span class="plan-name">
												<i class="fa fa-users"></i> 
												<%=TransType.HUMAN_ULTRA.getName() %> <small>6か国語</small>
											</span>
											<p class="plan-price">
												<sup class="currency"><i class="fa fa-language"></i></sup>
												<strong><%=(int)TransType.HUMAN_ULTRA.getPrice() %></strong>
												<sub> / 1文字</sub>
											</p>
										</div>
									</div><!-- plan end -->
								</div>
							</div>
						</div>
					</div>
					
				</div><!-- /.content-wrapper -->
			</section>
			
		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
		
		</div><!-- ./wrapper -->

	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	</body>
</html>
