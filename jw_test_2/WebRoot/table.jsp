<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath }" />
<!-- JQuery数据表 -->
<div class="row">
	<div class="col-xs-12">
		<h3 class="header smaller lighter blue">用户表</h3>
		<div class="table-header">
			"用户"的结果
		</div>

		<!-- <div class="table-responsive"> -->
		<!-- <div class="dataTables_borderWrap"> -->
		<div>
			<table id="sample-table-2" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
							<label class="position-relative">
								<input type="checkbox" class="ace" />
								<span class="lbl"></span>
							</label>
						</th>
						<th>用户id</th>
						<th>用户名</th>
						<th class="hidden-480">密码</th>

						<th>
							<i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
							手机号码
						</th>
						<th class="hidden-480">用户头像路径</th>

						<th>操作</th>
					</tr>
				</thead>

				<tbody>
					<!-- 1~10条 -->
					<c:forEach items="${users_2 }" var="user">
						<tr>
							<td class="center">
								<label class="position-relative">
									<input type="checkbox" class="ace" />
									<span class="lbl"></span>
								</label>
							</td>
	
							<td>
								<a href="#">${user.id }</a>
							</td>
							<td>${user.username }</td>
							<td class="hidden-480">${user.pwd }</td>
							<td>${user.phone }</td>
	
							<td class="hidden-480">
								<span class="label label-sm label-inverse arrowed-in">${user.user_icon_src }</span>
							</td>
	
							<td>
								<div class="hidden-sm hidden-xs action-buttons">
									<a class="blue" href="#">
										<i class="ace-icon fa fa-search-plus bigger-130"></i>
									</a>
	
									<a class="green" href="#">
										<i class="ace-icon fa fa-pencil bigger-130"></i>
									</a>
	
									<a class="red" href="#">
										<i class="ace-icon fa fa-trash-o bigger-130"></i>
									</a>
								</div>
	
								<!-- 标题（Bootstrap） -->
								<div class="hidden-md hidden-lg">
									<div class="inline position-relative">
										<button class="btn btn-minier btn-yellow dropdown-toggle" data-toggle="dropdown" data-position="auto">
											<i class="ace-icon fa fa-caret-down icon-only bigger-120"></i>
										</button>
	
										<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
											<li>
												<a href="#" class="tooltip-info" data-rel="tooltip" title="查看">
													<span class="blue">
														<i class="ace-icon fa fa-search-plus bigger-120"></i>
													</span>
												</a>
											</li>
	
											<li>
												<a href="#" class="tooltip-success" data-rel="tooltip" title="编辑">
													<span class="green">
														<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
													</span>
												</a>
											</li>
	
											<li>
												<a href="#" class="tooltip-error" data-rel="tooltip" title="删除">
													<span class="red">
														<i class="ace-icon fa fa-trash-o bigger-120"></i>
													</span>
												</a>
											</li>
										</ul>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>