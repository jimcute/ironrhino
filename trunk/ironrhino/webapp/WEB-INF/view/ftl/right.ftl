<#if request.scheme!='https' && !request.servletPath?string?contains('cart')>
	<@s.action name="cart!facade" namespace="/" executeResult="true" />
<@cache key="product" scope="session" timeToLive="300">
		<div><img src="${base}/pic/${relatedProduct.code}.small.jpg" alt="${relatedProduct.code}" class="product_list" /></div>
		<div><a href="${base}/product/${relatedProduct.code}.html">${relatedProduct.name}</a></div>
		<div><a href="${base}/cart/add/${relatedProduct.code}" class="ajax view" options="{replacement:'cart_items'}">放入购物车</a></div>
</@cache>
</#if>

