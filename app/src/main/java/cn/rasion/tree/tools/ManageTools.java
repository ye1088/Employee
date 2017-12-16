package cn.rasion.tree.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import cn.rasion.tree.personage.Employee;

public class ManageTools {
	/** 默认员工 信息 文件路径 */
	public static final String DEFAULT_NONE_FILE_PATH = "." + File.separator + "default" + File.separator + "None.dat";
	/** 默认员工 关联 文件路径 */
	public static final String DEFAULT_btndata_FILE_PATH = "." + File.separator + "default" + File.separator
			+ "btndata.dat";

	public static String Btn_Data_Dir;// 关联信息所在文件夹路径
	public static String Btn_Data_Path;// 关联信息文件路径

	public static String Emp_Data_Dir;// 员工信息文件所在文件夹路径
	public static String Emp_Data_Path;// 员工信息文件路径

	public static int Id_Len;// 编号存储于关联信息文件中所占字节数
	public static int Name_Len;// 姓名储存于关联信息文件中所占字节数

	public static long One_Data_Len;// 关联信息单条数据长度
	public static long Data_Len;// 关联信息文件长度

	static {
		new LoadConfig();// 加载配置类
		loadBetweenData();// 加载关联性数据文件（若文件不存在则创建）
		One_Data_Len = Id_Len + Name_Len;// 在配置类加载完毕后赋值
	}

	/**
	 * 加载关联性数据文件（若文件不存在则创建）
	 */
	private static void loadBetweenData() {
		try {
			System.out.println("Btn_Data_Path : "+Btn_Data_Path);
			RandomAccessFile raf = new RandomAccessFile(Btn_Data_Path, "r");
			Data_Len = raf.length();
			raf.close();
		} catch (FileNotFoundException e) {// 未找到文件
			/**
			 * 以下创建文件与文件夹等
			 */
			try {
				System.out.println("未找到关联性数据文件……");
				File dirs = new File(Btn_Data_Dir);
				if (!dirs.exists()) {
					System.out.println("未发现路径中所包含的文件夹……");
					dirs.mkdirs();
					System.out.println("已创建文件夹！");
				} else {
					System.out.println("文件夹已存在，无需创建……");
				}

				File file = new File(Btn_Data_Path);
				if (!file.exists()) {
					System.out.println("未发现数据文件……");
					file.createNewFile();
					System.out.println("已创建文件!");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读关联数据文件
	 * 
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public static String[] readBtnData(int num) throws IOException {
		String[] info = new String[2];

		RandomAccessFile raf = new RandomAccessFile(Btn_Data_Path, "r");
		raf.seek(num * One_Data_Len);// 移动文件指针至该条数据起始位置

		info[0] = readString(raf, Id_Len, "utf-8");// 读取编号
		info[1] = readString(raf, Name_Len, "utf-8");// 读取姓名

		return info;// 返回数组
	}

	/**
	 * 搜索btndata获取id
	 * 
	 * @param name

	 * @return
	 * @throws IOException
	 */
	public static String[] getBtnDataId(String name) throws IOException {
		int index = 0;// 创建数组下标

		/** 数组长度为数据条数 */
		String[] id = new String[(int) (Data_Len / One_Data_Len)];

		for (int i = 0; i <= Data_Len / One_Data_Len; i++) {
			String[] info = readBtnData(i);// 循环读取数据
			if (name.equals(info[1])) {// 若姓名相同
				id[index] = info[0];// 获取其id
				index++;// 下标增加
			}
		}
		return id;// 最后返回id数组
	}

	/**
	 * 获取整条数据（id+name）
	 * 
	 * @param num
	 * @return
	 * @throws IOException
	 */
	public static String getBtnDataInfo(int num) throws IOException {
		String[] info = readBtnData(num);
		return info[0] + info[1];// 将id与name拼接
	}

	/**
	 * 获取文件指针位置
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public static long getBtnDataPos(String id) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(Btn_Data_Path, "r");

		/**
		 * 遍历文件
		 */
		for (int i = 0; i < Data_Len / One_Data_Len; i++) {
			raf.seek(i * One_Data_Len);// 文件指针移动值每条信息开始位置

			String readId = readString(raf, Id_Len, "utf-8");// 读取文件中id
			if (id.equals(readId)) {// 传入的编号与读取到的编号相同
				return i * One_Data_Len;// 返回该编号所在信息条目起始为值
			}
		}
		return -1;// 未找到相同则返回-1
	}

	/** 获取格式化字符数组 */
	public static byte[] strBytes(String str, int len, String charSet) throws IOException {
		byte[] b = str.getBytes(charSet);
		b = Arrays.copyOf(b, len);
		return b;// 返回数组
	}

	/**
	 * 读字符串
	 * 
	 * @throws IOException
	 */
	public static String readString(RandomAccessFile raf, int len, String charSet) throws IOException {
		byte[] b = new byte[len];// 创建定长数组
		raf.read(b);// 读取文件存入数组
		String str = new String(b, charSet).trim();// 获取数据去前后空白字符
		return str;// 返回字符串
	}

	/**
	 * 搜索员工
	 * 
	 * @param id
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Employee checkEmp(String id) {
		/** 对象输入流 */
        File file = new File(Emp_Data_Dir + File.separator + id + ".dat");
        try{


            ObjectInputStream ois = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(file)));

            Object obj = ois.readObject();// 向下造型 将读出的数据存储与对象

            ois.close();// 关闭流

            return (Employee) obj;// 返回对象
        }catch (Exception e){
            e.printStackTrace();
            return new Employee();
        }
	}

