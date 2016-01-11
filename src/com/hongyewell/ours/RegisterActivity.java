package com.hongyewell.ours;

import com.hongyewell.pojo.Author;
import com.hongyewell.pojo.User;
import com.hongyewell.util.ActivityCollector;
import com.hongyewell.util.WebUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText edtUserName,edtPassword;
	private Button btnRegister;
	private String username, password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ActivityCollector.addActivity(this);
		setContentView(R.layout.activity_register);
		edtUserName = (EditText) findViewById(R.id.edit_username);
		edtPassword = (EditText) findViewById(R.id.edit_password);
		btnRegister = (Button) findViewById(R.id.btn_register);
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				username = edtUserName.getText().toString();
				password = edtPassword.getText().toString();
				//异步任务
				new AsyncTask<Void, Void, Integer>() {

					@Override
					protected Integer doInBackground(Void... arg0) {
						WebUtil webUtil = new WebUtil();
						int result = webUtil.userRegister(username, password);
						return result;
					}
					@Override
					protected void onPostExecute(Integer result) {
						if (result == 400) {
							Toast.makeText(RegisterActivity.this, "用户名已注册~", Toast.LENGTH_SHORT).show();
						}
						else {
							Toast.makeText(RegisterActivity.this, "注册成功~", Toast.LENGTH_SHORT).show();
							
							new AsyncTask<Void, Integer, User>() {
								@Override
								protected User doInBackground(Void... arg0) {
									User user = new User();
									WebUtil webUtil = new WebUtil();
									user = webUtil.userLogin(username, password);
									return user;
								}
								
								@Override
								protected void onPostExecute(User result) {
										Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
										Bundle bundle = new Bundle();
										bundle.putSerializable("user", result);
										intent.putExtras(bundle);
									    startActivity(intent);
										
									}
								
							}.execute();
						}
						
					}
				}.execute();
				
			}
		});
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
//		ActivityCollector.removeActivity(this);
	}
}
