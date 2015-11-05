package com.hongyewell.ours;

import com.hongyewell.pojo.Author;
import com.hongyewell.util.ActivityCollector;
import com.hongyewell.util.WebUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;


public class OursActivity extends Activity {
	private SharedPreferences pref;
	private String account,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		setContentView(R.layout.activity_ours);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		account = pref.getString("account", "");
		password = pref.getString("password", "");
		Log.i("why", "姓名："+account+"密码："+password);
		if (account == null || account.length() <= 0 || password == null || password.length() <= 0) {
			new Handler().postDelayed(new Runnable(){

				@Override
				public void run() {
				    Intent intent = new Intent(OursActivity.this,LoginActivity.class);
				    startActivity(intent);
				    OursActivity.this.finish();
				}

				}, 1500);  //开始动画持续时间
		}
		else {
			new AsyncTask<Void, Integer, Author>() {
				@Override
				protected Author doInBackground(Void... arg0) {
					Author author = new Author();
					WebUtil webUtil = new WebUtil();
					author = webUtil.userLogin(account, password);
					return author;
				}
				
				@Override
				protected void onPostExecute(Author result) {
						Intent intent = new Intent(OursActivity.this,MainActivity.class);
						intent.putExtra("username", result.getUsername());
						intent.putExtra("id", result.getId());
					    startActivity(intent);
						
					}
				
			}.execute();
		}
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}
