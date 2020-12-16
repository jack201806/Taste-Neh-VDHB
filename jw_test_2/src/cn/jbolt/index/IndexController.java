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

import org.json.JSONArray;
import cn.jbolt.common.model.Comment;
import cn.jbolt.common.model.CommentPhotos;
import cn.jbolt.common.model.Manager;
import cn.jbolt.common.model.Orders;
import cn.jbolt.common.model.Product;
import cn.jbolt.common.model.ProductType;
import cn.jbolt.common.model.Store;
import cn.jbolt.common.model.StorePosition;
import cn.jbolt.common.model.User;
import cn.jbolt.not_models.HotProduct;
import cn.jbolt.not_models.SearchProduct;

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
		//	需要使用流的方式接收客户端的数据
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_1 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));

		String success = "登录成功";
		String fail = "登录失败，用户名或密码错误";
		String json_login = reader_1.readLine();
//		String json_login = request.getParameter("user");
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
     * 向买家版用户提供店铺信息
     */
    public void android_store_init() throws Exception{
    	init();
    	renderText("商家浏览界面");
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_5 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
		String refresh_info = reader_5.readLine();
		List<Store> stores_0 = Store.dao.find("select * from store");
		int sum = stores_0.size();
		if (refresh_info!=null) {
			System.out.println(refresh_info);
			int refresh = Integer.parseInt(refresh_info);
			if (5*refresh<sum) {
				List<Store> stores = Store.dao.find("select * from store limit "+refresh*5+",5");
				String store_info = "[";
				for (Store store : stores) {
					System.out.println("商店地址："+store.getLocation());
					List<StorePosition> positions = StorePosition.dao.find("select * from store_position where id = '"+store.getLocation()+"' ");
					String location = positions.get(0).getName();
					cn.jbolt.not_models.Store store2 
						= new cn.jbolt.not_models.Store(
								store.getId(), store.getSellerId(), store.getScore(),store.getSale(),
								store.getName(), store.getStorePhotoSrc(), location,
								store.getAllowDelivery(), store.getDeliveryCost(),
								store.getStartDeliveryTime(), store.getEndDeliveryTime());
					Gson gson = new Gson();
					String json = gson.toJson(store2);
					store_info += json + ",";
				}
				store_info = store_info.substring(0,store_info.lastIndexOf(","));
				store_info += "]";
				renderText(store_info);
			}else {
				System.out.println("店铺信息已发送完毕");
			}
		}else {
			System.out.println("现在还没有查看店铺信息的用户哦");
		}
    }
    
    /**
     * 查看店铺详情界面
     * 根据店铺id搜索店铺所有商品
     */
    public void android_product_shop() throws Exception{
    	init();
    	renderText("店铺及菜品浏览界面");
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_8 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
		String store_info = reader_8.readLine();
		if (store_info!=null) {
			System.out.println(store_info);
			int store_id = Integer.parseInt(store_info);
			JSONArray array = new JSONArray();
			List<ProductType> types = ProductType.dao.find("select * from product_type");
			for (ProductType type : types) {
				List<Product> products = Product.dao.find("select * from product where shop_id = "+store_id+" and product_type = "+type.getId()+" ");
				JSONObject object = new JSONObject();
				object.put("productType", type.getName());
				JSONArray array_2 = new JSONArray();
				for (Product product : products) {
					JSONObject jObject = null;
					Gson gson2 = new Gson();
					cn.jbolt.not_models.Product product_2 
						= new cn.jbolt.not_models.Product(
								product.getId(), product.getShopId(),
								product.getName(), type.getName(),
								product.getPrice(), product.getSale(),
								product.getProductPhotoSrc(), product.getIntro());
					String json = gson2.toJson(product_2);
					jObject = new JSONObject(json);
					System.out.println(json);
					array_2.put(jObject);
				}
				object.put("content",array_2);
				System.out.println(object.toString());
				array.put(object);
			}
			System.out.println(array.toString());
			renderText(array.toString());
		}else {
			System.out.println("现在还没有浏览店家详情的用户哦");
		}
    }
    
    /**
     * 向买家版用户端推送商品信息
     * @throws UnsupportedEncodingException 
     */
    public void android_product_init() throws UnsupportedEncodingException {
		init();
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
			String json_0 = gson.toJson(product);
			System.out.println(json_0);
			String json = gson.toJson(CPI.getAttrs(product));
			System.out.println(json);
			products_info += json + "*" + store_info + "\n";
		}
		renderText(products_info);
    }
    
    /**
     * 显示热门商品（stars前10的）
     */
    public void android_product_hot() throws Exception{
    	init();
    	renderText("热门商品界面");
    	List<Product> products = Product.dao.find("select * from product order by stars desc limit 10");
		String products_info = "[";
		for (Product product : products) {
			List<Store> stores = Store.dao.find("select * from store where id = '"+product.getShopId()+"'");
			String store_info = stores.get(0).getName();
			HotProduct hp = new HotProduct(
					product.getShopId(), product.getSale(),
					product.getPrice(), product.getName(),
					product.getId(), product.getStars(), product.getProductPhotoSrc(),
					store_info);
			//	用Gson生成json串
			Gson gson = new Gson();
			String json = gson.toJson(hp);
			System.out.println(json);
			products_info += json + ",";
		}
		products_info = products_info.substring(0,products_info.lastIndexOf(","));
		products_info += "]";
		renderText(products_info);
    }
    
    /**
     * 对买家版首页其他4个Fragment显示的商品的显示操作
     * @throws Exception
     */
    public void android_product_types() throws Exception{
    	init();
    	renderText("热门商品界面");
    	//  获取网络输入流
    	InputStream in = request.getInputStream();
    	BufferedReader reader_7 = new BufferedReader(
   				new InputStreamReader(in, "utf-8"));
   		String usr_param = reader_7.readLine();
   		String sql = "";
   		switch(usr_param) {
   			case "canteen":
   				sql = "select * from product where shop_id in (select id from store where location in(select id from store_position where in_out=1))";
   				break;
   			case "guh_pehi":
   				sql = "select * from product where shop_id in (select id from store where location in(select id from store_position where in_out=2))";
   				break;
   			case "odurem_tekin":
   				sql = "select * from product where shop_id in (select id from store where location in(select id from store_position where in_out=4))";
   				break;
   			case "snacks":
   				sql = "select * from product where shop_id in (select id from store where location in(select id from store_position where in_out=3))";
   				break;
   			default:
   				break;
   		}
   		System.out.println(usr_param);
		List<Product> products = Product.dao.find(sql);
		String products_info = "[";
		for (Product product : products) {
			List<Store> stores = Store.dao.find("select * from store where id = '"+product.getShopId()+"'");
			String store_info = stores.get(0).getName();
			HotProduct hp = new HotProduct(
					product.getShopId(), product.getSale(),
					product.getPrice(), product.getName(),
					product.getId(), product.getStars(), product.getProductPhotoSrc(),
					store_info);
			//	用Gson生成json串
			Gson gson = new Gson();
			String json = gson.toJson(hp);
			System.out.println(json);
			products_info += json + ",";
		}
		products_info = products_info.substring(0,products_info.lastIndexOf(","));
		products_info += "]";
		renderText(products_info);
    }
    
    /**
     * 向买家版用户端推送商品信息（搜索）
     */
    public void android_product_search() throws Exception{
    	init();
    	renderText("商家搜索界面");
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_6 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
		String search = reader_6.readLine();
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
		String search_json = "[";
		if (search!=null) {
			System.out.println(search);
			List<Product> list 
				= Product.dao.find("select * from product where id like '%"
									+search+"%' or shop_id like '%"
									+search+"%' or name like '%"
									+search+"%' or price like '%"
									+search+"%' or stars like '%"
									+search+"%' or sale like '%"
									+search+"%' or product_photo_src like '%"
									+search+"%' ");
			if (list.size()!=0) {
				for (Product product : list) {
					List<Store> stores = Store.dao.find("select * from store where id = "+product.getId()+"");
					Store store = stores.get(0);
					SearchProduct sp = new SearchProduct(
							store.getId(),store.getStorePhotoSrc(),
							store.getName(),store.getScore(),
							store.getSale(),store.getAllowDelivery(),
							store.getDeliveryCost(),product.getName(),
							product.getPrice());
					Gson gson = new Gson();
					String str = gson.toJson(sp);
					search_json += str + ",";
				}
				search_json = search_json.substring(0,search_json.lastIndexOf(","));
			}
			search_json += "]";
			if (search_json.equals("[]")) {
				renderText("对不起，未找到相关结果");
			}else {
				renderText(search_json);
			}
		}else {
			System.out.println("现在还没有搜索商品的用户哦");
		}
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
				orders_info += json + "*" + json_2 + "*" + json_3 + "\n";
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
			System.out.println("现在还没有查看商品评论的用户哦");
		}
    }
    
    /**
     * 发送评论
     */
    public void android_send_comments() throws Exception{
    	init();
    	renderText("发送评论界面");
		//	获取网络输入流
		InputStream in = request.getInputStream();
		BufferedReader reader_4 = new BufferedReader(
				new InputStreamReader(in, "utf-8"));
		String comment_info = reader_4.readLine();
		if (comment_info!=null) {
			System.out.println(comment_info);
			JSONObject object = new JSONObject();
			
		}else {
			System.out.println("现在还没有发送评论的用户哦");
		}
//		FileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		try {
//			@SuppressWarnings("unchecked")
//			List<FileItem> items = upload.parseRequest(request);
//			System.out.println(items.size());
//			for (FileItem item : items) {
////				获取非文件属性
//				if (item.isFormField()) {
//					System.out.println(item.getFieldName()+":"+item.getString());
//					if (item.getFieldName().equals("cake_name")) {
//						int id = item.getString("utf-8");
//					}
//					if (item.getFieldName().equals("cake_type")) {
//						String cake_type_info = item.getString();
//						cake_type = Integer.parseInt(cake_type_info);
//					}
//					if (item.getFieldName().equals("cake_size")) {
//						String cake_size_info = item.getString();
//						cake_size = Double.parseDouble(cake_size_info);
//					}
//					if (item.getFieldName().equals("cake_price")) {
//						String cake_price_info = item.getString();
//						cake_price = Double.parseDouble(cake_price_info);
//					}
//				}else {
//					String path = this.getServletContext().getRealPath("/cake_img");
//					cake_photo_src = item.getName();
//					System.out.println(cake_photo_src);
//					if (cake_photo_src.contains(".")) {
//						System.out.println(cake_photo_src);
//						//	获取本地输出流
//						item.write(new File(path+"/"+cake_photo_src));
//					}else {
//						cake_photo_src = "cake_online_default.png";
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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
			System.out.println(order_info);
			//	根据指定的json串创建JSONObject对象
			JSONObject object = new JSONObject(order_info);
			int user_id = object.getInt("userInfo");
			JSONArray array = object.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				int product_id = obj.getInt("productId");
				int product_num = obj.getInt("productNum");
				Orders order = new Orders();
				order.setProductId(product_id);
				order.setUserId(user_id);
				order.setProductNum(product_num);
				order.setStatus("待付款");
				boolean b = order.save();
				if (!b) {
					renderText(fail);
				}
			}
			renderText(success);
		}else {
			renderText("现在还没有下单的用户哦");
		}
    }

}