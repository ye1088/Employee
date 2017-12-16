package cn.rasion.tree.manage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Scanner;

import cn.rasion.tree.personage.Employee;
import cn.rasion.tree.tools.ManageTools;

public class Add extends ManageTools {
	public void start() throws IOException {
		addEmployee();
	}

	/**
	 * 添加员工方法
	 * 
	 * @throws IOException
	 */
	private void addEmployee() throws IOException {
		Scanner input = new Scanner(System.in);

		System.out.println("请输入用户『编号』：");
		String id = input.nextLine();
		if ("".equals(id)) {// 若输入为空则转换默认值
			id = "None";
		}

		System.out.println("请输入用户『姓名』：");
		String name = input.nextLine();
		if ("".equals(name)) {
			name = "None";
		}

		System.out.println("请输入用户『级别』：");
		String level = input.nextLine();
		if ("".equals(level)) {
			level = "None";
		}

		System.out.println("请输入用户『电话』：");
		String number = input.nextLine();
		if ("".equals(number)) {
			number = "None";
		}

		System.out.println("请输入用户『入职时间』：");
		String hiredate = input.nextLine();
		if ("".equals(hiredate)) {
			hiredate = "None";
		}

		System.out.println("请输入用户『工资』：");
		String salaryStr = input.nextLine();
		int salary;
		if ("".equals(salaryStr)) {
			salary = 0;
		} else {
			salary = Integer.parseInt(salaryStr);
		}

		System.out.println("请输入用户『上级编号』：");
		String leaderId = input.nextLine();
		if ("".equals(leaderId)) {
			leaderId = "None";
		}

		System.out.println("请输入用户『下级左支编号』：");
		String leftUnderId = input.nextLine();
		if ("".equals(leftUnderId)) {
			leftUnderId = "None";
		}

		System.out.println("请输入用户『下级右支编号』：");
		String rightUnderId = input.nextLine();
		if ("".equals(rightUnderId)) {
			rightUnderId = "None";
		}

		System.out.println("请输入用户『会员帐号』：");
		String accountNumber = input.nextLine();
		if ("".equals(accountNumber)) {
			accountNumber = "None";
		}

		System.out.println("请输入用户『账户密码』：");
		String accountPassword = input.nextLine();
		if ("".equals(accountPassword)) {
			accountPassword = "None";
		}

		Emp_Data_Path = Emp_Data_Dir + File.separator + id + ".dat";// 设置员工信息数据文件路径

		File dirs = new File(Emp_Data_Dir);// 检测文件夹是否存在
		if (!dirs.exists()) {// 不存在
			dirs.mkdirs();// 创建所有文件夹
		}

		File file = new File(Emp_Data_Path);// 检测默认文件是否存在
		if (!file.exists()) {// 不存在
			file.createNewFile();// 创建文件
			File noneFile = new File(DEFAULT_NONE_FILE_PATH);// 调用默认文件 以下进行复制操作

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(DEFAULT_NONE_FILE_PATH));

			byte[] b = new byte[(int) noneFile.length()];
			bis.read(b);

			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(Emp_Data_Dir + File.separator + noneFile.getName()));
			bos.write(b);

			bis.close();
			bos.close();
		}

		addBtnDataInfo(id, name);// 关联文件(一个保存了名字和编号的文件，用于检阅方便)添加内容

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

		Employee emp = new Employee(id, name, level, number, hiredate, salary, leaderId, leftUnderId, rightUnderId,
				accountNumber, accountPassword);// 员工类添加所输入的属性

		oos.writeObject(emp);// 写出对象

		System.out.println("添加完成！");
		oos.close();// 关闭流
	}

	/**
	 * 添加关联文件内容
	 * 
	 * @param id
	 * @param name
	 * @throws IOException
	 */
	public void addBtnDataInfo(String id, String name) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(Btn_Data_Path, "rw");

		if (raf.length() == 0) {// 若文件是新文件 则进行默认文件复制
			File defualtBtnFile = new File(DEFAULT_btndata_FILE_PATH);

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(DEFAULT_btndata_FILE_PATH));

			byte[] b = new byte[(int) defualtBtnFile.length()];
			bis.read(b);

			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(Btn_Data_Path));
			bos.write(b);

			bis.close();
			bos.close();
		}

		if (checkSame(raf, id, name) >= 0) {// 检测路入信息重复
			raf.seek(checkSame(raf, id, name));// 将文件指针移动到相同的那条数据前
		} else if (checkEmpty(raf) >= 0) {// 检测信息空槽
			raf.seek(checkEmpty(raf));// 将文件指针移动到空槽数据前
		} else {
			raf.seek(raf.length());// 将文件指针移动至末尾
		}

		raf.write(strBytes(id, Id_Len, "utf-8"));// 写入编号
		raf.write(strBytes(name, Name_Len, "utf-8"));// 写入姓名

		raf.close();// 关闭流
	}

	/**
	 * 检测重复
	 * 
	 * @param raf
	 * @param id
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public long checkSame(RandomAccessFile raf, String id, String name) throws IOException {
		return checkInfo(raf, id + name);// 传入编号+姓名
	}

	/**
	 * 检测信息空槽
	 * 
	 * @throws IOException
	 */
	public long checkEmpty(RandomAccessFile raf) throws IOException {
		return checkInfo(raf, "");// 传入空字符串
	}

	/**
	 * 检测信息
	 * 
	 * @param raf
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public long checkInfo(RandomAccessFile raf, String str) throws IOException {
		raf.seek(0);// 将文件指针移动至文件开始位置

		for (int i = 0; i * One_Data_Len < raf.length(); i++) {// 遍历每一条数据
			if (str.equals(getBtnDataInfo(i))) {// 若传入字符串与此条数据相同
				return i * One_Data_Len;// 返回该条数据的指针位置（long类型）
			}
		}
		return -1;// 没有则返回-1
	}

}
