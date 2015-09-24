package com.azusal.weibo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azusal.bean.WeiboBean;
import com.azusal.sqlutils.SentToClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetWeibo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String requestusername;

		ObjectMapper objectMapper = new ObjectMapper();
		requestusername = objectMapper.readValue(
				new InputStreamReader(request.getInputStream(), "utf-8"),
				String.class);

		if (!requestusername.equals("")) {
			SentToClientUtils clientUtils = new SentToClientUtils();
			ArrayList<WeiboBean> list = clientUtils
					.sentallweibotoclient(requestusername);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(new OutputStreamWriter(
					response.getOutputStream(), "utf-8"), list);
		}
	}

}
