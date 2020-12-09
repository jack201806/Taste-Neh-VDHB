package cn.jbolt.index;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.CPI;

import cn.jbolt.common.model.Comment;
import cn.jbolt.common.model.Manager;
import cn.jbolt.common.model.Orders;
import cn.jbolt.common.model.Product;
import cn.jbolt.common.model.User;

/**
 * IndexController 指向系统访问首页
 * @author jbolt.cn
 * @email 909854136@qq.com
 * @date 2018年11月4日 下午9:02:52
 * 可以直接往这里写方法
 */
public class IndexController extends Controller {
	
	private static HttpServletRequest request;
	private static HttpServletResponse response;
	private static String path,files,files_u,files_p;
	private static File fu,fp;

	/**
	 * 初始化一些全局变量
	 * @throws UnsupportedEncodingException
	 */
	public void init() throws UnsupportedEncodingException {
    	request = getRequest();
    	response = getResponse();
    	request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		//	初始化这些所需文件夹
		path = request.getServletContext().getRealPath("/");
		System.out.println(path);
		files = path + "imgs/";
		files_u = files + "users/";
		files_p = files + "products/";
		fu = new File(files_u);
		fp = new File(files_p);
		if (!fu.exists()) {
			boolean b = fu.mkdirs();
			if (b) {
				System.out.println("已创建文件夹-用户头像");
			}
		}
		if (!fp.exists()) {
			boolean b = fp.mkdirs();
			if (b) {
				System.out.println("已创建文件夹-商品照片");
			}
		}
	}
	
	/**
	 * 首页Action
	 * 要想创建多个文件夹，必须在末尾加入斜杠(/)
	 * @throws UnsupportedEncodingException 
	 */
	public void index() throws UnsupportedEncodingException {
		init();
		if (getSessionAttr("us")==null) {
			render("login.html");
		}else {
			//	初始化用户登录信息
			List<User> users_2 = User.dao.find("select * from user");
			System.out.println("现在搜索到的用户数："+users_2.size());
			setAttr("users_2", users_2);
			getRequest().setAttribute("users_2", users_2);
			renderJsp("index.jsp");
		}
	}

	/**
	 * 后台用户登录
	 */
	public void login() {
    	Manager user = getBean(Manager.class,"u");
    	setAttr("u", user);
    	setSessionAttr("us", user);
    	setAttr("myung_kuk", user.getUsername());
    	List<Manager> users = Manager.dao.find("select * from manager where username = '"
				+user.getUsername()+"' or phone = '"+user.getUsername()+"' and pwd = '"+user.getPwd()+"'");
    	System.out.println(users.size());
    	if (users.size()>0) {
			List<User> users_2 = User.dao.find("select * from user");
			System.out.println("现在搜索到的用户数："+users_2.size());
			setAttr("users_2", users_2);
			getRequest().setAttribute("users_2", users_2);
    		renderJsp("index.jsp");
		}else {
			render("login.html");
		}
	}

    /**
     * 后台用户退出登录
     */
    public void logout() {
    	removeAttr("u");
    	removeSessionAttr("us");
    	render("login.html");
    }
    
    /**
     * 进入商家管理界面
     */
    public void manager_chueh() {
    	List<Manager> managers = Manager.dao.find("select * from manager");
    	System.out.println("现在搜索到的商家数："+managers.size());
    	setAttr("managers_2", managers);
    	renderJsp("managers.jsp");
    }

    /**
     * 进入商品管理界面
     */
    public void product_chua() {
    	List<Product> products = Product.dao.find("select * from product");
    	System.out.println("现在搜到的商品数："+products.size());
    	setAttr("products", products);
    	renderJsp("products.jsp");
    }
    
    /**
     * 进入订单管理界面
     */
    public void order_chua() {
    	List<Orders> orders = Orders.dao.find("select * from orders");
    	System.out.println("现在搜到的订单数："+orders.size());
    	setAttr("orders", orders);
    	renderJsp("orders.jsp");
    }
    
    /**
     * 进入评论管理页面
     */
    public void comment_chueh() {
    	List<Comment> comments = Comment.dao.find("select * from comment");
    	System.out.println("现在搜到的评论数："+comments.size());
    	setAttr("comments", comments);
    	renderJsp("comments.jsp");
    }
    
