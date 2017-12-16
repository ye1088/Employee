package cn.rasion.tree.mainmenu;

import cn.rasion.tree.tools.*;

public class Start {
	Menu m = new Menu();

	/**
	 * main方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Start().start();// 实例化本类并调用本类开始方法
	}

	/**
	 * 开始方法
	 */
	public void start() {
		loadConfig();// 加载所有配置文件与信息
		m.start();// 调用菜单类开始方法
	}

	/**
	 * 加载配置文件与信息
	 */
	public void loadConfig() {
		new ManageTools();// 实例化管理工具类
	}
}
