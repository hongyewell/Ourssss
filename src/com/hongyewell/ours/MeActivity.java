package com.hongyewell.ours;

import com.hongyewell.pojo.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MeActivity extends Activity {
	private TextView userId, userName, userEmail,userPhotoURL,userJoinedDate,userLastLogin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me);
		userId = (TextView) findViewById(R.id.user_id);
		userName = (TextView) findViewById(R.id.user_username);
		userEmail = (TextView) findViewById(R.id.user_email);
		userPhotoURL = (TextView) findViewById(R.id.user_photoURL);
		userJoinedDate = (TextView) findViewById(R.id.user_joinedDate);
		userLastLogin = (TextView) findViewById(R.id.user_lastLogin);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		User user = (User) bundle.getSerializable("user");
		userId.setText(user.getId()+"");
		userName.setText(user.getUsername());
		userEmail.setText(user.getEmail());
		userPhotoURL.setText(user.getPhotoUrl());
		userJoinedDate.setText(user.getJoinedDate());
		userLastLogin.setText(user.getLastLogin());
		
	}

}
