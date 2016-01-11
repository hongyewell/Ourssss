package com.hongyewell.ours;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.hongyewell.pojo.Author;
import com.hongyewell.pojo.User;
import com.hongyewell.util.ActivityCollector;
import com.hongyewell.util.WebUtil;


public class LoginActivity extends Activity {
	private Button btnLogin,btnRegister;
	private EditText edtUserName;
	private EditText edtPassword;
	private CheckBox ckRememberCB;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_login);
        
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_toRegister);
        edtUserName = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        ckRememberCB = (CheckBox) findViewById(R.id.cb_rememberCheck);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);
		if (isRemember) {
			//将账号和密码都设置到文本框中
			String account = pref.getString("account", "");
			String password = pref.getString("password", "");
			edtUserName.setText(account);
			edtPassword.setText(password);
			ckRememberCB.setChecked(true);
		}
        
		//点击登录
        btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				userLogin();
			}
		});
        
        //点击注册
        btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
       
    	
    }
    
    
    //调用接口，用户登录
    private void userLogin(){
    	new AsyncTask<Void, Integer, User>() {
    		//获取用户输入的用户名和密码
			String username = edtUserName.getText().toString();
        	String password = edtPassword.getText().toString();
        	

			@Override
			protected User doInBackground(Void... arg0) {
				User user = new User();
				WebUtil webUtil = new WebUtil();
				user = webUtil.userLogin(username, password);
				return user;
			}
			
			@Override
			protected void onPostExecute(User result) {
				if (result.getId() == -1) {
					Toast.makeText(LoginActivity.this, "亲，请检查用户名或密码是否正确~", Toast.LENGTH_SHORT).show();
				}
				else {
					editor = pref.edit();
					if (ckRememberCB.isChecked()) {
						//检查复选框是否被选中
						editor.putBoolean("remember_password", true);
						editor.putString("account", username);
						editor.putString("password", password);
						
					}else {
						editor.clear();
					}
					editor.commit();//数据提交
					Toast.makeText(LoginActivity.this, username+"登录成功~", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("user", result);
					intent.putExtras(bundle);
				    startActivity(intent);
					
				}
			}
			
		}.execute();
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
//		ActivityCollector.removeActivity(this);
	}
    
}
