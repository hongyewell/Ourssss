package com.hongyewell.ours;

import com.hongyewell.util.WebUtil;

import android.app.Activity;
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
		setContentView(R.layout.activity_register);
		edtUserName = (EditText) findViewById(R.id.edit_username);
		edtPassword = (EditText) findViewById(R.id.edit_password);
		btnRegister = (Button) findViewById(R.id.btn_register);
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				username = edtUserName.getText().toString();
				password = edtPassword.getText().toString();
				//“Ï≤Ω»ŒŒÒ
				new AsyncTask<Void, Void, String>() {

					@Override
					protected String doInBackground(Void... arg0) {
						WebUtil webUtil = new WebUtil();
						String result = webUtil.userRegister(username, password);
						return result;
					}
					@Override
					protected void onPostExecute(String result) {
						Toast.makeText(RegisterActivity.this, result, Toast.LENGTH_SHORT).show();
					}
				}.execute();
				
			}
		});
	}

}
