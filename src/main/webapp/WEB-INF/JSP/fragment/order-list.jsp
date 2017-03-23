<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach var="order" items="${orders }">
	<tr>
		<td><a href="/iShop/order?id=${order.id }">Order # ${order.id }</a></td>
		<td><fmt:formatDate value="${order.created }" pattern="dd-MM-yyyy HH:mm"/></td>
	</tr>
</c:forEach>