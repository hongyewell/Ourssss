package com.hongyewell.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 包管理工具类
 * @author miying
 */
public class PackageManagerUtil {

	private Context context;
	private PackageManager packageManager;
	private PackageInfo packageInfo;
	
	public PackageManagerUtil(Context context) {
		this.context = context;
		init();
	}
	
	/**
	 * 初始化信息
	 */
	private void init() {
		packageManager = context.getPackageManager();
		try {
			packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取版本号
	 * @return
	 */
	public int getVersionCode() {
		return packageInfo.versionCode;
	}
	
	/**
	 * 获取版本名称
	 * @return
	 */
	public String getVersionName() {
		return packageInfo.versionName;
	}
	
}
