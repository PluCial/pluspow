<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.pluspow.utils.*" %>
<%
Spot spot =(Spot) request.getAttribute("spot");
SupportLang lang =(SupportLang) request.getAttribute("lang");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
boolean isEditPage = Boolean.valueOf((String) request.getParameter("isEditPage"));
List<SupportLang> suppertLangList = (List<SupportLang>) request.getAttribute("suppertLangList");
%>
	<section id="spot-home" style="background-image:
                  url(https://d13pix9kaak6wt.cloudfront.net/background/users/n/i/r/nirajmehdiratta_1405985339_55.jpg)">
<!-- 		<div class="parallax-overlay"></div> -->
		<div id="spot-catch" class="spot-detail-inner"><!-- spot-image -->
                  
				<div class="detail">
					<h1>
						<a href="/+<%=spot.getSpotId() %>/l-<%=lang.toString() %>/"><%=spot.getName() %></a>
					</h1>
				</div>
				<div class="lang-box">
                        
					<!-- langs-select start -->
					<%if(suppertLangList != null && suppertLangList.size() > 0) { %>
						<select id="langs-select" class="" name="lang">
							<%for(SupportLang suppertLang: suppertLangList) {%>
								<option value="<%=suppertLang.toString() %>" <%=spot.getSpotLangInfo().getLang() == suppertLang ? "selected" : "" %>><%=suppertLang.getName() %></option>
							<%} %>
							<%if(isOwner && isEditPage) { %>
								<option value="add">&lt; 追加... &gt;</option>
							<%} %>
						</select>
					<%} %>
				</div>

			</div><!-- spot-catch end-->
	</section>
	<!-- <section id="home" class="no-padding">
		Carousel
    	<div id="main-slide" class="carousel slide" data-ride="carousel">

			Carousel inner
			<div class="carousel-inner" style="background-image:
                  url(/spot/images/spot.jpg)">

			</div>Carousel inner end
			
			<div class="cd-slider-nav">
				<nav>
				<span class="cd-marker item-1"></span>
					<ul>
						<li class="selected"><a href="#0"><i class="fa fa-cutlery"></i> 飲食</a></li>
						<li class="selected"><a href="#0"><i class="fa fa-hotel"></i> 宿泊</a></li>
						<li class=""><a href="#0"><i class="fa fa-shopping-cart"></i> 買い物</a></li>
						<li class=""><a href="#0"><i class="fa fa-bicycle"></i> 娯楽</a></li>
						<li class=""><a href="#0"><i class="fa fa-heart-o"></i> 癒し</a></li>
						<li class="selected"><a href="#0"><i class="fa fa-ellipsis-h"></i> その他</a></li>
					</ul>
				</nav> 
			</div>
		</div>/carousel    	
    </section> -->