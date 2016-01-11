package com.hongyewell.pojo;

/**
 * 版本信息
 * @author miying
 *
 */
public class VersionInfo {

	/**
	 * 版本号
	 */
	private int versionCode;
	
	/**
	 * 版本名称
	 */
	private String versionName;
	
	/**
	 * apk下载地址
	 */
	private String apkUrl;

	public VersionInfo() {
	}
	
	public VersionInfo(int versionCode, String versionName, String apkUrl) {
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.apkUrl = apkUrl;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}
	
}
