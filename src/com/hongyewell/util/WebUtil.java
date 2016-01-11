package com.hongyewell.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hongyewell.pojo.Info;
import com.hongyewell.pojo.ResultData;
import com.hongyewell.pojo.User;
import com.hongyewell.pojo.VersionInfo;

public class WebUtil {
	public static final String TAG = "WebUtil";
	
	String getInfoURL = "http://miying.sinaapp.com/api/posts/";
	String loginURL = "http://miying.sinaapp.com/api/login/";
	String postInfoURL = "http://miying.sinaapp.com/api/post/";
	String registerURL = "http://miying.sinaapp.com/api/register/";

	/**
	 * 发送post请求，返回实体类
	 * 
	 * @author miying
	 * @param url
	 * @return T
	 */
	public static <T> T getResultAsObject(String url, Class<T> clazz) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200) {
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity, "utf-8");
				@SuppressWarnings("unchecked")
				ResultData<T> resultData = com.alibaba.fastjson.JSONObject.parseObject(result, ResultData.class);
				com.alibaba.fastjson.JSONObject jsonObject  = (com.alibaba.fastjson.JSONObject) resultData.getResult();
				String jsonString = jsonObject.toJSONString();
				
				return com.alibaba.fastjson.JSONObject.parseObject(jsonString, clazz);
			}else {
				Log.i(TAG, "--statusCode->>" + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * http get请求下载文件
	 * @param url
	 * @return apk文件保存的路径
	 */
	public static String getResultAsFile(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpPost = new HttpGet(url);
		FileOutputStream fos = null;
		// sdcard的路径
		File file = Environment.getExternalStorageDirectory();
		
		try {
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == 200) {
				HttpEntity entity = response.getEntity();
				byte[] bytes = EntityUtils.toByteArray(entity);
				
				if(Environment.getExternalStorageState()
						.equals(Environment.MEDIA_MOUNTED)) {
					fos = new FileOutputStream(new File(file, "Ours.apk"));
					fos.write(bytes, 0, bytes.length);
					// 返回apk的路径
					return file.getAbsolutePath() + "/Ours.apk";
				}
				
			}else {
				Log.i(TAG, "--statusCode->>" + statusCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(fos != null) {
					fos.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return "";
	}
	
	/**
	 * 获取应用最新的版本信息
	 * @return
	 */
	public static VersionInfo getLatestVersionInfo() {
		return getResultAsObject(Constant.VERSION_CODE_URL, VersionInfo.class);
	}
	
	/**
	 * 下载最新版本的apk
	 * @return apk文件保存的路径
	 */
	public static String downloadApk(String url) {
		return getResultAsFile(url);
	}
	
	/**
	 * 用户注册
	 * @param username
	 * @param password
	 * @return
	 */
	public int userRegister(String username,String password){
		int state = 250;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(registerURL);
			JSONObject object = new JSONObject();
			object.put("username", username);
			object.put("password", password);
			StringEntity se = new StringEntity(object.toString(),"utf-8");
			httpPost.setEntity(se);
			httpPost.addHeader("Content-type","application/json;charset=UTF-8");
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity,"utf-8");
				JSONObject nObject = new JSONObject(result);
				state = nObject.getInt("code");
			    return state;
			}
			
		} catch (Exception e) {
		}
		return state;
	}
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User userLogin(String username,String password){
		User user = new User();
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(loginURL);
			JSONObject object = new JSONObject();
			object.put("username", username);
			object.put("password", password);
			StringEntity se = new StringEntity(object.toString(),"utf-8");
			httpPost.setEntity(se);
			httpPost.addHeader("Content-type","application/json;charset=UTF-8");
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String result = EntityUtils.toString(entity,"utf-8");
				JSONObject nObject = new JSONObject(result);
				//判断是否登录成功
				int state = nObject.getInt("code");
				if (state == 404) {
					user.setId(-1);
					return user;
				}else {
					JSONObject nObject2 = nObject.getJSONObject("datas");
					Gson gson = new Gson();
					user = gson.fromJson(nObject2.toString(), User.class);
					return user;
				}
				
			}
			
		} catch (Exception e) {
		}
		return user;
	}
	
	/**
	 * 获取信息列表
	 * @param page
	 * @return
	 */
	public List<Info> getInfoList(int page){
		List<Info> infoList = new ArrayList<Info>();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(getInfoURL+page+"/15");
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity,"utf-8");
				
				//JSON字符串解析
				try {
					JSONObject jObject = new JSONObject(result);
				
					Gson gson = new Gson();
					infoList = gson.fromJson(jObject.getJSONArray("datas").toString(), new TypeToken<List<Info>>(){}.getType());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return infoList;
		
	}
	
	/**
	 * 发布消息
	 * @param id
	 * @param title
	 * @param content
	 */
	public void postInfo(String id, String title, String content){
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(postInfoURL);
			JSONObject object = new JSONObject();
			object.put("userId", id);
			object.put("title", title);
			object.put("content", content);
			StringEntity se = new StringEntity(object.toString(),"utf-8");
			httpPost.setEntity(se);
			httpPost.setHeader("Content-type","application/json;charset=UTF-8");
			HttpResponse response = httpClient.execute(httpPost);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				Log.i("why", "提交成功");
			}
			
		} catch (Exception e) {
		}
		
	}
	
	/**
	 * 加载图片
	 * @return
	 */
	public void showImageByAsyncTask(ImageView imageView,String url){
		new NewsAsyncTask(imageView).execute(url);
	}
	//AsyncTask异步请求
	private class NewsAsyncTask extends AsyncTask<String, Void, Bitmap>{
		private ImageView mImageView;
		//构造方法
		public NewsAsyncTask(ImageView imageView){
			mImageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			return getBitmapFromURL(params[0]);
		}
		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			mImageView.setImageBitmap(result);
		}
	}
	//HttpURLConnection bitmap
	public Bitmap getBitmapFromURL(String urlString){
		Bitmap bitmap;
		InputStream is = null;
		try {
			URL url = new URL(urlString);
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				is = new BufferedInputStream(connection.getInputStream());
				bitmap = BitmapFactory.decodeStream(is);
				connection.disconnect();
				return bitmap;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}

}
