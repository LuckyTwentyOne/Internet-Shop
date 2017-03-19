<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="iShop" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="productList" data-page-count="${pageCount}" data-page-number="1">
	<div class="row">
		<jsp:include page="../fragment/product-list.jsp" />
	</div>
	<c:if test="${pageCount > 1 }">
		<div class="text-center hidden-print">
				 <a class="btn btn-success"	id="loadMore" href="#">Load more products</a>
		</div>
	</c:if>
</div>
<iShop:add-product-popup />