	/**
	 * 打印信息
	 * 
	 * @param id
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String printInfo(String id) throws FileNotFoundException, IOException, ClassNotFoundException {
		Employee emp = checkEmp(id);// 获取对象信息
        StringBuilder sb = new StringBuilder();
        System.out.println("编号：\t\t" + emp.getId() + "\n");
        sb.append("编号：\t\t" + emp.getId() + "\n");
		System.out.println("姓名：\t\t" + emp.getName() + "\n");
        sb.append("姓名：\t\t" + emp.getName() + "\n");
		System.out.println("级别：\t\t" + emp.getLevel() + "\n");
        sb.append("级别：\t\t" + emp.getLevel() + "\n");
		System.out.println("电话：\t\t" + emp.getNumber() + "\n");
        sb.append("电话：\t\t" + emp.getNumber() + "\n");
		System.out.println("工资：\t\t" + emp.getSalary() + "\n");
        sb.append("工资：\t\t" + emp.getSalary() + "\n");
		System.out.println("入职时间：\t" + emp.getHiredate() + "\n");
        sb.append("入职时间：\t" + emp.getHiredate() + "\n");
		System.out.println("上级姓名：\t" + checkEmp(emp.getLeaderId()).getName() +
                "\n\t编号：\t" + emp.getLeaderId() + "\n");

        sb.append("上级姓名：\t" + checkEmp(emp.getLeaderId()).getName() +
                "\n\t编号：\t" + emp.getLeaderId() + "\n");
		System.out.println(
				"下级左支姓名：\t" + checkEmp(emp.getLeftUnderId()).getName() +
                        "\n\t编号：\t" + emp.getLeftUnderId() + "\n");

        sb.append("下级左支姓名：\t" + checkEmp(emp.getLeftUnderId()).getName() +
                "\n\t编号：\t" + emp.getLeftUnderId() + "\n");
		System.out.println(
				"下级右支姓名：\t" + checkEmp(emp.getRightUnderId()).getName() +
                        "\n\t编号：\t" + emp.getRightUnderId() + "\n");

        sb.append("下级右支姓名：\t" + checkEmp(emp.getRightUnderId()).getName() +
                "\n\t编号：\t" + emp.getRightUnderId() + "\n");
		System.out.println("会员帐号：\t" + emp.getAccountNumber() + "\n");
        sb.append("会员帐号：\t" + emp.getAccountNumber() + "\n");
		System.out.println("账户密码：\t" + emp.getAccountPassword());
        sb.append("账户密码：\t" + emp.getAccountPassword());
		System.out.println("-------------------------------");
        sb.append("\n-------------------------------");

        return sb.toString();
	}
}
