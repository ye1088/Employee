package cn.rasion.tree.tools;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.employee.FindActivity;
import com.google.employee.MainActivity;

import java.util.ArrayList;
import java.util.Map;

import cn.rasion.tree.personage.Employee;

/**
 * Created by Administrator on 2017/12/16.
 */

public class Utils {

    public static String appDataDir = "/sdcard/demo";

    public static void gotoNextActivity(Activity srcActivity, Class dstActivityName, ArrayList<String[]> listData){
        Intent intent = new Intent();
        if (listData != null){
            String[] keys = listData.get(0);
            String[] values = listData.get(1);
            for (int i = 0; i < keys.length; i++) {
                intent.putExtra(keys[i],values[i]);
            }
        }

        intent.setClass(srcActivity,dstActivityName);
        srcActivity.startActivity(intent);
    }
    public static void gotoNextActivity(Activity srcActivity, Class dstActivityName, Employee employee){
        Intent intent = new Intent();
        if (employee != null){


            intent.putExtra("employee",employee);

        }

        intent.setClass(srcActivity,dstActivityName);
        srcActivity.startActivity(intent);
    }

    public static void gotoNextActivity(MainActivity srcActivity, Class dstActivityName) {
        Intent intent = new Intent();


        intent.setClass(srcActivity,dstActivityName);
        srcActivity.startActivity(intent);
    }

    public static void setAppDataDir(Activity activity){
        appDataDir = activity.getExternalFilesDir(null).getAbsolutePath();
    }

    public static void showToast(Activity activity ,String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String msg){
        Log.e("showLog : ",msg);
    }
}
