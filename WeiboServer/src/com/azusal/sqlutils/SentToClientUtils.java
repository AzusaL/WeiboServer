package com.azusal.sqlutils;

import java.io.File;
import java.util.ArrayList;

import com.azusal.bean.UsersBean;
import com.azusal.bean.WeiboBean;

public class SentToClientUtils extends DbUtils {
	public SentToClientUtils() {
	}

	//���һ��΢������������ӽ��
	public String sentaddweibostatus(WeiboBean bean) {
		conn = super.openDB();
		int resquestcode = insertweibo(bean);
		if (resquestcode == 823) {
			return "success";
		} else {
			return "server error";
		}
	}

	// ��ѯ���û�������΢������
	public ArrayList<WeiboBean> sentallweibotoclient(String name) {
		conn = super.openDB();
		ArrayList<WeiboBean> list = queryweiboontent(name);
		return list;
	}

	// �����û���ͷ��ͼƬ��ַ�浽���ݿ�
	public void udheadimg(String name, String path) {
		conn = super.openDB();
		updateheadimgpath(name, path);
	}

	// ����½������ظ��ͻ���
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

	// ��ע�������ظ��ͻ���
	public String sentregisterstatustoclient(String jsonobject) {
		UsersBean bean = JsonUtils.getUsersbean(jsonobject);
		conn = super.openDB();
		int result = hasname(bean.getName());
		if (result == 0) {
			insert(bean.getName(), bean.getPassword());
			// Ϊ�û�����һ������ͼƬ���ļ���
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
