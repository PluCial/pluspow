<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.model.*" %>
<%@ page import="com.pluspow.enums.*" %>
	<section id="footer" class="footer service-footer">
		<div class="container">
		
			<div class="row">
				<div class="col-md-3 col-sm-12 footer-widget">
					<h3 class="widget-title"><%=App.APP_DISPLAY_NAME %></h3>

					<ul class="unstyled">
						<li><a href="/info/agreement">利用規約</a></li>
						<li><a href="/info/privacy">プライバシーポリシー</a></li>
						<li><a href="/info/price">料金プラン</a></li>
						<li><a href="/info/sctl">特定商取引に関する表示</a></li>
						<li><a href="/info/contact">お問い合わせ</a></li>
					</ul>
<!-- 
					<div class="service-social">
								<a class="fb" href="#"><i class="fa fa-facebook"></i></a>
								<a class="twt" href="#"><i class="fa fa-twitter"></i></a>
								<a class="gplus" href="#"><i class="fa fa-google-plus"></i></a>
							</div> -->
	
				</div>
			
				<div class="col-md-5 col-sm-12 footer-widget footer-about-us">
					<h3 class="widget-title">あなたも店舗情報を世界に公開してみませんか？</h3>
					<%-- <p><strong><%=App.APP_DISPLAY_NAME %></strong>は、インバウンド市場に必要な多言語ページ、サービスメニュー、お問い合わせ管理機能(双方翻訳)などをだれでもすぐに利用できるインバウンドクラウドサービスです。</p> --%>
					<p><strong><%=App.APP_DISPLAY_NAME %></strong>は、どなたでもインバウンド市場に参加できるように作られたインバウンド市場に特化したクラウドサービス & メディアです。</p>
					<p>世界に向けての確実な情報発信をまず無料でお試しください。</p>
					<br>
					<p><a href="/" class="btn btn-primary solid">詳細 & 始める</a></p>
				</div><!--/ end about us -->

				<div class="col-md-4 col-sm-12 footer-widget">
					<h3 class="widget-title">特徴</h3>
					<ul class="">
						<li>64か国語 & 多業種に対応</li>
						<li>初期費用なし・月額無料<br />(必要に応じた有料プランあり)</li>
						<li>専門知識は一切不要<br />(登録から開始までただの10分)</li>
					</ul>
				</div>

			</div><!--/ Row end -->
			
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