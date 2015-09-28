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
    		padding-top: 5%;
    	}
    	#about .contents {
    		padding: 30px;
    		position: relative;
    		padding-top: 50px;
    	}
    	#about .img-contents {
			padding: 15px;
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
				<div class="col-md-5">
					<div class="contents">
						<a data-toggle="modal" 
							data-target="#selectLangModel" 
							class="cd-btn btn btn-default btn-sm solid"
							style="padding: 0 5px;"
							href="/selectLang?lang=<%=localeLang %>">
							<img class="align-middle" style="width:32px;vertical-align:middle;" src="<%=PathUtils.getCountryFlagUrl(localeLang) %>"> 
							<span class="align-middle"><%=localeLang.getName() %>  <i class="fa fa-chevron-down"></i></span>
						</a>
						<h2><fmt:message key="page.index.about.h2" /></h2>
						<h3 class="hidden-xs"><fmt:message key="page.index.about.h3" /></h3>
						<h4 class="hidden-xs"><fmt:message key="page.index.about.h4"><fmt:param><%=Lang.values().length %></fmt:param></fmt:message></h4>
						<div class="text-center action">
							<a href="/info/about" style="margin-right:20px;" class="cd-btn btn btn-default btn-sm solid"><fmt:message key="page.index.about.btn.readMore" /></a>
							<a href="/client/register" class="cd-btn btn btn-primary btn-sm solid"><fmt:message key="page.index.about.btn.StartNow" /></a>
						</div>
						
					</div>
				</div>
				<div class="col-md-7">
					<div class="img-contents">
						<a href="https://pluspow.com/+<%=App.SAMPLE_SPOT_ID %>/l-<%=localeLang.toString() %>/">
							<img class="img-responsive" src="/images/devices_play.png" />
						</a>
					</div>
				</div>
			</div>
		</section>
		
		<section id="search-jp" class="country">
			<img class="country-flag" src="/images/flag/flat/JP.png">
			<ul class="cd-hero-slider">
				<li class="cd-bg-video selected from-right">
					<div class="cd-half-width">
						<h2><fmt:message key="page.index.searchJp.h2" /></h2>
						<p><fmt:message key="page.index.searchJp.p1" /></p>
						<div class="text-center search-area">
							<div class="text-search input-group">
                    			<input type="text" class="form-control" placeholder="<fmt:message key="page.index.search.input.placeholder" />">
                    			<span class="input-group-addon"><i class="fa fa-search"></i></span>
                  			</div>
						</div>
					</div>

					<div class="cd-bg-video-wrapper" data-video="videos/video">
						<video autoplay loop muted controls poster="/pluspow/war/videos/search_ja_video_default_image.jpg" width="100%">
  						<source src="/videos/search_top_ja_superlow.mp4" type="video/mp4" />
  						<img src="/pluspow/war/videos/search_ja_video_default_image.jpg" width="100%" />
						</video>
					</div>
				</li>
			</ul>
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
