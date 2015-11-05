package com.hongyewell.ours;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class OursActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ours);
		new Handler().postDelayed(new Runnable(){

			@Override
			public void run() {
			    Intent intent = new Intent(OursActivity.this,LoginActivity.class);
			    startActivity(intent);
			    OursActivity.this.finish();
			}

			}, 1500);  //开始动画持续时间
	}

}
