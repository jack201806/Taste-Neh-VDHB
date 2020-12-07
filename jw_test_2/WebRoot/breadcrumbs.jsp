<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!-- 面包屑 -->
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
	</script>

	<ul class="breadcrumb">
		<li>
			<i class="ace-icon fa fa-home home-icon"></i>
			<a href="#">首页</a>
		</li>
		<li class="active">
			<c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('index') }">
				主页面
			</c:if>
			<c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('login') }">
				主页面
			</c:if>
			<c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('manager_chueh') }">
				商家管理
			</c:if>
			<c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('product_chua') }">
				商品管理
			</c:if>
			<c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('order_chua') }">
				订单管理
			</c:if>
			<c:if test="${requestScope['javax.servlet.forward.request_uri'].contains('comment_chueh') }">
				评论管理
			</c:if>
		</li>
		<li>
			
		</li>
	</ul><!-- /.breadcrumb -->

	<div class="nav-search" id="nav-search">
		<form class="form-search">
			<span class="input-icon">
				<input type="text" placeholder="搜索 ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
				<i class="ace-icon fa fa-search nav-search-icon"></i>
			</span>
		</form>
	</div><!-- /.nav-search -->
</div>