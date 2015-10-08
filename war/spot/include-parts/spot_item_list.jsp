<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.pluspow.utils.*" %>
<%
Client client =(Client) request.getAttribute("client");
Spot spot =(Spot) request.getAttribute("spot");
boolean isOwner = Boolean.valueOf((String) request.getAttribute("isOwner"));
List<Item> itemList =(List<Item>) request.getAttribute("itemList");
/* Map<String,Item> langItemMap = (Map<String,Item>) request.getAttribute("langItemMap"); */
Lang lang = (Lang)request.getAttribute("lang");
boolean isEditPage = Boolean.valueOf((String) request.getParameter("isEditPage"));
boolean isLocal = Boolean.valueOf((String) request.getAttribute("isLocal"));
boolean isClientLogged = Boolean.valueOf((String) request.getAttribute("isClientLogged"));
%>
				
				<%for(Item item: itemList) {
					ItemLangUnit langUnit = item.getLangUnit();
					boolean isSupport = item.getLangs().indexOf(lang) >= 0 ? true : false;
				%>
				<div class="col-sm-4 col-xs-12 item-box wow fadeInUp animated <%=isSupport ? "" : "not-support-lang" %>" 
					data-spot-id="<%=spot.getSpotId() %>" 
					data-item-id="<%=item.getKey().getName() %>" 
					data-order="<%=item.getSortOrder() %>" 
					data-sort-max-order="<%=spot.getItemCounts().getItem() %>">
					
						<figure data-wow-duration="500ms" data-wow-delay="0ms">
							
							<div class="img-wrapper" style="background-image: url(<%=item.getItemImageUrl() %>)">
								<a href="<%=isSupport ? PathUtils.itemPage(spot, item.getKey().getName(), lang, isLocal, isClientLogged) : PathUtils.itemPage(spot, item.getKey().getName(), spot.getBaseLang(), isLocal, isClientLogged) %>"></a>
								<%if(item.getPrice() > 0) { %>
								<div class="price-label"><i class="fa fa-jpy"></i> <span><%=item.getPriceString() %></span></div>
								<%} %>
							</div>

							<figcaption>
                                <h4 class="text-ellipsis">
                                	<a href="<%=PathUtils.itemPage(spot, item.getKey().getName(), item.getLangUnit().getLang(), isLocal, isClientLogged) %>">
                                		<%=item.getName() %>
                                		<%-- <%if(isSupport && isEditPage && !spot.getBaseLang().equals(lang) && langItemMap != null) { %>
											<%=langItemMap.get(item.getKey().getName()) != null ? langItemMap.get(item.getKey().getName()).getName() : item.getName() %>
										<%}else { %>
											<%=item.getName() %>
										<%} %> --%>
                                	</a>
                                </h4>
                                
                                <%if(!isSupport) { %>
                                <div class="parallax-overlay">
                                	<a class="btn btn-default" href="/spot/secure/trans?spotId=<%=spot.getSpotId() %>&objectType=<%=ObjectType.ITEM.toString()%>&itemId=<%=item.getKey().getName() %>&transLang=<%=lang.toString() %>">
                                		<i class="fa fa-language"></i> <%=lang.getName() %>ページに追加
                                	</a>
                                </div>
                                <%} %>
                             </figcaption>
							
							<%if(isOwner && isSupport) { %>
							<div class="sort-bar">
								<i class="fa fa-ellipsis-v"></i>
								<i class="fa fa-ellipsis-v"></i>
							</div>
							
							<%if(isEditPage) { %>
							<div class="item-menu btn-group">
								<button class="btn btn-sm dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-bars"></i></button>
								<ul class="dropdown-menu pull-right" role="menu">
									<%if(spot.getBaseLang() != lang) { %>
									<li><a href="/spot/secure/trans?spotId=<%=spot.getSpotId() %>&itemId=<%=item.getKey().getName() %>&objectType=<%=ObjectType.ITEM %>&transLang=<%=spot.getLangUnit().getLang().toString() %>">
										<i class="fa fa-language"></i> 再翻訳</a>
									</li>
									<li class="divider"></li>
									<li><a href="/spot/secure/setLangInvalid?spotId=<%=spot.getSpotId() %>&objectType=<%=ObjectType.ITEM %>&lang=<%=spot.getLangUnit().getLang() %>&itemId=<%=item.getKey().getName() %>&invalid=true">
										<i class="fa fa-times"></i> <%=lang.getName() %>ページから削除</a>
									</li>
									<%}else { %>
									<li>
										<a data-toggle="modal" 
											data-backdrop="static" 
											data-target="#itemDeleteModal" 
											href="/spot/secure/itemDelete?spotId=<%=spot.getSpotId() %>&lang=<%=spot.getLangUnit().getLang().toString() %>&itemId=<%=item.getKey().getName() %>">
											<i class="fa fa-trash"></i> このアイテムを削除
										</a>
									</li>
									<%} %>
								</ul>
							</div>
							<%} %>
							<%} %>
						</figure>
				</div><!--/ item 1 end -->
				<%} %>