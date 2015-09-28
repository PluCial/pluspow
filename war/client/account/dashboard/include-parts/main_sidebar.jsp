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

			<li><a href="/info/price"><i class="fa fa-file-text-o"></i> 料金プラン</a></li>
			<li><a href="/info/langs"><i class="fa fa-file-text-o"></i> サポート言語一覧</a></li>
			<li><a href="/info/countryList"><i class="fa fa-file-text-o"></i> サポート国一覧</a></li>
            <li><a href="/info/agreement"><i class="fa fa-file-text-o"></i> <span>利用規約</span></a></li>
            <li><a href="/info/privacy"><i class="fa fa-file-text-o"></i> <span>プライバシーポリシー</span></a></li>
            <li><a href="/info/contact"><i class="fa fa-file-text-o"></i> <span>お問い合わせ</span></a></li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>

	