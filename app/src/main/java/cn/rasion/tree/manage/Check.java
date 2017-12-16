package cn.rasion.tree.manage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import cn.rasion.tree.personage.Employee;
import cn.rasion.tree.tools.ManageTools;

public class Check extends ManageTools {

	/**
	 * 开始方法
	 */
	public void start() {
		Scanner input = new Scanner(System.in);

		System.out.println("请选择查询方式：1.编号查询 2.姓名查询");
		int switchNum = Integer.parseInt(input.nextLine());
		if (switchNum == 1) {
			System.out.println("请输入编号：");
			String id = input.nextLine();
			checkMode(id);
		} else if (switchNum == 2) {
			System.out.println("请输入姓名：");
			String name = input.nextLine();

			try {
				RandomAccessFile raf = new RandomAccessFile(Btn_Data_Path, "r");
				for (int i = 0; i < Data_Len / One_Data_Len; i++) {
					String[] ids = getBtnDataId(name);
					if (ids[i] != null) {
						String id = ids[i];
						checkMode(id);
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 选择查阅模式
	 * 
	 * @param id
	 */
	public void checkMode(String id) {
		Scanner input = new Scanner(System.in);
		try {
			System.out.println("请输入查询方式：（1.单一查询 2.关联查询）");
			int switchNum = Integer.parseInt(input.nextLine());

			if (switchNum == 1) {

				checkOneEmp(id);// 查阅个人信息
			} else if (switchNum == 2) {
				checkBetweenEmp(id);
			} else {
				System.out.println("没有这个序号哟～");
			}

		} catch (FileNotFoundException e) {
			System.out.println("未找到该用户……");
		} catch (ClassNotFoundException e) {
			System.out.println("这是什么类型？");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关联查询
	 * 
	 * @param id
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void checkBetweenEmp(String id) throws FileNotFoundException, ClassNotFoundException, IOException {
		Employee emp = checkEmp(id);// 获取信息
		System.out.println("--------------上级--------------");
		printInfo(emp.getLeaderId());// 打印上级信息
		System.out.println("--------------本级--------------");
		printInfo(id);// 打印本级信息
		System.out.println("--------------左支--------------");
		printInfo(emp.getLeftUnderId());// 打印左支信息
		System.out.println("--------------右支--------------");
		printInfo(emp.getRightUnderId());// 打印右支信息
	}

	/**
	 * 查询个人信息
	 * 
	 * @param id
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void checkOneEmp(String id) throws FileNotFoundException, ClassNotFoundException, IOException {
		System.out.println("-------------------------------");
		printInfo(id);
	}
}
