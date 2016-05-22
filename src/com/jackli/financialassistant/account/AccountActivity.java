package com.jackli.financialassistant.account;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jackli.financialassistant.R;
import com.jackli.financialassistant.instructions.InstructionsActivity;
import com.jackli.financialassistant.mainactivity.MainActivity;
import com.jackli.financialassistant.util.FontManager;

public class AccountActivity extends FragmentActivity {
	
	List<Fragment> fragments;		//碎片集合
	private Button btn_back;		//顶部标题的返回按钮
	private TextView tv_pay;		//支出头标
	private TextView tv_income;		//收入头标
	private ImageView iv_cursor; 	//游标图片
    private int bmpWidth = 0; 		//游标宽度  
    private int offset = 0;			//游标偏移量  
    private int curIndex = 0;		//当前页卡编号
    ViewPager viewPager;
    private int viewitem = 0;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);		
		setContentView(R.layout.account_main);
		
		//初始化指示器(游标)位置  
        initCursorPos();
        //初始化头标  
		initTextView();

		//构造适配器
		fragments=new ArrayList<Fragment>();
		Log.d("Account", "set FragmentAdapter add1");
		fragments.add(new PayFragment());
		fragments.add(new IncomeFragment());
		FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
	    //设定适配器
	    viewPager = (ViewPager)findViewById(R.id.account_viewPager);
	    viewPager.setAdapter(adapter);
	    //设置字体
	    FontManager.changeFont(FontManager.getContentView(this),this);
	    
		// 返回键 监听
		btn_back = (Button) findViewById(R.id.account_btn_back);
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AccountActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_enter, R.anim.pop_activity);
				finish();
			}
		});
		
		
//		Bundle bundle = getIntent().getExtras();
//		if (bundle != null) {
//			viewitem = bundle.getInt("viewItem");
//		}
		Intent intent = getIntent();
		if (intent != null) {
		viewitem = intent.getIntExtra("viewItem", 0);
		}
		viewPager.setCurrentItem(viewitem);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

	}
	
	// ViewPager在处理滑动事件的时候要用到OnPageChangeListener
	public class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpWidth;// 页卡1->页卡2 偏移量
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			Animation animation = new TranslateAnimation(one * curIndex, one* arg0, 0, 0);
			curIndex = arg0;
			animation.setFillAfter(true);// true：图片停在动画结束位置
			animation.setDuration(300);
			iv_cursor.startAnimation(animation);
		}

	}
	
	/*
	 * 初始化动画，这个就是页卡滑动时， 下面的横线也滑动的效果，在这里需要计算一些数据
	 */
	//初始化指示器位置  ??????????????????????????
	private void initCursorPos() {
		iv_cursor = (ImageView) findViewById(R.id.iv_cursor);
		//获得图片宽度
		bmpWidth = BitmapFactory.decodeResource(getResources(),R.drawable.select_flag).getWidth();
		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 2 - bmpWidth) / 2;// 计算偏移量
		Intent intent = getIntent();
		//????????
		int info = intent.getIntExtra("double", 0);
		if (info == 1) {
			int one = offset * 4;
			Animation animation = new TranslateAnimation(one * curIndex, one,
					0, 0);
			animation.setFillAfter(true);// true：图片停在动画结束位置
			animation.setDuration(0);
			iv_cursor.startAnimation(animation);
		}
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		iv_cursor.setImageMatrix(matrix);// 设置动画初始位置
		
	}
	
	// 初始化头标
	private void initTextView() {
		tv_pay = (TextView) findViewById(R.id.text1);
		tv_income = (TextView) findViewById(R.id.text2);

		tv_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(0);
			}
		});
		tv_income.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(1);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_enter, R.anim.pop_activity);
			finish();
			return false;
		}
		return false;
	}
}
