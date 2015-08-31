<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/client/include-parts/html_head.jsp" />
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
			<jsp:include page="/client/include-parts/main_header.jsp" />
      
      
			<div class="content-wrapper">
				<div class="container">

					<section class="content">
						<h3>
							<i class="fa fa-plus"></i> <i class="fa fa-map-marker fa-2x" style="color:#dd4b39;"></i> 新しいスポットの作成
						</h3>
						
						<div class="progress-group">
							<span class="progress-text">現在のステップ</span>
                        	<span class="progress-number"><b>3</b>/3</span>
                        	<div class="progress sm">
                          		<div class="progress-bar progress-bar-green" style="width: 90%"></div>
                        	</div>
                      	</div>
			
						<div class="box box-primary">
                
							<!-- form start -->
							<form action="/client/account/addSpotStep3Entry" method="post">
								<div class="box-body">
									
									<div class="form-group">
										<label for="exampleInputFile">トップページに表示する画像</label>
										<div>
											<div class="btn btn-default btn-file">
												<i class="fa fa-file-image-o"></i> ファイルを選択
												<input type="file" name="attachment">
											</div>
										</div>
										<p class="help-block">幅1400px以上の写真推奨。</p>
									</div>
									
								</div><!-- /.box-body -->

								<div class="box-footer">
									<a href="/client/addSpot/step2" class="btn btn-default">戻る</a>
									<button type="submit" class="btn btn-primary pull-right">登録</button>
								</div>
							</form>
						</div>
            
					</section>
          
				</div><!-- /.container -->
			</div><!-- /.content-wrapper -->
      
			<jsp:include page="/client/include-parts/main_footer.jsp" />
		</div><!-- ./wrapper -->

		<jsp:include page="/client/include-parts/html_script.jsp" />
	</body>
</html>

