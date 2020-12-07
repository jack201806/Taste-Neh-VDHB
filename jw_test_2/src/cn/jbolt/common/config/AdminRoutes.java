package cn.jbolt.common.config;

import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;

//import cn.jbolt.android.AndroidController;
import cn.jbolt.index.IndexController;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		//针对一组路由配置baseViewPath
		//this.setBaseViewPath("/_view/_admin");
		//针对一组路由配置单独的拦截器
		//this.addInterceptor(new AdminAuthInterceptor());
		//针对后台管理系统配置路由+controller
//		this.add("/admin", IndexController.class,"/index");
		this.add("/", IndexController.class);
		this.add("/android/", IndexController.class, "/");
	}

	public static void main(String[] args) {
		String path = JFinal.me().getContextPath();
		System.out.println(path);
	}
	
}