<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
%>

				<!-- plan start -->
				<div>
				    <div class="plan text-center featured">
				        <span class="plan-name">月曜日</span>
				        <p class="plan-price"><sup class="currency"><i class="fa fa-sun-o"></i></sup><strong>10:00</strong> <sub><i class="fa fa-moon-o"></i>21:30</sub></p>
				    </div>
				</div><!-- plan end -->

				<!-- plan start -->
				<div>
				    <div class="plan text-center featured">
				        <span class="plan-name">火曜日</span>
				        <p class="plan-price"><sup class="currency">$</sup><strong>99</strong><sub>.99</sub></p>
				    </div>
				</div><!-- plan end -->

				<!-- plan start -->
				<div>
				    <div class="plan text-center featured">
				        <span class="plan-name">水曜日</span>
				        <p class="plan-price"><sup class="currency">$</sup><strong>149</strong><sub>.99</sub></p>
				    </div>
				</div><!-- plan end -->

				<!-- plan start -->
				<div>
				    <div class="plan text-center featured">
				        <span class="plan-name">木曜日</span>
				        <p class="plan-price"><sup class="currency">$</sup><strong>399</strong><sub>.99</sub></p>
				    </div>
				</div><!-- plan end -->
			
				<!-- plan start -->
				<div>
				    <div class="plan text-center featured">
				        <span class="plan-name">金曜日</span>
				        <p class="plan-price"><sup class="currency">$</sup><strong>49</strong><sub>.99</sub></p>
				    </div>
				</div><!-- plan end -->

				<!-- plan start -->
				<div>
				    <div class="plan text-center">
				        <span class="plan-name">土曜日</span>
				        <p class="plan-price"><strong>休み</strong></p>
				    </div>
				</div><!-- plan end -->

				<!-- plan start -->
				<div>
				    <div class="plan text-center">
				        <span class="plan-name">日曜日</span>
				        <p class="plan-price"><strong>休み</strong></p>
				    </div>
				</div><!-- plan end -->