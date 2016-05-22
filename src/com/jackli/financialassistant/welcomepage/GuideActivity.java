package com.jackli.financialassistant.welcomepage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jackli.financialassistant.R;

public class GuideActivity extends Activity{
	private List<View> views;
	private ImageView[] dots;	//底部小点图片
	private int currentIndex;	//记录当前选中位置
	private ViewPager viewPager;
	private ViewPagerAdapter vpAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	//强制竖屏
		setContentView(R.layout.guide);
		initViews();	// 初始化页面
		initDots();		// 初始化底部小点
	}

	private void initViews() {
		LayoutInflater inflater = LayoutInflater.from(this);	//获得LayoutInflater实例
		views = new ArrayList<View>();
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.viewpager_one, null));
		views.add(inflater.inflate(R.layout.viewpager_two, null));
		views.add(inflater.inflate(R.layout.viewpager_three, null));
		views.add(inflater.inflate(R.layout.viewpager_four, null));

		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views, this);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(vpAdapter);
		// 绑定页面改变监听
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {	
			
			// 当新的页面被选中时调用
			@Override
			public void onPageSelected(int arg0) {
				setCurrentDot(arg0);	//设置底部小圆点为选中状态
			}
			// 当当前页面被滑动时调用
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			// 当滑动状态改变时调用
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.guide_ll);
		dots = new ImageView[views.size()];
		// 循环取得小点图片
		for (int i = 0; i < views.size(); i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(false);	// 都设为灰色
		}
		currentIndex = 0;
		dots[currentIndex].setEnabled(true);// 初始化第一个设置为白色，即选中状态
	}

	//设置底部小圆点为选中状态
	private void setCurrentDot(int position) {
		if (position < 0 || position > views.size() - 1|| currentIndex == position) {
			return;
		}
		dots[position].setEnabled(true);	//改变页面后传进来的位置设为白色
		dots[currentIndex].setEnabled(false);//之前的设为暗色
		currentIndex = position;
	}

}
