<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%
Lang localeLang =(Lang) request.getAttribute("localeLang");
%>
<fmt:setLocale value="<%=localeLang.toString() %>" />
	<section id="footer" class="footer service-footer">
		<div class="container">
		
			<%if(localeLang.isAvailable()) { %>
			<div class="row">
				<div class="col-md-10 col-md-offset-2 footer-widget">
 					<h3 class="widget-title"><%=App.APP_DISPLAY_NAME %></h3>
 				</div>
 			</div>
			
			<div class="row">
				<div class="col-md-3 col-sm-12 col-md-offset-2 footer-widget">
					<ul class="unstyled">
						<li><a href="/info/about"><fmt:message key="mainFooter.nav.about"><fmt:param><%=App.APP_DISPLAY_NAME %></fmt:param></fmt:message></a></li>
						<li><a href="/info/price"><fmt:message key="mainFooter.nav.price" /></a></li>
						<li><a href="/info/langs"><fmt:message key="mainFooter.nav.langs" /></a></li>
						<li><a href="/info/countryList"><fmt:message key="mainFooter.nav.countryList" /></a></li>
					</ul>
				</div>
			
				<div class="col-md-3 col-sm-12 footer-widget">
					<ul class="unstyled">
						<li><a href="/info/agreement"><fmt:message key="mainFooter.nav.agreement" /></a></li>
						<li><a href="/info/privacy"><fmt:message key="mainFooter.nav.privacy" /></a></li>
						<li><a href="/info/sctl"><fmt:message key="mainFooter.nav.sctl" /></a></li>
						<li><a href="/info/contact"><fmt:message key="mainFooter.nav.contact" /></a></li>
					</ul>
				</div>

				<div class="col-md-2 col-sm-12 footer-widget">
					<div class="service-social">
						<a class="fb" href="#"><i class="fa fa-facebook"></i></a>
						<a class="twt" href="#"><i class="fa fa-twitter"></i></a>
						<a class="gplus" href="#"><i class="fa fa-google-plus"></i></a>
					</div>
				</div>

			</div><!--/ Row end -->
			<%} %>
			
			<div class="row">
				<div class="col-md-12 text-center">
							
					<div class="copyright-info">
         			&copy; Copyright PluCial, Inc. <span>All Rights Reserved</span>
        			</div>
				</div>
			</div><!--/ Row end -->
		   <div id="back-to-top" data-spy="affix" data-offset-top="10" class="back-to-top affix">
				<button class="btn btn-primary" title="Back to Top"><i class="fa fa-angle-double-up"></i></button>
			</div>
		</div><!--/ Container end -->
		
	</section>