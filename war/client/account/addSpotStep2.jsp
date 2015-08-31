<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
String content = (String) request.getAttribute("content");
%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/account/include-parts/html_head.jsp" />
		<style>
			.content-wrapper {
/* 				background-color: #d2d6de; */
			}
			h3 {
				  text-align: center;
				  margin-bottom: 2em;
			}
			.box {
				  background: #fff;
				  padding: 20px;
			}
			.content {
				margin: 5% auto;
				max-width: 600px;
			}
			.checkbox {
				margin-top: 2em;
			}
		</style>
	</head>
	<body class="skin-blue sidebar-collapse">
		<div class="wrapper">
			<jsp:include page="/client/account/include-parts/main_header.jsp" />
      
      
			<div class="content-wrapper">
				<div class="container">

					<section class="content">
						<h3>
							<i class="fa fa-plus"></i> <i class="fa fa-map-marker fa-2x" style="color:#dd4b39;"></i> 新しいスポットの作成
						</h3>
						
						<div class="progress-group">
							<span class="progress-text">現在のステップ</span>
                        	<span class="progress-number"><b>2</b>/2</span>
                        	<div class="progress sm">
                          		<div class="progress-bar progress-bar-green" style="width: 50%"></div>
                        	</div>
                      	</div>
			
						<div class="box box-primary">
                
							<!-- form start -->
							<form action="/client/account/AddSpotStep2Entry" method="post">
								<div class="box-body">
								
									<div class="form-group ${f:errorClass('name','has-error')}">
										<%if (errors.containsKey("name")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.name}</label>
										<%}else { %>
										<label for="exampleInputEmail1">スポット名</label>
										<%} %>
										<input type="text" ${f:text("name")} class="form-control" placeholder="スポット名">
									</div>
									
									<div class="form-group ${f:errorClass('catchCopy','has-error')}">
										<%if (errors.containsKey("catchCopy")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.catchCopy}</label>
										<%}else { %>
										<label for="exampleInputEmail1">キャッチコピー</label>
										<%} %>
										<input type="text" ${f:text("catchCopy")} class="form-control" placeholder="キャッチコピー">
									</div>
									
									<div class="form-group ${f:errorClass('content','has-error')}">
										<%if (errors.containsKey("content")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.content}</label>
										<%}else { %>
										<label for="exampleInputEmail1">スポットの説明</label>
										<%} %>
										<textarea class="form-control" name="content" rows="5" placeholder="特徴・説明"><%=content == null ? "" : content %></textarea>
									</div>
									
								</div><!-- /.box-body -->

								<div class="box-footer">
									<a href="/client/account/addSpotStep1" class="btn btn-default">戻る</a>
									<button type="submit" class="btn btn-primary pull-right">登録</button>
								</div>
							</form>
						</div>
            
					</section>
          
				</div><!-- /.container -->
			</div><!-- /.content-wrapper -->
      
			<jsp:include page="/client/account/include-parts/main_footer.jsp" />
		</div><!-- ./wrapper -->

		<jsp:include page="/client/account/include-parts/html_script.jsp" />
	</body>
</html>

