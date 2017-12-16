package cn.rasion.tree.manage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;

import cn.rasion.tree.personage.Employee;
import cn.rasion.tree.tools.ManageTools;

public class Edit extends ManageTools {

	/**
	 * 开始方法
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void start() throws FileNotFoundException, ClassNotFoundException, IOException {
		editEmp();
	}

	/**
	 * 编辑员工信息
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void editEmp() throws FileNotFoundException, ClassNotFoundException, IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入员工编号：");
		String id = input.nextLine();

		printInfo(id);// 显示员工信息

		ObjectOutputStream oos = null;

		do {
			System.out.println("请选择需要改动的属性：");
			System.out.println("[0.退出 1.编号 2.姓名 3.级别 4.电话 5.工资 6.入职时间]");
			System.out.println("[7.上级编号 8.左支编号 9.右支编号 10.帐号 11.账户密码]");
			int switchNum = input.nextInt();

			String var = null;

			Employee emp = null;

			if (switchNum > 0) {
				System.out.println("请输入修改后的内容：");
				var = input.next();
				emp = checkEmp(id);// 获取员工对象
			}

			if (switchNum == 0) {
				System.out.println("已退出……");
				break;
			} else if (switchNum == 1) {
				emp.setId(var);// 更改编号
				File file = new File(Emp_Data_Dir + File.separator + id + ".dat");// 获取该信息文件
				file.delete();// 删除原文件
				id = emp.getId();// 将id更改为更新后的id值
			} else if (switchNum == 2) {
				emp.setName(var);// 更改姓名
				editBtnData(emp.getId(), emp.getName());// 修改关联文件信息 传入id和name
			} else if (switchNum == 3) {
				emp.setLevel(var);// 更改级别
			} else if (switchNum == 4) {
				emp.setNumber(var);// 更改电话号
			} else if (switchNum == 5) {
				emp.setSalary(Integer.parseInt(var));// 更改工资
			} else if (switchNum == 6) {
				emp.setHiredate(var);// 更改入职时间
			} else if (switchNum == 7) {
				emp.setLeaderId(var);// 更改上级编号
			} else if (switchNum == 8) {
				emp.setLeftUnderId(var);// 更改左支编号
			} else if (switchNum == 9) {
				emp.setRightUnderId(var);// 更改右支编号
			} else if (switchNum == 10) {
				emp.setAccountNumber(var);// 更改帐号
			} else if (switchNum == 11) {
				emp.setAccountPassword(var);// 更改密码
			} else {
				System.out.println("请输入有效序号！");
			}

			/** 实例化对象输出流 */
			oos = new ObjectOutputStream(new FileOutputStream(Emp_Data_Dir + File.separator + emp.getId() + ".dat"));

			oos.writeObject(emp);// 将新对象写入文件

			printInfo(emp.getId());// 显示修改后的信息
		} while (true);
		if (oos != null) {
			oos.close();
		}
	}

	/**
	 * 修改关联文件信息
	 * 
	 * @param id
	 * @param name
	 * @throws IOException
	 */
	private void editBtnData(String id, String name) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(Btn_Data_Path, "rw");
		long pos = getBtnDataPos(id);// 获取指针位置

		raf.seek(pos + Id_Len);// 移动文件指针到id所在的数据的name前

		raf.write(strBytes(name, Name_Len, "utf-8"));// 写入字符串

		raf.close();
	}

}
