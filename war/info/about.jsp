<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="com.pluspow.App" %>
<%@ page import="com.pluspow.enums.*" %>
<%@ page import="com.pluspow.utils.*" %>
<%
Lang localeLang =(Lang) request.getAttribute("localeLang");
%>
<fmt:setLocale value="<%=localeLang.toString() %>" />
<!DOCTYPE html>
<html>
<head>
	<jsp:include page="/spot/include-parts/html_head.jsp" />
    <style>
    	video {
    		z-index:-100;
    	}
    	#about {
    		box-shadow: inset 0 0 20px rgba(0,0,0, 0.3);
    		-webkit-box-shadow: inset 0 0 20px rgba(0,0,0, 0.3);
    		-moz-box-shadow: inset 0 0 20px rgba(0,0,0, 0.3);
   			-o-box-shadow: inset 0 0 20px rgba(0,0,0, 0.3);
    	}
     	#about .action {
    		margin-top: 30px;
    	}
    	#about h3 {
    		margin-top: 30px;
    	}
    	.country {
  			position: relative;
  			padding: 0;
    	}
    	.country h2, .country p {
    		text-shadow: 1px 1px 3px rgba(0,0,0,.7),1px 1px 5px rgba(0,0,0,.3);
    	}
    	
    	.country .country-flag {
    		position: absolute;
    		top: 10px;
    		left: 10px;
    		z-index: 100;
    		width: 50px;
    	}
    	.country .search-area {
    		padding: 50px;
    	}
    	.country .search-area .text-search input,
    	.country .search-area .text-search .input-group-addon {
    		    background-color: #fff;
    	}
    	.country .search-area .text-search input {
    		border-top-left-radius: 4px;
    		border-bottom-left-radius: 4px;
    	}
    	#about {
    		    padding-top: 50px;
    	}
    	#about .contents {
    		padding: 30px;
    		position: relative;
    		padding-top: 50px;
    	}
    	#about .img-contents {
			padding: 15px;
    	}
    	
    	#creator-register {
    	background: url(/videos/search_ja_video_default_image.jpg) no-repeat;
    	}
    	#creator-register {
    	}
    </style>
