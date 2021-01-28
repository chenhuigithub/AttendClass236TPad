package com.example.teaching236pad.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.ViewGroup;

import com.example.teaching236pad.model.KeyValue;

import java.util.ArrayList;
import java.util.List;


/**
 * 工具类
 *
 * @author zhaochenhui，2018.05.16
 */
public class Utils {
    private Context context;

    public Utils(Context context) {
        this.context = context;
    }

    /**
     * String[]转为List<String>
     *
     * @param s String[]数据
     * @return
     */
    public static List<String> getList(String[] s) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < s.length; i++) {
            list.add(s[i]);
        }

        return list;
    }

    /**
     * String[]转为List<String>
     *
     * @param s String[]数据
     * @return
     */
    public static List<KeyValue> getKeyValueList(String[] s) {
        List<KeyValue> list = new ArrayList<KeyValue>();
        for (int i = 0; i < s.length; i++) {
            KeyValue info = new KeyValue();
            info.setId(String.valueOf(i));
            info.setName(s[i]);
            list.add(info);
        }

        return list;
    }

    /**
     * 去除view的父布局
     *
     * @param view
     */
    public static void removeParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
    }

    public boolean checkinstallornotadobeflashapk() {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> infoList = pm
                .getInstalledPackages(PackageManager.GET_SERVICES);
        for (PackageInfo info : infoList) {
            if ("com.adobe.flashplayer".equals(info.packageName)) {
                return true;
            }
        }
        return false;
    }

}
