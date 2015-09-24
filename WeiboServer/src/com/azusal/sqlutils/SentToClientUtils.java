package com.azusal.sqlutils;

import java.io.File;
import java.util.ArrayList;

import com.azusal.bean.UsersBean;
import com.azusal.bean.WeiboBean;

public class SentToClientUtils extends DbUtils {
	public SentToClientUtils() {
	}

	//添加一条微博，并返回添加结果
	public String sentaddweibostatus(WeiboBean bean) {
		conn = super.openDB();
		int resquestcode = insertweibo(bean);
		if (resquestcode == 823) {
			return "success";
		} else {
			return "server error";
		}
	}

	// 查询该用户的所有微博数据
	public ArrayList<WeiboBean> sentallweibotoclient(String name) {
		conn = super.openDB();
		ArrayList<WeiboBean> list = queryweiboontent(name);
		return list;
	}

	// 将该用户的头像图片地址存到数据库
	public void udheadimg(String name, String path) {
		conn = super.openDB();
		updateheadimgpath(name, path);
	}

	// 将登陆结果返回给客户端
	public String sentresulttoclient(String jsonobject) {
		UsersBean bean = JsonUtils.getUsersbean(jsonobject);
		conn = super.openDB();
		int result = JdugeUsers(bean.getName(), bean.getPassword());
		if (result == 1) {
			return "login success";
		} else if (result == 0) {
			return "user no be found";
		} else if (result == -1) {
			return "password error";
		} else if (result == 404) {
			return "server error";
		}
		return null;
	}

	// 将注册结果返回给客户端
	public String sentregisterstatustoclient(String jsonobject) {
		UsersBean bean = JsonUtils.getUsersbean(jsonobject);
		conn = super.openDB();
		int result = hasname(bean.getName());
		if (result == 0) {
			insert(bean.getName(), bean.getPassword());
			// 为用户创建一个保存图片的文件夹
			File file = new File(
					"E:/Myeclipseworkspace/.metadata/.me_tcat7/webapps/Weibo/"
							+ bean.getName());
			if (!file.exists()) {
				file.mkdir();
			}
			return "success";
		} else if (result == 1) {
			return "this name has bean registered";
		} else if (result == 404) {
			return "server error";
		}
		return null;
	}
}
