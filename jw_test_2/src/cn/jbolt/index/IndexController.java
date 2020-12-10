package cn.jbolt.index;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import cn.jbolt.common.model.CommentPhotos;
import cn.jbolt.common.model.Manager;
import cn.jbolt.common.model.Orders;
import cn.jbolt.common.model.Product;
import cn.jbolt.common.model.Store;
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
	private static String path,files,files_u,files_p,files_c,files_s;
	private static File fu,fp,fc,fs;

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
		files_c = files + "comments/";
		files_s = files + "stores/";
		fu = new File(files_u);
		fp = new File(files_p);
		fc = new File(files_c);
		fs = new File(files_s);
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
		if (!fc.exists()) {
			boolean b = fc.mkdirs();
			if (b) {
				System.out.println("已创建文件夹-评论照片");
			}
		}
		if (!fs.exists()) {
			boolean b = fs.mkdirs();
			if (b) {
				System.out.println("已创建文件夹-店铺照片");
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
     * 安卓端用户注册
     */
    public void android_user_info() throws IOException{
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
			int id_0 = object.getInt("id");
			String username = object.getString("username");
			String pwd = object.getString("pwd");
			String phone = object.getString("phone");
			String user_icon_src = object.getString("user_icon_src");
			//	创建Json串
			Gson gson = new Gson();
			User user = new User();
			user.setId(id_0);
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
			e.printStackTrace();
		}
    }
    
    /**
     * 向买家版用户端推送商品信息
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
		List<Product> products = Product.dao.find("select * from product");
		String products_info = "";
		for (Product product : products) {
			List<Store> stores = Store.dao.find("select * from store where id = '"+product.getShopId()+"'");
			String store_info = stores.get(0).getName();
			//	用Gson生成json串
			Gson gson = new Gson();
			String json = gson.toJson(CPI.getAttrs(product));
			products_info += json + "*" + store_info + "<br>";
		}
		renderText(products_info);
    }
    
    
    /**
     * 日期转换
     */
    public static Date stringToDate(String time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        Date date = null;
        try {
            date = format.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 向买家版用户端推送订单信息
     */
    public void android_order_init() throws Exception{
    	init();
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_3 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
		String user_info = reader_3.readLine();
		if (user_info!=null) {
			JSONObject object = new JSONObject(user_info);
			int user_id = object.getInt("id");
	    	List<Orders> orders = Orders.dao.find("select * from orders where user_id = '"+user_id+"'");
	    	String orders_info = "";
	    	for (Orders order : orders) {
				List<Product> products = Product.dao.find("select * from product where id = '"+order.getProductId()+"'");
				Product product = products.get(0);
				List<Store> stores = Store.dao.find("select * from store where id = '"+product.getShopId()+"'");
				Store store = stores.get(0);
				Gson gson = new Gson();
				String json = gson.toJson(CPI.getAttrs(order));
				String json_2 = gson.toJson(CPI.getAttrs(product));
				String json_3 = gson.toJson(CPI.getAttrs(store));
				orders_info += json + "*" + json_2 + "*" + json_3 + "<br>";
	    	}
	    	renderText(orders_info);
		}else {
			renderText("现在还没有查询订单的用户哦");
		}
    	
    }
    
    /**
     * 向买家版用户推送每个商品的评论信息
     */
    public void android_comment_init() throws Exception{
    	init();
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_4 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
		String user_info = reader_4.readLine();
		if (user_info!=null) {
			JSONObject object = new JSONObject(user_info);
			int user_id = object.getInt("id");
			List<Comment> comments = Comment.dao.find("select * from comment where user_id = '"+user_id+"'");
			String comments_info = "";
			for (Comment comment : comments) {
				String photos_info = "";
				List<CommentPhotos> photos = CommentPhotos.dao.find("select * from comment_photos where comment_id = '"+comment.getId()+"'");
				for (CommentPhotos photo : photos) {
					photos_info += photo.getPhotoSrc() + ";";
				}
				Gson gson = new Gson();
				String json = gson.toJson(CPI.getAttrs(comment));
				comments_info += json + "*" + photos_info + "<br>";
			}
			renderText(comments_info);
		}else {
			renderText("现在还没有查看商品评论的用户哦");
		}
    }
    
    /**
     * 买家版下单操作
     */
    public void android_post_orders() throws Exception{
    	init();
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_4 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
		String order_info = reader_4.readLine();
		String success = "提交订单成功";
		String fail = "下单失败，请重试";
		if (order_info!=null) {
			//	根据指定的json串创建JSONObject对象
			JSONObject object = new JSONObject(order_info);
			int product_id = object.getInt("product_id");
			int user_id = object.getInt("user_id");
			int amount = object.getInt("amount");
			double total = object.getDouble("total");
			String status = object.getString("status");
			Date time = stringToDate(object.getString("time"));
			//	创建Json串
			Gson gson = new Gson();
			Orders order = new Orders();
			order.setProductId(product_id);
			order.setUserId(user_id);
			order.setAmount(amount);
			order.setTotal(total);
			order.setStatus(status);
			order.setTime(time);
			boolean b = order.save();
			if (b) {
				renderText(success);
			}else {
				renderText(fail);
			}
		}else {
			renderText("现在还没有下单的用户哦");
		}
    }

    /**
     * 买家版
     */
}