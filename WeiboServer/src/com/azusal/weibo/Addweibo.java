package com.azusal.weibo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.azusal.bean.WeiboBean;
import com.azusal.sqlutils.SentToClientUtils;
@MultipartConfig
@WebServlet("/Addweibo")
public class Addweibo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		File file;
		WeiboBean bean = null;
		String name = request.getParameter("username");
		String time = request.getParameter("time");
		String weibocontent = request.getParameter("weibocontent");
		String size = request.getParameter("imgsize");
		int imgsize = Integer.valueOf(size);

		if (name != null) {
			int id = getweibosize(name);
			ArrayList<String> pathlist = new ArrayList<String>();
			file = new File(
					"E:/Myeclipseworkspace/.metadata/.me_tcat7/webapps/Weibo/"
							+ name);
			if (!file.exists()) {
				file.mkdir();
			}
			for (int i = 1; i < imgsize; i++) {
				Part part = request.getPart("weiboimg" + name + i);
				String path = file.getAbsolutePath() + "/"+name + id + i + ".jpg";
				part.write(path);
				pathlist.add("http://azusal.tunnel.mobi/Weibo/" + name + "/"
						+ name + id + i + ".jpg");
			}

			bean = new WeiboBean(name, time, weibocontent, pathlist);
			SentToClientUtils clientUtils = new SentToClientUtils();
			String responseString = clientUtils.sentaddweibostatus(bean);
			PrintWriter pw = response.getWriter();
			pw.write(responseString);
			pw.flush();
			pw.close();
		}
	}

	public int getweibosize(String name) {
		int size;
		SentToClientUtils utils = new SentToClientUtils();
		ArrayList<WeiboBean> allweibolist = utils.sentallweibotoclient(name);
		if (allweibolist == null) {
			size = 0;
		} else {
			size = allweibolist.size();
		}
		return size + 1;
	}
}
