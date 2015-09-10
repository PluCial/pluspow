<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%
Client client =(Client) request.getAttribute("client");
Spot spot =(Spot) request.getAttribute("spot");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
List<Item> itemList =(List<Item>) request.getAttribute("itemList");
Map<String,Item> langItemMap = (Map<String,Item>) request.getAttribute("langItemMap");
SupportLang lang = (SupportLang)request.getAttribute("lang");
boolean isEditPage = Boolean.valueOf((String) request.getParameter("isEditPage"));
%>
		<div class="container">
			<div class="row <%=isOwner && isEditPage && spot.getLangUnit().getLang() == spot.getBaseLang() ? "connectedSortable" :""  %>">
				
				<%for(Item item: itemList) { 
					boolean isSupport = item.getSupportLangs().indexOf(lang) >= 0 ? true : false;
					String itemLink = "/+" + spot.getSpotId() + "/l-";
					if(isSupport) {
						itemLink = itemLink + spot.getLangUnit().getLang().toString();
					}else {
						itemLink = itemLink + item.getBaseLang().toString();
					}
					itemLink = itemLink + "/item/" + item.getKey().getName();
				%>
				<div class="col-sm-4 col-xs-12 item-box wow fadeInUp animated <%=isSupport ? "" : "not-support-lang" %>" 
					data-spot-id="<%=spot.getSpotId() %>" 
					data-item-id="<%=item.getKey().getName() %>" 
					data-order="<%=item.getSortOrder() %>" 
					data-sort-max-order="<%=spot.getItemCounts().getItem() %>">

						<figure data-wow-duration="500ms" data-wow-delay="0ms">
							<a href="<%=itemLink %>">
								<img src="/spot/images/portfolio/portfolio4.jpg" alt="">
								<span class="item-name">
									<%if(isSupport && isEditPage && !spot.getBaseLang().equals(lang) && langItemMap != null) { %>
										<%=langItemMap.get(item.getKey().getName()) != null ? langItemMap.get(item.getKey().getName()).getName() : item.getName() %>
									<%}else { %>
										<%=item.getName() %>
									<%} %>
								</span>
							</a>
							<figcaption>
								<%if(isSupport) { %>
								<a class="" href="/+<%=spot.getSpotId() %>/l-<%=spot.getSpotLangInfo().getLang().toString() %>/item/<%=item.getKey().getName() %>">
									<i class="fa fa-link"></i>
								</a>
								<%}else { %>
								<a class="" href="/spot/secure/trans?spotId=<%=spot.getSpotId() %>&transGroup=<%=ResGroups.ITEM.toString()%>&itemId=<%=item.getKey().getName() %>&transLang=<%=lang.toString() %>">
									<i class="fa fa-language"></i>
								</a>
								<%} %>
							</figcaption>
							
							<div class="sort-bar">
								<i class="fa fa-arrows"></i>
							</div>
						</figure>
				</div><!--/ item 1 end -->
				<%} %>
					
			</div><!-- Content row end -->
		</div><!-- Container end -->