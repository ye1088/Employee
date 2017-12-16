package com.google.employee;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import cn.rasion.tree.personage.Employee;

import cn.rasion.tree.tools.ManageTools;
import cn.rasion.tree.tools.Utils;

/**
 * Created by Administrator on 2017/12/16.
 */

public class FindActivity extends Activity {

    EditText find_id_et;
    EditText find_name_et;
    RadioGroup rg_mode_sel;
    boolean isSingleMode = true;
    boolean isModIntent = false;
    TextView result_find_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_layout);
        Intent intent = this.getIntent();
        String action = intent.getStringExtra("action");
        if (action != null) {
            if (action.equals("mod")){
                isModIntent = true;
                Toast.makeText(this,"请先查找出要修改的员工",Toast.LENGTH_LONG).show();
            }
        }

        init();
//
//        if (isModIntent){
//            find_name_et.setFocusable(false);
//        }
    }

    private void init() {
        find_id_et = (EditText) findViewById(R.id.find_id_et);
        find_name_et = (EditText) findViewById(R.id.find_name_et);
        rg_mode_sel = (RadioGroup) findViewById(R.id.rg_mode_sel);
        result_find_tv = (TextView) findViewById(R.id.find_result_tv);
        rg_mode_sel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.single_rb){
                    isSingleMode = true;
                }else {
                    isSingleMode = false;
                }
            }
        });
    }


    public void start_find(View v){
        switch (v.getId()){
            case R.id.start_find_bt:
                start();
                break;
            case R.id.find_exit_bt:
                finish();
                break;
        }
    }


    /**
     * 开始方法
     */
    public void start() {

        String id = find_id_et.getText().toString();
        String name = find_name_et.getText().toString();
        if (!"".equals(id)){
            checkMode(id);
        }else if (!"".equals(name)){

            try {
                boolean hasFind = false;
                RandomAccessFile raf = new RandomAccessFile(ManageTools.Btn_Data_Path, "r");
                for (int i = 0; i < ManageTools.Data_Len / ManageTools.One_Data_Len; i++) {
                    String[] ids = ManageTools.getBtnDataId(name);
                    if (ids[i] != null) {
                        checkMode(ids[i]);
                        hasFind = true;
                        break;
                    }
                }
                if (!hasFind){
                    Utils.showToast(this,"没有这个人,请重查");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(this,"编号和姓名不能全为空!!",Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 选择查阅模式
     *
     * @param id
     */
    public void checkMode(String id) {

        try {
            if (isModIntent){
                isSingleMode = true;
            }

            if (isSingleMode) {

                checkOneEmp(id);// 查阅个人信息
            } else  {
                checkBetweenEmp(id);
            }

        } catch (FileNotFoundException e) {
            result_find_tv.setText("未找到该用户……");
        } catch (ClassNotFoundException e) {
            result_find_tv.setText("这是什么类型？");
        } catch (IOException e) {
            e.printStackTrace();
            result_find_tv.setText(e.toString());
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
        Employee emp = ManageTools.checkEmp(id);// 获取信息
        StringBuffer sb = new StringBuffer();

        sb.append("--------------上级--------------\n");
        sb.append(ManageTools.printInfo(emp.getLeaderId()));// 打印上级信息
        sb.append("--------------本级--------------\n");
        sb.append( ManageTools.printInfo(id));// 打印本级信息
        sb.append("--------------左支--------------\n");
        sb.append(ManageTools.printInfo(emp.getLeftUnderId()));// 打印左支信息
        sb.append("--------------右支--------------\n");
        sb.append(ManageTools.printInfo(emp.getRightUnderId()));// 打印右支信息
        result_find_tv.setText(sb.toString());
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
        result_find_tv.setText(ManageTools.printInfo(id));
//        Utils.showToast(this,"isModIntent : "+isModIntent);
        if (isModIntent){

            Utils.showLog("isModIntent : "+isModIntent);
            Utils.gotoNextActivity(this,AddActivity.class,ManageTools.checkEmp(id));
            finish();
        }
    }
}
