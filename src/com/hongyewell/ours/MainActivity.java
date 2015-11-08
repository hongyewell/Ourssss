package com.hongyewell.ours;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hongyewell.adapter.InfoAdapter;
import com.hongyewell.pojo.Info;
import com.hongyewell.pojo.User;
import com.hongyewell.util.ActivityCollector;
import com.hongyewell.util.WebUtil;

public class MainActivity extends Activity implements PullToRefreshBase.OnRefreshListener2<ListView> {
	
	private PullToRefreshListView infoListView;
	private List<Info> infoList = new ArrayList<Info>();
	private InfoAdapter adapter;
	private TextView tvUserName;
	private Button  btnExit,btnQuit,btnMe;
	private ImageView btnPost;
	private int num,id;
	private Info info;
	private String username;
	private User user;
	private SharedPreferences pref;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityCollector.addActivity(this);
		setContentView(R.layout.activity_main);
		infoListView = (PullToRefreshListView) findViewById(R.id.infoListView);
		tvUserName = (TextView) findViewById(R.id.tv_username);
		btnPost = (ImageView) findViewById(R.id.btn_post);
		btnExit = (Button) findViewById(R.id.btn_exit);
		btnQuit = (Button) findViewById(R.id.btn_quit);
		btnMe = (Button) findViewById(R.id.btn_me);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		user = (User) bundle.getSerializable("user");
		username = user.getUsername();
		id = user.getId();
		Log.i("why", user.getJoinedDate()+".......");
		tvUserName.setText(username);
		
		infoListView.setMode(Mode.BOTH);//同时支持下拉刷新和下拉加载
		infoListView.setOnRefreshListener(this);
		num = 1;		
		initData(num);
		
		//发布一条状态
		btnPost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,PostActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		//ListView点击事件
		infoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				info = infoList.get(position -1);
				
				Toast.makeText(MainActivity.this, info.getTitle(), Toast.LENGTH_SHORT).show();
				
			}
		});
		
		//退出当前账号
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
				dialog.setTitle("退出账号");
				dialog.setMessage("亲，你要退出账号么？");
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						editor = pref.edit();
						editor.clear();
						editor.commit();
						Intent intent = new Intent(MainActivity.this,LoginActivity.class);
						startActivity(intent);
					}
				});
				dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				dialog.show();
			}
		});
		
		//退出应用程序
		btnQuit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
				dialog.setTitle("退出程序");
				dialog.setMessage("亲，你真的要退出了么？");
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						ActivityCollector.finishAll();
					}
				});
				dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						
					}
				});
				dialog.show();
			}
		});
		
		//关于我
		btnMe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,MeActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				startActivity(intent);
				
			}
		});
	
	}
	
	//下拉刷新
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		new AsyncTask<Void, Void, List<Info>>(){

			@Override
			protected List<Info> doInBackground(Void... arg0) {
				List<Info> refreshList = new ArrayList<Info>();
				refreshList.add(null);
				Log.i("why", refreshList.size()+"");
				return refreshList;
			}
			protected void onPostExecute(List<Info> result) {
				if (result.size() == 1) {
					adapter.notifyDataSetChanged();
					infoListView.onRefreshComplete();
					Toast.makeText(MainActivity.this, "等营咪咪的接口...", Toast.LENGTH_SHORT).show();
				}
				
			};
		}.execute();
		
	}
	//上拉加载
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		num ++;
		DataChange(num);
		
	}
	
	
	//访问网络，获取数据
	private void initData(int page) {
		
		new AsyncTask<Integer, Void, List<Info>>() { //使用Integer包装类
			
			@Override
			protected List<Info> doInBackground(Integer... page) {
				List<Info> refreshList = new ArrayList<Info>();
				WebUtil webUtil = new WebUtil();
				int pageNum = page[0];
				refreshList = webUtil.getInfoList(pageNum);
				Log.i("yeye", refreshList.size()+"");
				return refreshList;
			}
			
			protected void onPostExecute(List<Info> result) {
				if (result.size() == 0) {
					adapter.notifyDataSetChanged();
					infoListView.onRefreshComplete();
					Toast.makeText(MainActivity.this, "没有其他数据了耶...", Toast.LENGTH_SHORT).show();
				}
				else {
					Log.i("infolist", infoList.toString());
					infoList.addAll(result);
					adapter = new InfoAdapter(MainActivity.this, infoList);//注意这里是infoList
					infoListView.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					infoListView.onRefreshComplete();
					Toast.makeText(MainActivity.this, "加载了"+result.size()+"条数据..", Toast.LENGTH_SHORT).show();
				}
				
			}
			
		}.execute(page); //传参数page

	}
	
	//下拉加载数据
	private void DataChange(int page) {
		
		new AsyncTask<Integer, Void, List<Info>>() { //使用Integer包装类
			
			@Override
			protected List<Info> doInBackground(Integer... page) {
				List<Info> refreshList = new ArrayList<Info>();
				WebUtil webUtil = new WebUtil();
				int pageNum = page[0];
				refreshList = webUtil.getInfoList(pageNum);
				Log.i("yeye", refreshList.size()+"");
				return refreshList;
			}
			
			protected void onPostExecute(List<Info> result) {
				if (result.size() == 0) {
					adapter.notifyDataSetChanged();
					infoListView.onRefreshComplete();
					Toast.makeText(MainActivity.this, "没有其他数据了耶...", Toast.LENGTH_SHORT).show();
				}
				else {
					Log.i("infolist", infoList.toString());
					infoList.addAll(result);
				/*	adapter = new InfoAdapter(MainActivity.this, infoList);//注意这里是infoList*/	
				/*infoListView.setAdapter(adapter);*/
					adapter.notifyDataSetChanged();
					infoListView.onRefreshComplete();
					Toast.makeText(MainActivity.this, "加载了"+result.size()+"条数据..", Toast.LENGTH_SHORT).show();
				}
				
			}
			
		}.execute(page); //传参数page

	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollector.removeActivity(this);
	}

}
