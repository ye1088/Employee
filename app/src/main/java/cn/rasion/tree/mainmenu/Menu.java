package cn.rasion.tree.mainmenu;

import android.app.Activity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import cn.rasion.tree.manage.Add;
import cn.rasion.tree.manage.Check;
import cn.rasion.tree.manage.Delete;
import cn.rasion.tree.manage.Edit;

public class Menu {
	public void start() {
		mainMenu();
	}

	/**
	 * 主菜单
	 */
	public void mainMenu() {
		Scanner input = new Scanner(System.in);

		try {
			System.out.println("-------主菜单-------");
			System.out.println("1. 添加员工");
			System.out.println("2. 查找员工");
			System.out.println("3. 修改信息");
			System.out.println("4. 删除员工");
			System.out.println("0. 退出");
			System.out.println("-------------------");
			System.out.println("请选择：");
			int switchNum = input.nextInt();// 接收用户输入信息

			if (switchNum == 1) {
				new Add().start();// 添加员工类 开始方法
			} else if (switchNum == 2) {
				new Check().start();// 检阅员工类 开始方法
			} else if (switchNum == 3) {
				new Edit().start();// 编辑员工类 开始方法
			} else if (switchNum == 4) {
				new Delete().start();// 删除员工类 开始方法
			} else if (switchNum == 0) {
				input.close();// 关闭流
				System.out.println("退出～");
			} else {
				System.out.println("请输入正确编号！");
			}
		} catch (FileNotFoundException e) {
			System.out.println("查无此人……");
		} catch (ClassNotFoundException e) {
			System.out.println("这是什么类型？");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
