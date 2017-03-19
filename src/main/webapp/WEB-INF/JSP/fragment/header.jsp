<%@ page contentType="text/html; UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#ishopNav" aria-expanded="false">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span> 
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/iShop/products">IShop</a>
		</div>
		<div class="collapse navbar-collapse" id="ishopNav">
			<ul id="currentShoppingCart" class="nav navbar-nav navbar-right ${CURRENT_SHOPPING_CART==null ? 'hidden' : ''}">
				<li class="dropdown">
				  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
					<i class="fa fa-shopping-cart" aria-hidden="true"></i> Shopping cart (<span class="total-count">${CURRENT_SHOPPING_CART.totalCount}</span>)
					       <span class="caret"></span></a>
				  <div class="dropdown-menu shopping-cart-desc">
					    Total count: <span class="total-count">${CURRENT_SHOPPING_CART.totalCount}</span><br>
					    Total cost: <span class="total-cost">${CURRENT_SHOPPING_CART.totalCost}</span><br>
					    <a href="/iShop/shopping-cart" class="btn btn-primary btn-block">View cart</a>
				  </div>
				</li>
			</ul>
			<a href="#" class="btn btn-primary navbar-btn navbar-right sign-in">
			<i class="fa fa-facebook-official" aria-hidden="true"></i> Sign in</a>
		</div>
	</div>
</nav>