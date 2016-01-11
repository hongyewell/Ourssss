package com.hongyewell.util;

/**
 * 常量
 * @author miying
 */
public class Constant {
	/**
	 * base url
	 */
	public static final String BASE_URL = "http://miying.sinaapp.com/api";
	
	/**
	 * 版本号url
	 */
	public static final String VERSION_CODE_URL = BASE_URL + "/getLatestVersionInfo/";
	
	/**
	 * apk下载地址
	 */
	public static final String APK_DOWNLOAD_URL = "http://miying.sinaapp.com/static/files/Ours.apk";
	
	
	/**
	 * 接口url
	 * @author miying
	 */
	public interface OursUrl {
		/**
		 * 登录接口
		 */
		String LOGIN_URL = BASE_URL + "/api/login/";
		
		/**
		 * 查看用户信息接口
		 */
		String USER_INFO = BASE_URL + "//";
	}
	
}
