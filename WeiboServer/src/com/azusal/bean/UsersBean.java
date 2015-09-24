package com.azusal.bean;

public class UsersBean {
	private String name;
	private String password;
	private String user_head_img_path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UsersBean(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public String getUser_head_img_path() {
		return user_head_img_path;
	}

	public void setUser_head_img_path(String user_head_img_path) {
		this.user_head_img_path = user_head_img_path;
	}

}
