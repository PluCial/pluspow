<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@ page import="org.slim3.controller.validator.Errors" %>
<%
Errors errors =(Errors) request.getAttribute("errors");
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
                        	<span class="progress-number"><b>1</b>/2</span>
                        	<div class="progress sm">
                          		<div class="progress-bar progress-bar-green" style="width: 0%"></div>
                        	</div>
                      	</div>
			
						<div class="box box-primary">
                
							<!-- form start -->
							<form action="/client/account/addSpotStep1Entry" method="post">
								<div class="box-body">
								
									<div class="form-group ${f:errorClass('spotId','has-error')}">
										<%if (errors.containsKey("spotId")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.spotId}</label>
										<%}else { %>
										<label for="exampleInputEmail1">スポット ID</label>
										<%} %>
										<div class="input-group">
											<span class="input-group-addon" style="color: #333;"><b>https://pluspow.com/+</b></span>
											<input type="text" ${f:text("spotId")} class="form-control" placeholder="スポットID">
										</div>
									</div>
									
									<div class="form-group ${f:errorClass('address','has-error')}">
										<%if (errors.containsKey("address")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.address}</label>
										<%}else { %>
										<label for="exampleInputEmail1">住所</label>
										<%} %>
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
											<input type="text" ${f:text("address")} class="form-control" placeholder="東京都杉並区・・・">
										</div>
									</div>
									
									<div class="form-group ${f:errorClass('phoneNumber','has-error')}">
										<%if (errors.containsKey("phoneNumber")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.phoneNumber}</label>
										<%}else { %>
										<label for="exampleInputEmail1">電話番号</label>
										<%} %>
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-phone"></i></span>
											<input type="text" ${f:text("phoneNumber")} class="form-control" placeholder="092-1111-1111">
										</div>
									</div>
								
									<div class="form-group ${f:errorClass('email','has-error')}">
										<%if (errors.containsKey("phoneNumber")){ %>
										<label class="control-label" for="inputError"><i class="fa fa-times-circle-o"></i> ${errors.email}</label>
										<%}else { %>
										<label for="exampleInputEmail1">メールアドレス(お問い合わせ用)</label>
										<%} %>
										<div class="input-group">
											<span class="input-group-addon"><i class="fa fa-envelope"></i></span>
											<input type="email" ${f:text("email")} class="form-control" placeholder="メールアドレス">
										</div>
									</div>
                    
									<div class="checkbox">
										<label>
											<input type="checkbox" ${f:checkbox("owner")}> 私はこのスポットの所有者です。
										</label>
									</div>
									<%if (errors.containsKey("owner")){ %>
									<div class="callout callout-danger">
										<h4><i class="icon fa fa-warning"></i></h4>
										<p>${errors.owner}</p>
									</div>
									<%} %>
								</div><!-- /.box-body -->

								<div class="box-footer">
									<a href="/client/account/selectSpot" class="btn btn-default">戻る</a>
									<button type="submit" class="btn btn-primary pull-right">次へ</button>
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

