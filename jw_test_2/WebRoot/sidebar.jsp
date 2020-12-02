<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="sidebar" class="sidebar responsive" style="background: #fff;">
	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
	</script>
	<!-- /.sidebar-shortcuts -->

	<!-- 导航栏-项目 -->
	<ul class="nav nav-list">
	
		<!-- 仪表盘 -->
		<li class="active">
			<a href="index.html">
				<i class="menu-icon fa fa-tachometer"></i>
				<span class="menu-text"> 仪表盘 </span>
			</a>
			<b class="arrow"></b>
		</li>

		<!-- UI和元素 -->
		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-desktop"></i>
				<span class="menu-text"> UI和元素 </span>

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

		<!-- 表格 -->
		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-list"></i>
				<span class="menu-text"> 表格 </span>

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

		<!-- 表单 -->
		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-pencil-square-o"></i>
				<span class="menu-text"> 表单 </span>

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
		
		<!-- 控件 -->
		<li class="">
			<a href="widgets.html">
				<i class="menu-icon fa fa-list-alt"></i>
				<span class="menu-text"> 控件 </span>
			</a>

			<b class="arrow"></b>
		</li>

		<!-- 日历 -->
		<li class="">
			<a href="calendar.html">
				<i class="menu-icon fa fa-calendar"></i>

				<span class="menu-text">
					日历

					<span class="badge badge-transparent tooltip-error" title="2 Important Events">
						<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
					</span>
				</span>
			</a>

			<b class="arrow"></b>
		</li>

		<!-- 库 -->
		<li class="">
			<a href="gallery.html">
				<i class="menu-icon fa fa-picture-o"></i>
				<span class="menu-text"> 库 </span>
			</a>

			<b class="arrow"></b>
		</li>

		<!-- 更多页面 -->
		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-tag"></i>
				<span class="menu-text"> 更多页面 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="profile.html">
						<i class="menu-icon fa fa-caret-right"></i>
						用户信息
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="inbox.html">
						<i class="menu-icon fa fa-caret-right"></i>
						收件箱
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="pricing.html">
						<i class="menu-icon fa fa-caret-right"></i>
						定价表
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="invoice.html">
						<i class="menu-icon fa fa-caret-right"></i>
						发票
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="timeline.html">
						<i class="menu-icon fa fa-caret-right"></i>
						时间线
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="login.html">
						<i class="menu-icon fa fa-caret-right"></i>
						登录 &amp; 注册
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>

		<!-- 其他页面 -->
		<li class="">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-file-o"></i>

				<span class="menu-text">
					其他页面

					<span class="badge badge-primary">5</span>
				</span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li class="">
					<a href="faq.html">
						<i class="menu-icon fa fa-caret-right"></i>
						常见问题
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="error-404.html">
						<i class="menu-icon fa fa-caret-right"></i>
						404错误
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="error-500.html">
						<i class="menu-icon fa fa-caret-right"></i>
						500错误
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="grid.html">
						<i class="menu-icon fa fa-caret-right"></i>
						网格
					</a>

					<b class="arrow"></b>
				</li>

				<li class="">
					<a href="blank.html">
						<i class="menu-icon fa fa-caret-right"></i>
						空白页
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>
	</ul><!-- /.nav-list -->

	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>

	<script type="text/javascript">
		try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
	</script>
	<!-- <div>
		<p style="font-size: 100;">合作伙伴
		<ul class="osc_logo">
			<li>
				<a target="_blank" href="http://www.oschina.net"><img src="https://www.oschina.net/img/logo_s2.gif" alt="Logo_s2"></a>
			</li>
			<li>
				<a target="_blank" href="http://www.51idc.com/"><img src="/51idc.png" alt="51idc"></a>
			</li>
			<li>
				<a target="_blank" href="http://www.aliyun.com/"><img src="/aliyun_logo.png" alt="Aliyun_logo"></a>
			</li>
		</ul>
	</div> -->
	<!-- <div class="m-order-ad">
		移动端流量引入广告位
		<div class="ad-mobile-entrance" id="J_ad334"><a title="新年开运美妆" target="_blank" href="http://beauty.vip.com?adidx=1&amp;f=ad&amp;adp=334&amp;adid=8576"><img width="140" height="270" alt="新年开运美妆" src="http://a.vpimg3.com/upload/cmc/2015/01/07/172/3c4ad9f633c24ae79e8bc6491e83004020150107-new-140X270.jpg"></a></div>
	</div> -->
</div>