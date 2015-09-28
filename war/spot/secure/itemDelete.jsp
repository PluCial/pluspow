<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%
String spotId = (String) request.getAttribute("spotId");
String lang = (String) request.getAttribute("lang");
String itemId = (String) request.getAttribute("itemId");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
	<div class="modal-header">
		<h4 class="modal-title"><i class="fa fa-trash"></i> アイテムの削除</h4>
	</div><!-- /modal-header -->
    <form action="/spot/secure/itemDeleteEntry" id="item-delete-submit-form" method="post">
		<div class="modal-body">
			<p>母国語のページからアイテムを削除すると、そのアイテムは他の言語ページからも削除されます。<br />また、一度削除したアイテムは元に戻すことはできません。<br /><br />このアイテムを本当に削除してよろしいですか？</p>
		</div><!-- /modal-body -->
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">キャンセル</button>
			<button id="item-delete-submit-button" type="button" class="btn btn-danger">削除する</button>
		</div>
		<input type="hidden" name="spotId" value="<%=spotId == null ? "" : spotId %>" />
		<input type="hidden" name="lang" value="<%=lang == null ? "" : lang %>" />
		<input type="hidden" name="itemId" value="<%=itemId == null ? "" : itemId %>" />
	</form>
</body>
</html>