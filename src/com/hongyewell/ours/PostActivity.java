package com.hongyewell.ours;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hongyewell.util.WebUtil;

public class PostActivity extends Activity {
	private Button postInfoButton;
	private EditText editTitle;
	private EditText editContent;
	private String inputTitle,inputContent,username;
	private String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
		postInfoButton = (Button) findViewById(R.id.btn_postInfo);
		editTitle = (EditText) findViewById(R.id.edit_title);
		editContent = (EditText) findViewById(R.id.edit_content);
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
	    id =String.valueOf(intent.getExtras().getInt("id")) ;
		
		postInfoButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				inputTitle = editTitle.getText().toString();
				inputContent = editContent.getText().toString();
				
				if (inputTitle == null || inputTitle.length() <= 0 || inputContent == null || inputContent.length() <= 0) {
					Toast.makeText(PostActivity.this, "您可能还没输入标题或内容o(>﹏<)o",Toast.LENGTH_SHORT ).show();
				}
				else 
				{
					new AsyncTask<Void, Void, Void>(){

						@Override
						protected Void doInBackground(Void... arg0) {
							
								WebUtil webUtil = new WebUtil();
								webUtil.postInfo(id, inputTitle,inputContent);
							return null;
						}
						
					}.execute();
					//跳转至主页...
					Intent intent = new Intent(PostActivity.this,MainActivity.class);
					intent.putExtra("username", username);
					startActivity(intent);
				}
				
			}
		});
	}

}
