package com.google.employee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

import cn.rasion.tree.personage.Employee;

import cn.rasion.tree.tools.ManageTools;

/**
 * Created by Administrator on 2017/12/16.
 */

public class AddActivity extends Activity {

    EditText et_id;
    EditText et_name;
    EditText et_level;
    EditText et_tel;
    EditText et_time;
    EditText et_salary;
    EditText et_sjbh;
    EditText et_xjzzbh;
    EditText et_xjyzbh;
    EditText et_account;
    EditText et_passward;
    boolean isModIntent = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        Intent intent = this.getIntent();
        init();

//        Employee employee = (Employee)
        Employee employee = (Employee)intent.getSerializableExtra("employee");
        if (employee != null){
            isModIntent = true;
            init(employee);
        }


    }

    private void init(Employee employee) {
        et_id.setText(employee.getId());
        et_name.setText(employee.getName());
        et_level.setText(employee.getLevel());
        et_tel.setText(employee.getNumber());
        et_time.setText(employee.getHiredate());
        et_salary.setText(employee.getSalary()+"");
        et_sjbh.setText(employee.getLeaderId());
        et_xjzzbh.setText(employee.getLeftUnderId());
        et_xjyzbh.setText(employee.getRightUnderId());
        et_account.setText(employee.getAccountNumber());
        et_passward.setText(employee.getAccountPassword());
    }

    private void init() {
        et_id = (EditText) findViewById(R.id.id);
        et_name = (EditText) findViewById(R.id.name);
        et_level = (EditText) findViewById(R.id.level);
        et_tel = (EditText) findViewById(R.id.tel);
        et_time = (EditText) findViewById(R.id.enter_time);
        et_salary = (EditText) findViewById(R.id.salary);
        et_sjbh = (EditText) findViewById(R.id.sjbh);
        et_xjzzbh = (EditText) findViewById(R.id.xjzzbh);
        et_xjyzbh = (EditText) findViewById(R.id.xjyzbh);
        et_account = (EditText) findViewById(R.id.account);
        et_passward = (EditText) findViewById(R.id.passward);


    }


    public void add_data(View v){
        switch (v.getId()){
            case R.id.bt_add_data:
                try {
                    addEmployee();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void addEmployee() throws IOException {
        String id = et_id.getText().toString();
        String name = et_name.getText().toString();
        String level = et_level.getText().toString();
        String number = et_tel.getText().toString();
        String hiredate = et_time.getText().toString();
        String salaryStr = et_salary.getText().toString();
        String leaderId = et_sjbh.getText().toString();
        String leftUnderId = et_xjzzbh.getText().toString();
        String rightUnderId = et_xjyzbh.getText().toString();
        String accountNumber = et_account.getText().toString();
        String accountPassword = et_passward.getText().toString();

        if (TextUtils.isEmpty(id)|| TextUtils.isEmpty(name)){
            Toast.makeText(this,"id 和 姓名不能为空!!",Toast.LENGTH_SHORT).show();
            return;
        }



        ManageTools.Emp_Data_Path = ManageTools.Emp_Data_Dir + File.separator + id + ".dat";// 设置员工信息数据文件路径

        File dirs = new File(ManageTools.Emp_Data_Dir);// 检测文件夹是否存在
        if (!dirs.exists()) {// 不存在
            dirs.mkdirs();// 创建所有文件夹
        }

        File file = new File(ManageTools.Emp_Data_Path);// 检测默认文件是否存在
        if (!file.exists()) {// 不存在
            file.createNewFile();// 创建文件
            File noneFile = new File(ManageTools.DEFAULT_NONE_FILE_PATH);// 调用默认文件 以下进行复制操作

            InputStream btndata = this.getAssets().open("btndata.dat");
            BufferedInputStream bis = new BufferedInputStream(btndata);

            byte[] b = new byte[ btndata.available()];
            bis.read(b);

            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(ManageTools.Emp_Data_Dir + File.separator + noneFile.getName()));
            bos.write(b);

            bis.close();
            bos.close();
        }

        addBtnDataInfo(id, name);// 关联文件(一个保存了名字和编号的文件，用于检阅方便)添加内容

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

        Employee emp = new Employee(id, name, level, number, hiredate, salaryStr, leaderId, leftUnderId, rightUnderId,
                accountNumber, accountPassword);// 员工类添加所输入的属性

        oos.writeObject(emp);// 写出对象

        Toast.makeText(this,"添加完成！",Toast.LENGTH_SHORT).show();
        oos.close();// 关闭流

        finish();
    }

    /**
     * 添加关联文件内容
     *
     * @param id
     * @param name
     * @throws IOException
     */
    public void addBtnDataInfo(String id, String name) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ManageTools.Btn_Data_Path, "rw");

        if (raf.length() == 0) {// 若文件是新文件 则进行默认文件复制
//            File defualtBtnFile = new File(ManageTools.DEFAULT_btndata_FILE_PATH);

            InputStream btndata = this.getAssets().open("btndata.dat");

            BufferedInputStream bis = new BufferedInputStream(btndata);

            byte[] b = new byte[(int) btndata.available()];
            bis.read(b);

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(ManageTools.Btn_Data_Path));
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

        raf.write(ManageTools.strBytes(id, ManageTools.Id_Len, "utf-8"));// 写入编号
        raf.write(ManageTools.strBytes(name, ManageTools.Name_Len, "utf-8"));// 写入姓名

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

        for (int i = 0; i * ManageTools.One_Data_Len < raf.length(); i++) {// 遍历每一条数据
            if (str.equals(ManageTools.getBtnDataInfo(i))) {// 若传入字符串与此条数据相同
                return i * ManageTools.One_Data_Len;// 返回该条数据的指针位置（long类型）
            }
        }
        return -1;// 没有则返回-1
    }
}
