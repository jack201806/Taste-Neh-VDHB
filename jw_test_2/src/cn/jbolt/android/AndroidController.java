package cn.jbolt.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.jfinal.core.Controller;

import cn.jbolt.common.model.User;

public class AndroidController extends Controller {

    /**
     * 安卓端用户登录
     * @throws IOException
     */
    public void android_user_login() throws IOException {
    	HttpServletRequest request = getRequest();
    	HttpServletResponse response = getResponse();
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		render("安卓用户登录界面"+"<br/>"+"---------------"+"<br/>");
		//	需要使用流的方式接收客户端的数据
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_1 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));

		String success = "登录成功";
		String fail = "登录失败，用户名或密码错误";
		String json_login = reader_1.readLine();
		if (json_login!=null) {
			Gson gson = new Gson();
			User user = gson.fromJson(json_login, User.class);
			List<User> users = User.dao.find("select * from manager where username = '"
					+user.getUsername()+"' or phone = '"+user.getUsername()+"' and pwd = '"+user.getPwd()+"'");
	    	System.out.println(users.size());
	    	if (users.size()>0) {
	    		setAttr("u", user);
	    		setSessionAttr("us", user);
	    		render(success);
			}else {
				render(fail);
			}
		}else {
			render("现在还没有（移动端）登录的用户哦");
		}
    }
    
    /**
     * 安卓端用户注册
     */
    public void android_user_regest() throws IOException{
    	HttpServletRequest request = getRequest();
    	HttpServletResponse response = getResponse();
		render("安卓用户注册界面"+"<br/>"+"---------------"+"<br/>");
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//	需要使用流的方式接收客户端的数据
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_2 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));

		String success = "注册成功";
		String fail = "注册失败，数据库插入异常";
		String json_regest = reader_2.readLine();
		if (json_regest!=null) {
			Gson gson = new Gson();
			User user = gson.fromJson(json_regest, User.class);
			user.set("username", user.getUsername());
	    	user.set("pwd", user.getPwd());
	    	user.set("phone", user.getPhone());
	    	user.set("user_icon_src", user.getUserIconSrc());
	    	boolean b = user.save();
	    	if (b) {
	    		setAttr("u", user);
	    		setSessionAttr("us", user);
	    		render(success);
			}else {
				render(fail);
			}
		}else {
			render("现在还没有（移动端）注册的用户哦");
		}
    }
    
    /**
     * 用户退出登录
     * @throws IOException
     */
    public void android_user_logout() throws IOException{
    	removeAttr("u");
    	removeSessionAttr("us");
    	render("用户已退出登录");
    }
}
