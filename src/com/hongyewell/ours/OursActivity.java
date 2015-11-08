package com.hongyewell.ours;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.hongyewell.pojo.User;
import com.hongyewell.util.ActivityCollector;
import com.hongyewell.util.NetStateUtil;
import com.hongyewell.util.WebUtil;


public class OursActivity extends Activity {
	private SharedPreferences pref;
	private String account,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		setContentView(R.layout.activity_ours);
		//判断是否有网络
		boolean flag = NetStateUtil.isAvailable(OursActivity.this);
		if (flag) {
			pref = PreferenceManager.getDefaultSharedPreferences(this);
			account = pref.getString("account", "");
			password = pref.getString("password", "");
			Log.i("why", "姓名:"+account+"密码："+password);
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
				new AsyncTask<Void, Integer, User>() {
					@Override
					protected User doInBackground(Void... arg0) {
						User user = new User();
						WebUtil webUtil = new WebUtil();
						user = webUtil.userLogin(account, password);
						return user;
					}
					
					@Override
					protected void onPostExecute(User result) {
							Intent intent = new Intent(OursActivity.this,MainActivity.class);
							Bundle bundle = new Bundle();
							bundle.putSerializable("user", result);
							intent.putExtras(bundle);
						    startActivity(intent);
							
						}
					
				}.execute();
			}
		}
		else {
			Toast.makeText(OursActivity.this, "亲，请先连接网络哟~", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}
}
