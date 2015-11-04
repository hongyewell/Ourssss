package com.hongyewell.util;

import java.io.IOException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;

import com.hongyewell.pojo.Info;
import com.hongyewell.pojo.User;

public class WebUtil {
	
	String getInfoURL = "http://miying.sinaapp.com/api/posts/";
	String loginURL = "http://miying.sinaapp.com/api/login/";
	String postInfoURL = "http://miying.sinaapp.com/api/post/";
	
	/**
	 * 获取信息列表
	 * @param page
	 * @return
	 */
	public List<Info> getInfoList(int page){
		List<Info> infoList = new ArrayList<Info>();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(getInfoURL+page+"/10");
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity,"utf-8");
				
				//JSON字符串解析
				try {
					JSONObject jObject = new JSONObject(result);
					JSONArray jsonArray = jObject.getJSONArray("datas");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject nObject = (JSONObject) jsonArray.get(i);
						Info info = new Info();
						info.setId(nObject.getJSONObject("author").getInt("id"));
						info.setTitle(nObject.getString("title"));
						info.setContent(nObject.getString("content"));
						info.setAuthor(nObject.getJSONObject("author").getString("username"));
						info.setPublishedDate(nObject.getString("publishedDate"));
						infoList.add(info);
						Log.i("yeye", nObject.getString("title"));
					}
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
					user.setId(nObject2.getInt("id"));
					user.setUsername(nObject2.getString("username"));
					user.setEmail(nObject2.getString("email"));
					user.setPhotoUrl(nObject2.getString("photoUrl"));
					user.setJoinedDate(nObject2.getString("joinedDate"));
					user.setLastLogin(nObject2.getString("lastLogin"));
					return user;
				}
				
			}
			
		} catch (Exception e) {
		}
		return user;
	}
	
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

}
