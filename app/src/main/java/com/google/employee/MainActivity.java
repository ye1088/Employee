package com.google.employee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import cn.rasion.tree.tools.ManageTools;
import cn.rasion.tree.tools.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.setAppDataDir(this);
        start();// 实例化本类并调用本类开始方法
    }

//    Menu m = new Menu();




    public void operate(View v){

        try {
            switch (v.getId()){
                case R.id.add:// 添加员工类 开始方法
                    Utils.gotoNextActivity(this,AddActivity.class);
                    break;
                case R.id.del:
                    deleteEmp();
                    break;
                case R.id.mod:
                    ArrayList<String[]> listData = new ArrayList<>();
                    listData.add(new String[]{"action"});
                    listData.add(new String[]{"mod"});
                    Utils.gotoNextActivity(this,FindActivity.class,listData);
                    break;
                case R.id.find:
                    Utils.gotoNextActivity(this,FindActivity.class);
                    break;
                case R.id.exit:
                    System.exit(0);
                    break;
            }

//            } else if (switchNum == 2) {
//                new Check().start();// 检阅员工类 开始方法
//            } else if (switchNum == 3) {
//                new Edit().start();// 编辑员工类 开始方法
//            } else if (switchNum == 4) {
//                new Delete().start();// 删除员工类 开始方法
//            } else if (switchNum == 0) {
//                System.out.println("退出～");
//            } else {
//                System.out.println("请输入正确编号！");
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteEmp(String id){
        if (id.equals("")) {
            Toast.makeText(MainActivity.this, "id不能为空!", Toast.LENGTH_SHORT).show();
            try {
                deleteEmp();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File file = new File(ManageTools.Emp_Data_Dir + File.separator + id + ".dat");// 获取文件
            if (file.exists()) {
                file.delete();// 删除员工信息文件


                /** 以下将关联信息文件中该条信息释放为空槽 */
                RandomAccessFile raf = null;
                try {
                    raf = new RandomAccessFile(ManageTools.Btn_Data_Path, "rw");


                    long pos = ManageTools.getBtnDataPos(id);

                    raf.seek(pos);

                    raf.write(ManageTools.strBytes("", (int) ManageTools.One_Data_Len, "utf-8"));

                    raf.close();
                    Toast.makeText(MainActivity.this, "员工已删除！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                Utils.showToast(this,"不存在此员工!!!");
            }
        }
    }




    private void deleteEmp() throws IOException {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final EditText et_id = new EditText(this);
        dialog.setView(et_id);

        dialog.setTitle("输入要删除员工id");
        dialog.setNegativeButton("取消",null);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = et_id.getText().toString();
                deleteEmp(id);
                }});

        dialog.create().show();

    }

    /**
     * 开始方法
     */
    public void start() {
        loadConfig();// 加载所有配置文件与信息
//        m.start(this);// 调用菜单类开始方法
    }

    /**
     * 加载配置文件与信息
     */
    public void loadConfig() {
        new ManageTools();// 实例化管理工具类
    }
}