    /**
     * 安卓端用户登录
     * @throws IOException
     */
    public void android_user_login() throws IOException {
    	init();
		render("安卓用户登录界面"+"<br/>"+"---------------"+"<br/>");
		//	需要使用流的方式接收客户端的数据
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_1 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));

		String success = "登录成功";
		String fail = "登录失败，用户名或密码错误";
		String json_login = reader_1.readLine();
		System.out.println(json_login);
		if (json_login!=null) {
			//	根据指定的json串创建JSONObject对象
			JSONObject object = new JSONObject(json_login);
			//	获取JSONObject对象
			String username = object.getString("username");
			String pwd = object.getString("pwd");
			//	构造出用户对象
			User user = new User();
			user.setUsername(username);
			user.setPwd(pwd);
			List<User> users = User.dao.find("select * from user where username = '"
					+user.getUsername()+"' or phone = '"+user.getUsername()+"' and pwd = '"+user.getPwd()+"'");
	    	System.out.println(users.size());
	    	if (users.size()>0) {
	    		//	一定是CPI.getAttrs(user);<这才是我们想要的json串>
	    		String json_send = new Gson().toJson(CPI.getAttrs(users.get(0)));
	    		int id = users.get(0).getId();
	    		System.out.println(json_send);
	    		System.out.println(success+":"+id);
	    		setAttr("u_"+id, user);
	    		setSessionAttr("us_"+id, user);
	    		renderText(success+":"+json_send);
			}else {
				renderText(fail);
			}
		}else {
			renderText("现在还没有（移动端）登录的用户哦");
		}
    }
    
    /**
     * 安卓端用户注册
     */
    public void android_user_regest() throws IOException{
    	init();
		renderText("安卓用户注册界面"+"<br/>"+"---------------"+"<br/>");
		//	需要使用流的方式接收客户端的数据
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_2 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));

		String success = "注册成功";
		String fail = "注册失败，数据库插入异常";
		String json_regest = reader_2.readLine();
		if (json_regest!=null) {
			//	根据指定的json串创建JSONObject对象
			JSONObject object = new JSONObject(json_regest);
			//	获取JSONObject对象
			String username = object.getString("username");
			String pwd = object.getString("pwd");
			String phone = object.getString("phone");
			String user_icon_src = object.getString("user_icon_src");
			//	创建Json串
			Gson gson = new Gson();
			User user = new User();
			user.setUsername(username);
	    	user.setPwd(pwd);
	    	user.setPhone(phone);
	    	user.setUserIconSrc(user_icon_src);
	    	boolean b = user.save();
	    	if (b) {
	    		List<User> users = new User().dao().find("select * from user");
	    		int id = users.get(0).getId();
	    		user.setId(id);
	    		setAttr("u_"+id, user);
	    		setSessionAttr("us_"+id, user);
				setAttr("regest_icon_src", user_icon_src);
	    		//	获取完id后，才输出json串
		    	String json_send = gson.toJson(CPI.getAttrs(user));
	    		renderText(success+":"+json_send);
			}else {
				renderText(fail);
			}
		}else {
			renderText("现在还没有（移动端）注册的用户哦");
		}
    }
    
    /**
     * 用户退出登录
     * @throws IOException
     */
    public void android_user_logout() throws IOException{
    	init();
		renderText("安卓用户注册界面"+"<br/>"+"---------------"+"<br/>");
		//	需要使用流的方式接收客户端的数据
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_2 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));

		String success = "注销登录成功";
//		String fail = "注销登录失败，请重试";
		String json_logout = reader_2.readLine();
		if (json_logout!=null) {
			//	根据指定的json串创建JSONObject对象
			JSONObject object = new JSONObject(json_logout);
			//	获取JSONObject对象
			int id = object.getInt("id");
	    	removeAttr("u_"+id);
	    	removeSessionAttr("us_"+id);
	    	renderText(success);
		}else {
			renderText("现在还没有（移动端）注销登录的用户哦");
		}
    }
    
    /**
     * 用户头像添加、修改
     * @throws Exception 
     */
    public void android_user_icon() throws Exception{
    	init();
		renderText("安卓用户上传头像界面"+"<br/>"+"---------------"+"<br/>");
    	FileItemFactory factory = new DiskFileItemFactory();
    	ServletFileUpload upload = new ServletFileUpload(factory);
    	try {
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					System.out.println(item.getFieldName()+":"+item.getString());
				}else {
					if (!fu.exists()) {
						fu.mkdir();
					}else {
						String filename = item.getName();
						System.out.println(filename);
						if (filename.contains(".")) {
							//	获取本地输出流
							item.write(new File(fu+"/"+filename));
						}
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
    }
    
    /**
     * 向买家版用户端
     * @throws UnsupportedEncodingException 
     */
    public void android_shop_init() throws UnsupportedEncodingException {
		init();
		renderText("商品浏览界面"+"<br/>"+"---------------"+"<br/>");
		if (!fp.exists()) {
			fp.mkdir();
		}else {
			System.out.println("商品照片文件夹已建立，可以操作");
		}
    }
}