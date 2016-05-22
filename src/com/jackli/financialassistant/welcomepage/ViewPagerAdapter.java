package com.jackli.financialassistant.welcomepage;

import java.util.List;

import com.jackli.financialassistant.mainactivity.MainActivity;
import com.jackli.financialassistant.util.Constants;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jackli.financialassistant.R;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> views;// 界面列表
	private Activity activity;

	public ViewPagerAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}
	
	@Override
	public int getCount() {
		if (views != null) {
			 return views.size();
		}
		return 0;
	}
	
	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	//销毁对应位置的界面
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewGroup)container).removeView(views.get(position));		
	}

	//初始化对应位置的界面
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewGroup)container).addView(views.get(position));
		if (position == views.size()-1) {
			ImageView iv_start = (ImageView)container.findViewById(R.id.iv_start);
			iv_start.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					setNoGuide();	//设置不需要再引导
					goHome();	//跳转到应用的主页面
				}
			});
		}
		return views.get(position);
	}

	private void setNoGuide() {
		SharedPreferences preferences = activity.getSharedPreferences(
				Constants.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putBoolean("isFirstIn", false);
		editor.commit();
	}

	private void goHome() {
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

}
