<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" name="viewport"
			content="width=device-width, initial-scale=1">
		<title>IShop</title>
		<link rel="stylesheet" href="/iShop/static/css/bootstrap.css">
		<link rel="stylesheet" href="/iShop/static/css/bootstrap-theme.css">
		<link rel="stylesheet" href="/iShop/static/css/font-awesome.css">
		<link rel="stylesheet" href="/iShop/static/css/app.css">
	</head>
<body>
	<header>
		<jsp:include page="fragment/header.jsp" />
	</header>
	<div class="container-fluid">
		<div class="row">
			<aside class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
				<jsp:include page="fragment/aside.jsp" />
			</aside>
			<main class="col-xs-12 col-sm-8 col-md-9 col-lg-10">
			 	<jsp:include page="${currentPage }" />
			</main>
		</div>
	</div>
	<footer class="footer">
		<jsp:include page="fragment/footer.jsp" />
	</footer>
	<script src="/iShop/static/js/jquery.js"></script>
	<script src="/iShop/static/js/bootstrap.js"></script>
	<script src="/iShop/static/js/app.js"></script>
</body>
</html>