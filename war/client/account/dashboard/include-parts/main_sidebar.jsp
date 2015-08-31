<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="org.slim3.util.StringUtil" %>
<%
Client client =(Client) request.getAttribute("client");
Spot baseSpot =(Spot) request.getAttribute("spot");
String activeMenu =(String) request.getAttribute("activeMenu");
%>

	<aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
          	<li>
              <a href="/client/account/dashboard/?spotId=<%=baseSpot.getSpotId() %>">
                <i class="fa fa-map-marker fa-2x" style="color:#dd4b39"></i> <span style="color:#fff"><b><%=baseSpot.getName() %></b></span>
              </a>
            </li>

<!--             <li>
              <a href="mailbox/mailbox.html">
                <i class="fa fa-envelope"></i> <span>お問い合わせ</span>
                <small class="label pull-right bg-yellow">0</small>
              </a>
            </li> -->
<%--             <li class="<%=!StringUtil.isEmpty(activeMenu) && activeMenu.equals("edit") ? "active" : "" %>">
              <a href="/client/spot/edit?spotId=<%=baseSpot.getSpotId() %>">
                <i class="fa fa-pencil-square-o"></i> <span>スポット情報の修正</span>
              </a>
            </li>
            <li class="<%=!StringUtil.isEmpty(activeMenu) && activeMenu.equals("item") ? "active" : "" %>">
            	<a href="#"><i class="fa fa fa-th"></i> <span>アイテム管理</span></a>
            </li> --%>
<%--             <li class="<%=!StringUtil.isEmpty(activeMenu) && activeMenu.equals("howto") ? "active" : "" %>">
            	<a href="#"><i class="fa fa-lightbulb-o"></i> <span>ハウツー管理</span></a>
            </li>
            
            <li><a href="#"><i class="fa fa-paper-plane"></i> <span>プランの変更</span></a></li> --%>
            
<!--             <li class="header">ドキュメント</li> -->
            <li><a href="#"><i class="fa fa-file-text-o"></i> <span>利用規約</span></a></li>
            <li><a href="#"><i class="fa fa-file-text-o"></i> <span>プライバシーポリシー</span></a></li>
            <li><a href="#"><i class="fa fa-file-text-o"></i> <span>翻訳プランと費用</span></a></li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>

	