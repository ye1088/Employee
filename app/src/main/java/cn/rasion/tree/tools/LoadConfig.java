package cn.rasion.tree.tools;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class LoadConfig {
	/** 默认配置文件路径 */
	private static final String DEFAULT_CONFIG_FILE_PATH = "." + File.separator + "default" + File.separator
			+ "config.xml";
	/** 配置文件所在文件夹 */
	private static final String CONFIG_DIRS = "." + File.separator + "config";
	/** 配置文件路径 */
	private static final String CONFIG_FILE_PATH = "." + File.separator + "config" + File.separator + "config.xml";

	static {
		loadToolsConfig();// 加载ManageTools类中的静态变量值
	}

	/**
	 * 加载ManageTools类中的静态变量值
	 */
	private static void loadToolsConfig() {
		try {
			/**
			 * SAXReader Document Element引用『dom4j』jar包
			 * 
			 * 读取xml文件操作
			 */
//			SAXReader reader = new SAXReader();
//			Document doc = reader.read(CONFIG_FILE_PATH);// 读取xml文件
//			Element rootEle = doc.getRootElement();// 获取根元素

			/**
			 * btnData 关联文件配置信息 empData 员工文件配置信息
			 */
//			Element btnEle = rootEle.element("btnData");// 获取根元素下名为"btnData"的元素
//			Element empEle = rootEle.element("empData");// 获取根元素下名为"empData"的元素

			ManageTools.Btn_Data_Dir = Utils.appDataDir + File.separator +"Data" +File.separator+"btn";// 为工具类中的 关联文件所在文件夹 赋值
			ManageTools.Btn_Data_Path = Utils.appDataDir + File.separator +"Data"
					+File.separator+"btn"+File.separator + "btndata.dat";// 为工具类中的 关联文件路径 赋值

			ManageTools.Emp_Data_Dir = Utils.appDataDir + File.separator +"employee" +File.separator+"btn";// 为工具类中的 员工文件所在文件夹 赋值

			ManageTools.Id_Len = 20;// 为工具类中的 『编号』 所占字节长度 赋值
			ManageTools.Name_Len = 30;// 为工具类中的 『姓名』 所占字节长度
																									// 赋值
		} catch (Exception e) {
			System.out.println("未找到配置文件……");
			createConfigFile();// 未找到文件则创建配置文件
		}
	}

	/**
	 * 创建配置文件
	 */
	private static void createConfigFile() {
//		File dirs = new File(CONFIG_DIRS);
//		if (!dirs.exists()) {// 判断没有该文件夹
//			System.out.println("未找到配置文件所在文件夹……");
//			dirs.mkdir();// 创建文件夹
//			System.out.println("已创建配置文件所在文件夹！");
//		}
//
		File file = new File(CONFIG_FILE_PATH);
		if (!file.exists()) {// 判断没有该文件
			try {
//				file.createNewFile();// 创建新文件
//
//				System.out.println("已创建配置文件！");
//
//				/**
//				 * 以下复制操作
//				 */
//				BufferedReader reader = new BufferedReader(
//						new InputStreamReader(new FileInputStream(DEFAULT_CONFIG_FILE_PATH)));
//				PrintWriter writer = new PrintWriter(CONFIG_FILE_PATH, "utf-8");
//				String line;
//				while ((line = reader.readLine()) != null) {
//					writer.println(line);
//				}
//				System.out.println("已写入配置信息……");
//				reader.close();// 关闭流
//				writer.close();// 关闭流

				loadToolsConfig();// 写入后加载工具类

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
