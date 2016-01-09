package com.hongyewell.ours;

import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.hongyewell.pojo.VersionInfo;
import com.hongyewell.util.AlertDialogUtil;
import com.hongyewell.util.PackageManagerUtil;
import com.hongyewell.util.ToastUtil;
import com.hongyewell.util.WebUtil;

public class WelcomeActivity extends Activity {
	private PackageManagerUtil packageManagerUtil;
	
	/**
	 * 当前版本号
	 */
	private int currentVersionCode = 0;

	private VersionInfo newVersionInfo;
	
	private AlertDialogUtil alertDialogUtil;
	
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_welcome);
	
		packageManagerUtil = new PackageManagerUtil(this);
		// 获取当前应用的版本号
		currentVersionCode = packageManagerUtil.getVersionCode();
		
		alertDialogUtil = AlertDialogUtil.getInstance(WelcomeActivity.this);
		dialog = new ProgressDialog(WelcomeActivity.this);
		
		// 获取最新版本
		try {
			newVersionInfo = new GetLatestVersionInfoTask().execute().get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(newVersionInfo != null && newVersionInfo.getVersionCode() > currentVersionCode) {
			alertDialogUtil.setTitle("更新提醒").setMessage("亲，版本可以更新了哦~~")
			.setPositiveButton("是", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					new DownloadApkTask().execute(newVersionInfo.getApkUrl());
				}
			}).setNegativeButton("否", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			}).display();
		}else {
			ToastUtil.show(WelcomeActivity.this, "没有更新", Toast.LENGTH_LONG);
		}
	}
	
	/**
	 * 获取最新版本信息任务
	 * @author miying
	 *
	 */
	class GetLatestVersionInfoTask extends AsyncTask<Void, Void, VersionInfo> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected VersionInfo doInBackground(Void... params) {
			return WebUtil.getLatestVersionInfo();
		}
		@Override
		protected void onPostExecute(VersionInfo result) {
			super.onPostExecute(result);
		}
	}

	/**
	 * 下载apk任务
	 * @author miying
	 */
	class DownloadApkTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			dialog.show();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(String... params) {
			String apkPath = WebUtil.downloadApk(params[0]);
			if(apkPath != null && !apkPath.equals("")) {
				// 安装apk
				Uri uri = Uri.fromFile(new File(apkPath));
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setDataAndType(uri, "application/vnd.android.package-archive");
				startActivity(intent);
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			dialog.dismiss();
			super.onPostExecute(result);
		}
	}
	
}
