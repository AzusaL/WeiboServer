package com.azusal.sqlutils;

import com.azusal.bean.UsersBean;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {

	//��ÿͻ��˷��������û���������
	public static UsersBean getUsersbean(String jsonobject) {
		UsersBean bean = null;
		String name = null;
		String password = null;
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = null;
		try {
			jsonObject = jsonParser.parse(jsonobject).getAsJsonObject();
			name = jsonObject.get("name").getAsString();
			password = jsonObject.get("password").getAsString();
			bean = new UsersBean(name, password);
		} catch (Exception e) {
		}
		return bean;
	}
}
