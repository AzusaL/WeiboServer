package com.azusal.sqlutils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.azusal.bean.WeiboBean;
import com.mysql.jdbc.Connection;

public abstract class DbUtils {
	private static final int CONNECT_ERROR = 404;
	private static final int SUCCESS = 823;
	public Connection conn = null;

	public Connection openDB() {
		try {
			if (conn == null) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = (Connection) DriverManager
						.getConnection("jdbc:mysql://localhost:3306/Me?"
								+ "user=root&password=root");
			} else if (conn.isClosed() == true) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = (Connection) DriverManager
						.getConnection("jdbc:mysql://localhost:3306/Me?"
								+ "user=root&password=root");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void closeDB() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int JdugeUsers(String name, String password) {
		conn = openDB();
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		String sql = "select password from users where name=?";
		try {
			if (conn != null) {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.setString(1, name);
				rs = prepareStatement.executeQuery();
				if (!rs.next()) {
					return 0; // 用户不存在
				} else {
					if (rs.getString("password").equals(password)) {
						return 1; // 验证成功
					} else {
						return -1; // 密码错误
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
			try {
				prepareStatement.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CONNECT_ERROR; // 数据库连接出错
	}

	// 将新用户的用户名和密码插入数据库
	public void insert(String name, String password) {
		PreparedStatement prepareStatement = null;
		String sql = "insert into users (name,password) values(?,?)";
		conn = openDB();
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, name);
			prepareStatement.setString(2, password);
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
			try {
				prepareStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateheadimgpath(String name, String path) {
		PreparedStatement prepareStatement = null;
		String sql = "update users set head_img_path=? where name=?";
		conn = openDB();
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, path);
			prepareStatement.setString(2, name);
			prepareStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
			try {
				prepareStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 注册时判断用户名是否已经存在
	public int hasname(String name) {
		conn = openDB();
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		String sql = "select password from users where name=?";
		try {
			if (conn != null) {
				prepareStatement = conn.prepareStatement(sql);
				prepareStatement.setString(1, name);
				rs = prepareStatement.executeQuery();
				if (!rs.next()) {
					return 0; // 该用户名未被使用
				} else {
					return 1; // 该用户名已经被使用
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
			try {
				prepareStatement.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CONNECT_ERROR; // 数据库连接出错
	}

	// 往数据库中的该用户添加一条微博
	public int insertweibo(WeiboBean bean) {
		PreparedStatement prepareStatement = null;
		String sql = "insert into weibo (user_name,time,text_content,allimg_url) values(?,?,?,?)";
		conn = openDB();
		ArrayList<String> path = bean.getPhotourls();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < path.size(); i++) {
			sb.append(path.get(i));
			if (i != path.size() - 1) {
				sb.append(",");
			}
		}
		String allimg_url = null;
		if (path.size() != 0) {
			allimg_url = sb.toString();
		}
		try {
			prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, bean.getName());
			prepareStatement.setString(2, bean.getTime());
			prepareStatement.setString(3, bean.getContent());
			prepareStatement.setString(4, allimg_url);
			prepareStatement.executeUpdate();
			return SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDB();
			try {
				prepareStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CONNECT_ERROR;
	}

	// 查询该用户所有微博
	public ArrayList<WeiboBean> queryweiboontent(String name) {
		conn = openDB();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ArrayList<WeiboBean> list = new ArrayList<WeiboBean>();
		String sql = "select *from weibo where user_name=? order by id desc";
		try {
			if (conn != null) {
				preparedStatement = conn.prepareStatement(sql);
				preparedStatement.setString(1, name);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					ArrayList<String> allimg_url = new ArrayList<String>();
					String i = resultSet.getString("allimg_url");
					if (i != null) {
						String[] array = i.split(",");
						for (int j = 0; j < array.length; j++) {
							allimg_url.add(array[j]);
						}
					}
					WeiboBean bean = new WeiboBean(resultSet.getInt("id"),
							name, resultSet.getString("time"),
							resultSet.getString("text_content"), allimg_url);
					list.add(bean);
				}
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			closeDB();
			try {
				preparedStatement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