</head>
<body lang="<%=localeLang %>">
	<div class="body-inner">
		<!-- Header start -->
 		<jsp:include page="/include-parts/main_header.jsp" />
		<!-- Header end -->
		
		<section id="about" class="no-padding">
			<div class="row">
				<div class="col-md-6">
					<div class="contents text-center">
						<h2 class="">PLUSPOW（プラスポ）とは？</h2>
						<h3>PLUSPOW＝『インバウンド・ガイドブック』</h3>
						<h4>
							PLUSPOWは、あなたのお店を外国人観光客に知ってもらうための「ガイドブックページ」を簡単に作成できるWEBサービスです。</h4>
						<div class="text-center action">
							<a href="/client/register" class="cd-btn btn btn-primary btn-sm solid"><fmt:message key="page.index.about.btn.StartNow" /></a>
						</div>
						
					</div>
				</div>
				<div class="col-md-6">
					<div class="img-contents">
						<a href="https://pluspow.com/+<%=App.SAMPLE_SPOT_ID %>/l-<%=localeLang.toString() %>/">
							<img class="img-responsive" src="/images/devices_play.png" />
						</a>
					</div>
				</div>
			</div>
		</section>
		
		<section id="creator-register" class="parallax parallax5">
		<div class="parallax-overlay"></div>
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<div class="col-md-4 col-sm-4 wow fadeInDown animated" data-wow-delay=".5s" style="visibility: visible;-webkit-animation-delay: .5s; -moz-animation-delay: .5s; animation-delay: .5s;">
					<div class="service-content">
						<span class="service-image"><img class="img-responsive" src="/images/about/img1.png" alt=""></span>
						<p>1.あなたのお店の概要・住所・営業時間・商品・写真などを登録するだけで、あっという間に「ガイドブックページ」のようなHPを作成できます。</p>
					</div>
				</div><!--/ End first service -->

				<div class="col-md-4 col-sm-4 wow fadeInDown animated" data-wow-delay=".8s" style="visibility: visible;-webkit-animation-delay: .8s; -moz-animation-delay: .8s; animation-delay: .8s;">
					<div class="service-content">
						<span class="service-image"><img class="img-responsive" src="/images/about/img2.png" alt=""></span>
						<p>2.世界57言語から希望の言語を選択すると、選択した言語に翻訳されたガイドブックページが言語ごとに自動的に作成されます。</p>
					</div>
				</div><!--/ End Second features -->

				<div class="col-md-4 col-sm-4 wow fadeInDown animated" data-wow-delay="1.1s" style="visibility: visible;-webkit-animation-delay: 1.1s; -moz-animation-delay: 1.1s; animation-delay: 1.1s;">
					<div class="service-content">
						<span class="service-image"><img class="img-responsive" src="/images/about/img3.png" alt=""></span>
						<p>3.各言語ごとのガイドブックページは自動的に各言語地域の検索エンジンに登録され、検索対象に。外国人観光客があなたのお店を見つけやすくなります。</p>
					</div>
				</div><!--/ End Third features -->
					
				</div>
			</div>
		</div><!-- Container end -->
    </section>
		
		<section id="" class="no-padding">
			<div class="row">
				<div class="col-md-7">
					<div class="contents" style="padding:50px 30px;">
						<h2 class="text-center">ガイドブックページがインバウンドに「効く」理由。</h2>
						<h3 class="text-center">あなたは外国旅行をする際に、その地域で行くお店を「何」で選びますか？</h3>
						<p style="margin-top: 2em;line-height: 2.2em;">言語に堪能なごく一部の人を除き、世界中の外国人観光客のほとんどは、「母国語のガイドブック（またはHP）」を見て行くお店を決めます。つまり、「母国語のガイドブック（またはHP）」に情報が無ければ、あなたのお店が選ばれることは必然的にほとんどありません。</p>

						<p style="margin-top: 2em;line-height: 2.2em;"><strong style="color: #323232;font-size: 1.2em;">だからPLUSPOW。</strong>PLUSPOWなら、各言語ごとのガイドブックページを簡単に作成できます。しかも、各言語地域ごとにSEO対策されるので、あなたのお店が旅行時の行き先候補に入りやすくなります。</p>
						
						<h2 class="text-center" style="margin-top:2.5em;">低コストでインバウンド・マーケティングを始めましょう。</h2>
						<h3>さあ、今すぐPLUSPOWであなたのお店のガイドブックページを世界に公開し、インバウンドニーズを取り込みましょう。</h3>
						<div class="row" style="margin-top:2em;margin-bottom: 2em;">
							<div class="col-md-4 text-center">
								<i class="fa fa-check" style="font-size: 2.8em;color: #337ab7;"></i>
								<h4>世界57言語189カ国・地域をカバー。</h4>
							</div>
							<div class="col-md-4 text-center">
								<i class="fa fa-check" style="font-size: 2.8em;color: #337ab7;"></i>
								<h4>言語ごとに公開する情報の調整が可能。</h4>
							</div>
							<div class="col-md-4 text-center">
								<i class="fa fa-check" style="font-size: 2.8em;color: #337ab7;"></i>
								<h4>無料からご利用可能。</h4>
							</div>
						</div>
						
					</div>
				</div>
				<div class="col-md-5" style="padding:0 30px; padding-top: 50px;background-color: #d2d6de;box-shadow: inset 0 0 20px rgba(0,0,0, 0.3);-webkit-box-shadow: inset 0 0 20px rgba(0,0,0, 0.3);-moz-box-shadow: inset 0 0 20px rgba(0,0,0, 0.3);-o-box-shadow: inset 0 0 20px rgba(0,0,0, 0.3);">
					<div class="contents">
						<h2 class="text-center">操作マニュアル</h2>
						<h3 class="text-center">スポット登録</h3>
						<div class="cd-bg-video-wrapper" data-video="videos/video">
							<!-- <video autoplay muted controls poster="/pluspow/war/videos/search_ja_video_default_image.jpg" width="100%" style="background-color: #333;box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.04), 0 2px 10px 0 rgba(0, 0, 0, 0.06);">
	  						<source src="/videos/tutorial1_low.mp4" type="video/mp4" />
	  						<img src="/pluspow/war/videos/search_ja_video_default_image.jpg" width="100%" />
							</video> -->
							<iframe width="100%" height="315" src="https://www.youtube.com/embed/0ABm7c10ufE" frameborder="0" allowfullscreen></iframe>
						</div>
					
						<h3 class="text-center">3クリックで翻訳完了</h3>
						<div class="cd-bg-video-wrapper" data-video="videos/video">
							<iframe width="100%" height="315" src="https://www.youtube.com/embed/bVk3-pnjjJk" frameborder="0" allowfullscreen></iframe>
						</div>
						
						<h3 class="text-center">サービス登録してお店ページを充実しよう</h3>
						<div class="cd-bg-video-wrapper" data-video="videos/video">
							<iframe width="100%" height="315" src="https://www.youtube.com/embed/PXKNoBjMAmw" frameborder="0" allowfullscreen></iframe>
						</div>
					</div>
				</div>
			</div>
		</section>
		
		<!-- Footer start -->
		<jsp:include page="/include-parts/main_footer.jsp" />
		<!--/ Footer end -->
		
	</div>
	
	<jsp:include page="/spot/include-parts/dialog_modal.jsp">
		<jsp:param name="modelId" value="selectLangModel" />
	</jsp:include>
	
	<!-- javaScript start -->
 	<jsp:include page="/include-parts/html_script.jsp" />
	<!-- javaScript end -->
</body>
</html>
