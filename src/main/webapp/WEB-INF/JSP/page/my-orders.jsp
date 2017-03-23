<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h4 class="text-center">My orders</h4>
<hr />
<table id="orderList" data-page-count="${pageCount}" data-page-number="1" class="table table-bordered">
	<thead>
		<tr>
			<th>Order id</th>
			<th>Date</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty orders }">
			<tr>
				<td colspan="2" class="text-center">Orders not found</td>
			</tr>
		</c:if>
		<jsp:include page="../fragment/order-list.jsp" />
	</tbody>
</table>
<c:if test="${pageCount > 1 }">
	<div class="text-center hidden-print">
		<a class="btn btn-success"	id="loadMoreOrders" href="#">Load more orders</a>
	</div>
</c:if>
