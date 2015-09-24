package com.azusal.weibo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azusal.sqlutils.SentToClientUtils;

@SuppressWarnings("serial")
public class Register extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/json;charset=UTF-8");
		String requestString, responseString;

		BufferedReader br = new BufferedReader(new InputStreamReader(
				request.getInputStream(), "UTF-8"));
		StringBuffer sb = new StringBuffer("");
		String temp;
		while ((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		requestString = sb.toString();
		if (!requestString.equals("")) {
			SentToClientUtils utils = new SentToClientUtils();
			responseString = utils.sentregisterstatustoclient(requestString);
			PrintWriter pw = response.getWriter();
			pw.write(responseString);
			pw.flush();
			pw.close();
		}
	}

}
