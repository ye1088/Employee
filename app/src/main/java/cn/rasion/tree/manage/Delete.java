package cn.rasion.tree.manage;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import cn.rasion.tree.tools.ManageTools;

public class Delete extends ManageTools {

	/**
	 * 开始方法
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		deleteEmp();
	}

	/**
	 * 删除员工
	 * 
	 * @throws IOException
	 */
	private void deleteEmp() throws IOException {
		Scanner input = new Scanner(System.in);

		System.out.println("请输入编号：");
		String id = input.nextLine();

		System.out.println("确认删除？ 输入『yes』确认删除");
		String del = input.next();

		if ("yes".equals(del)) {// 判断是否确认删除
			File file = new File(Emp_Data_Dir + File.separator + id + ".dat");// 获取文件
			if (file.exists()){
                file.delete();// 删除员工信息文件


                /** 以下将关联信息文件中该条信息释放为空槽 */
                RandomAccessFile raf = new RandomAccessFile(Btn_Data_Path, "rw");

                long pos = getBtnDataPos(id);

                raf.seek(pos);

                raf.write(strBytes("", (int) One_Data_Len, "utf-8"));

                raf.close();
                System.out.println("员工已删除！");
            }


		} else {
			System.out.println("程序已退出……");
		}
	}
}
