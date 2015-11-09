package com.hongyewell.ours;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyewell.pojo.User;
import com.hongyewell.util.WebUtil;

public class MeActivity extends Activity {
	private TextView userId, userName, userEmail,userPhotoURL,userJoinedDate,userLastLogin;
	private String photoURL;
	private ImageView mImageView;
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
		mImageView = (ImageView) findViewById(R.id.user_image);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		User user = (User) bundle.getSerializable("user");
		userId.setText(user.getId()+"");
		userName.setText(user.getUsername());
		userEmail.setText(user.getEmail());
		photoURL = user.getPhotoUrl();
		userPhotoURL.setText(photoURL);
		userJoinedDate.setText(user.getJoinedDate());
		userLastLogin.setText(user.getLastLogin());
			
	      WebUtil webUtil = new WebUtil();
	      webUtil.showImageByAsyncTask(mImageView, photoURL);
				/*new AsyncTask<Void, Void, Bitmap>() {

					@Override
					protected Bitmap doInBackground(Void... arg0) {
						WebUtil webUtil = new WebUtil();
						Bitmap bitmap = webUtil.getBitmapFromURL(photoURL);
						return bitmap;
					}
					protected void onPostExecute(Bitmap result) {
						mImageView.setImageBitmap(result);
					};
				}.execute();*/
	
		
	}

}
