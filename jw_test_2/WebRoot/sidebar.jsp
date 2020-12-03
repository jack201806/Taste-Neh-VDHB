<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sidebar" class="sidebar responsive" style="background: #fff;">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>
	<!-- /.sidebar-shortcuts -->

	<!-- 导航栏-项目 -->
	<ul class="nav nav-list">
		
		<!-- 主页面 -->
		<li class="active">
			<a href="index.html">
				<i class="menu-icon fa fa-home"></i>
				<span class="menu-text"> 主页面 </span>
			</a>
			<b class="arrow"></b>
		</li>
	
		<!-- 用户管理 -->
		<li class="">
			<a href="index.html">
				<i class="menu-icon fa fa-user"></i>
				<span class="menu-text"> 用户管理 </span>
			</a>
			<b class="arrow"></b>
		</li>

		<!-- 商家管理 -->
		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-cutlery"></i>
				<span class="menu-text"> 商家管理 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-caret-right"></i>
						布局
						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="top-menu.html">
								<i class="menu-icon fa fa-caret-right"></i>
								一级菜单
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="mobile-menu-1.html">
								<i class="menu-icon fa fa-caret-right"></i>
								默认菜单（移动版）
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="mobile-menu-2.html">
								<i class="menu-icon fa fa-caret-right"></i>
								默认菜单2（移动版）
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="mobile-menu-3.html">
								<i class="menu-icon fa fa-caret-right"></i>
								默认菜单2（移动版）
							</a>

							<b class="arrow"></b>
						</li>
					</ul>
				</li>

				<li class="">
					<a href="typography.html">
						<i class="menu-icon fa fa-caret-right"></i>
						排版
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="elements.html">
						<i class="menu-icon fa fa-caret-right"></i>
						元素
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="buttons.html">
						<i class="menu-icon fa fa-caret-right"></i>
						按钮和图标
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="treeview.html">
						<i class="menu-icon fa fa-caret-right"></i>
						Treeview
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="jquery-ui.html">
						<i class="menu-icon fa fa-caret-right"></i>
						jQuery UI
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="nestable-list.html">
						<i class="menu-icon fa fa-caret-right"></i>
						Nestable表
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="#" class="dropdown-toggle">
						<i class="menu-icon fa fa-caret-right"></i>
						三级菜单
						<b class="arrow fa fa-angle-down"></b>
					</a>

					<b class="arrow"></b>

					<ul class="submenu">
						<li class="">
							<a href="#">
								<i class="menu-icon fa fa-leaf"></i>
								项目 #1
							</a>

							<b class="arrow"></b>
						</li>

						<li class="">
							<a href="#" class="dropdown-toggle">
								<i class="menu-icon fa fa-pencil"></i>
								四级
								<b class="arrow fa fa-angle-down"></b>
							</a>

							<b class="arrow"></b>

							<ul class="submenu">
								<li class="">
									<a href="#">
										<i class="menu-icon fa fa-plus"></i>
										添加产品
									</a>

									<b class="arrow"></b>
								</li>

								<li class="">
									<a href="#">
										<i class="menu-icon fa fa-eye"></i>
										浏览产品
									</a>

									<b class="arrow"></b>
								</li>
							</ul>
						</li>
					</ul>
				</li>
			</ul>
		</li>

		<!-- 所有商品 -->
		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-shopping-cart"></i>
				<span class="menu-text"> 所有商品 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="tables.html">
						<i class="menu-icon fa fa-caret-right"></i>
						简单和动态表格
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="jqgrid.html">
						<i class="menu-icon fa fa-caret-right"></i>
						jqGrid插件
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>

		<!-- 订单管理 -->
		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-list-alt"></i>
				<span class="menu-text"> 订单管理 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="form-elements.html">
						<i class="menu-icon fa fa-caret-right"></i>
						表单元素
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="form-wizard.html">
						<i class="menu-icon fa fa-caret-right"></i>
						向导和验证
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="wysiwyg.html">
						<i class="menu-icon fa fa-caret-right"></i>
						可见即可得、Markdown
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="dropzone.html">
						<i class="menu-icon fa fa-caret-right"></i>
						Dropzone文件上传
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
	</ul> <!-- /.nav-list -->

	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
	</script>
</div>