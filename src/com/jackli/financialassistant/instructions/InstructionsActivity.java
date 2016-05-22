package com.jackli.financialassistant.instructions;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.jackli.financialassistant.R;
import com.jackli.financialassistant.mainactivity.MainActivity;
import com.jackli.financialassistant.util.FontManager;
import com.jackli.financialassistant.welcomepage.GuideActivity;



public class InstructionsActivity extends Activity {
	
	private ListView listView;
	private Button btn_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.instructions);
		
		//如果点击的是listview第二个功能说明，则跳转到引导活动
		listView = (ListView) this.findViewById(R.id.instructions_listView);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 1) {
					Intent intent = new Intent(InstructionsActivity.this,GuideActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.push_activity, R.anim.main_exit);
					finish();
				}
			}
		});

		//如果点击退出则返回到主活动
		btn_back = (Button) this.findViewById(R.id.instructions_bn_back);
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InstructionsActivity.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.main_enter, R.anim.pop_activity);
				finish();
			}
		});

		// 更改字体
		FontManager.initTypeface(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFont(vg, this);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent(InstructionsActivity.this, MainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.main_enter, R.anim.pop_activity);
			finish();
			return false;
		}
		return false;
	}
}
