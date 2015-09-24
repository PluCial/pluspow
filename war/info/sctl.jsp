<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/include-parts/html_head.jsp" />
	<style>
		table.table>tbody>tr>th,table.table>tbody>tr>td {
			padding: 20px 15px;
		}
	</style>
</head>
	
<body class="info">

	<div class="body-inner">
		<!-- Header start -->
 		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->
    	
    	<!-- Main container start -->
	<section id="main-container">
		<article class="container">
			<header>
				<div>
					<span class="title-icon classic pull-left"><i class="fa fa-file-text-o"></i></span>
					<h2 class="title classic">特定商取引法に基づく表記</h2>
					| <%=App.APP_DISPLAY_NAME %><small class="pull-right">v1: 2/10/2015</small>
				</div>
				
			</header>

			
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="faq-box">
						<div class="box-header">
							<h3 class="box-title">事業者について</h3>
						</div>
						<div class="alt-table-responsive">
						<table class="table table-striped">
							<tbody>
								<tr>
									<th class="col-md-5">販売事業者名</th>
									<td>株式会社プラシャル</td>
								</tr>
								<tr>
									<th>販売事業者所在地</th>
									<td>〒812-0013<br />福岡県福岡市博多区博多駅東1-12-17 オフィスニューガイア博多駅前3F</td>
								</tr>
								<tr>
									<th>代表者</th>
									<td>高原 功(タカハラ コウ)</td>
								</tr>
								<tr>
									<th>連絡先／ホームページ</th>
									<td>http://inc.plucial.com/</td>
								</tr>
								<tr>
									<th>連絡先／電子メール</th>
									<td>info@plucial.com</td>
								</tr>
								<tr>
									<th>連絡先／電話番号</th>
									<td>準備中</td>
								</tr>
							</tbody>
						</table>
						</div>
						
						<div class="box-header" style="margin-top:3em;">
							<h3 class="box-title">販売について</h3>
						</div>
						<div class="alt-table-responsive">
						<table class="table table-striped">
							<tbody>
								<tr>
									<th class="col-md-5">販売価格帯</th>
									<td>サービスごとに販売価格（消費税等含む）を設定し、該当ページで表示します。</td>
								</tr>
								<tr>
									<th>商品等の引き渡し時期（日数）・発送方法</th>
									<td>オンラインにおけるサービス利用</td>
								</tr>
								<tr>
									<th>代金の支払時期および方法</th>
									<td>サービスごとに支払時期および方法を指定し、該当ページで表示します。</td>
								</tr>
								<tr>
									<th>商品代金以外に必要な費用</th>
									<td>なし</td>
								</tr>
								<tr>
									<th>送料、消費税等</th>
									<td>すべての料金は税抜き価格にて表示</td>
								</tr>
								<tr>
									<th>返品の取扱条件／返品期限</th>
									<td>解約はサービスごとに当社指定の手続きにより可能。</td>
								</tr>
								<tr>
									<th>返金</th>
									<td>既に支払われた分に対する返金は不可。</td>
								</tr>
							</tbody>
						</table>
						</div>
					</div>
				</div><!-- End col-md-6 -->
			</div><!-- Content row  end -->

		</article><!--/ container end -->

	</section><!--/ Main container end -->
	

		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
	
	</div><!-- Body inner end -->
	
	<!-- javaScript start -->
	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
	
</body>
</html>
