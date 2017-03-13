<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="iShop" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div id="productList">
	<jsp:include page="../fragment/product-list.jsp" />
	<div class="text-center hidden-print">
			<img id="loadMoreIndicator" src="static/img/loading.gif"
				class="hidden" alt="Loading..."> <a class="btn btn-success"
				id="loadMore" href="#">Load more products</a>
	</div>
</div>
<iShop:add-product-popup />
