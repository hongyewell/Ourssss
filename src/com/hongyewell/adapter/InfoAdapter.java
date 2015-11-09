package com.hongyewell.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hongyewell.ours.R;
import com.hongyewell.pojo.Info;

public class InfoAdapter extends BaseAdapter{
	private List<Info> iList;
	private LayoutInflater inflater;
	
	public InfoAdapter(Context context,List<Info> infoList){
		iList = infoList;
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public int getCount() {
		return iList.size();
	}

	@Override
	public Object getItem(int position) {
		return iList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view ;
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			view = inflater.inflate(R.layout.info_item, null);
			viewHolder.title = (TextView) view.findViewById(R.id.info_title);
			viewHolder.username = (TextView) view.findViewById(R.id.info_author);
			viewHolder.time = (TextView) view.findViewById(R.id.info_publishedDate);
			viewHolder.content = (TextView) view.findViewById(R.id.info_content);
			/*viewHolder.photo = (ImageView) view.findViewById(R.id.info_photo);*/
			view.setTag(viewHolder);
			
		}else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		Info infoItem = iList.get(position);
		//截取标题
		String title = infoItem.getTitle();
		if (title.length()>10) {
			title = infoItem.getTitle().substring(0,10)+"...";
		}
		viewHolder.title.setText(title);
		viewHolder.username.setText(infoItem.getAuthor().getUsername());
		//截取时间
		String time = infoItem.getPublishedDate().substring(0,16);
		viewHolder.time.setText(time);
		//截取内容
		String content = infoItem.getContent();
		if (content.length()>50) {
			content = content.substring(0,50)+"...";
		}
		viewHolder.content.setText(content);
		//设置头像
		/*viewHolder.photo.setImageBitmap();*/

		return view;
	}
	
	class ViewHolder{
		TextView title;
		TextView username;
		TextView time;
		TextView content;
		/*ImageView photo;*/
	}

}
