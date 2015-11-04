package com.hongyewell.ours;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hongyewell.adapter.InfoAdapter;
import com.hongyewell.pojo.Info;
import com.hongyewell.util.WebUtil;

public class MainActivity extends Activity implements PullToRefreshBase.OnRefreshListener2<ListView> {
	
	private PullToRefreshListView infoListView;
	private List<Info> infoList = new ArrayList<Info>();
	private InfoAdapter adapter;
	private TextView tvUserName;
	private Button btnPost;
	private int num,id;
	private Info info;
	private String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		infoListView = (PullToRefreshListView) findViewById(R.id.infoListView);
		tvUserName = (TextView) findViewById(R.id.tv_username);
		btnPost = (Button) findViewById(R.id.btn_post);
		
		Intent intent = getIntent();
		username = intent.getStringExtra("username");
		id = intent.getExtras().getInt("id");
		tvUserName.setText("hello~"+username+"~");
		
		infoListView.setMode(Mode.BOTH);//同时支持下拉刷新和下拉加载
		infoListView.setOnRefreshListener(this);
		num = 1;		
		initData(num);
		
		//发布一条状态
		btnPost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,PostActivity.class);
				intent.putExtra("id", id);
				intent.putExtra("username", username);
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
		initData(num);
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
					adapter = new InfoAdapter(MainActivity.this, infoList);//注意这里是infoList，
					infoListView.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					infoListView.onRefreshComplete();
					Toast.makeText(MainActivity.this, "加载了"+result.size()+"条数据..", Toast.LENGTH_SHORT).show();
				}
				
			}
			
		}.execute(page); //传参数page

	}

}
