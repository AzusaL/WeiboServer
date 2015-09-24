package com.azusal.weibo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.azusal.sqlutils.SentToClientUtils;

@MultipartConfig(location = "E:/Myeclipseworkspace/.metadata/.me_tcat7/webapps/Weibo/users_head_img")
@WebServlet("/UsersHeadimg")
public class UsersHeadimg extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name, path;
		Part part_head_img = request.getPart("img");
		name = request.getParameter("username");
		part_head_img.write(name + ".jpg");
		path = "http://azusal.tunnel.mobi/Weibo/users_head_img/" + name + ".jpg";
		new SentToClientUtils().udheadimg(name, path);
	}
}
