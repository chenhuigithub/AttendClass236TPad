package com.example.teaching236pad.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义application,用于管理整个程序
 * 
 * @author
 * 
 */
public class CustomApplication extends Application {

	public static Context applicationContext;
	private static CustomApplication instance;

	public static int Screen_Width;
	public static int Screen_Height;
	public static float Screen_Density;

	private List<Activity> activityStack = new ArrayList<Activity>();
	public static boolean isFirst = true;

	@Override
	public void onCreate() {
		super.onCreate();
		applicationContext = this;
		instance = this;

		// DisplayMetrics dm = new DisplayMetrics();
		// WindowManager manager = (WindowManager)
		// getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		// manager.getDefaultDisplay().getMetrics(dm);
		// Screen_Width=dm.widthPixels;

		Screen_Width = getResources().getDisplayMetrics().widthPixels;
		Screen_Height = getResources().getDisplayMetrics().heightPixels;
		Screen_Density = getResources().getDisplayMetrics().density;


	}


	public void addAty(Activity aty) {
		activityStack.add(aty);
	}

	public void finishAty() {
		for (Activity aty : activityStack) {
			aty.finish();
		}
	}

	public static CustomApplication getInstance() {
		return instance;
	}

	/**
	 * 返回本程序包名、版本之类的信息
	 * 
	 * @return
	 */
	public PackageInfo getPackageInfo() {
		PackageInfo info = new PackageInfo();
		try {
			info = getPackageManager().getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return info;
	}


}
