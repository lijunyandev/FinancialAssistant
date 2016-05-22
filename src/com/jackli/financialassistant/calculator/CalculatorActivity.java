package com.jackli.financialassistant.calculator;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jackli.financialassistant.R;
import com.jackli.financialassistant.mainactivity.MainActivity;
import com.jackli.financialassistant.util.FontManager;


public class CalculatorActivity extends Activity implements OnClickListener {
	private String string = "0";
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;//10个数字键
	private Button btnadd, btnsub, btnmul, btndiv,//加减乘除
				   btneq, btnpoint, btndel,btnback, btnc;//"="  "."  "del"删除     "back"返回     "c"清除
	private TextView tv1, tv_calculate;//结果栏、计算栏
	private GetValue getValue = new GetValue();
	private Judge judge = new Judge();
	private boolean flag = false;	//每一次计算（“=”）后--true 再点任意键 --false 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.calculator_main);
		//初始化各个按键及监听事件
		init();
		//改变控件字体
		FontManager.initTypeface(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFont(vg, this);
	}
	
	private void init() {
		btn1 = (Button) this.findViewById(R.id.one);
		btn2 = (Button) this.findViewById(R.id.two);
		btn3 = (Button) this.findViewById(R.id.three);
		btn4 = (Button) this.findViewById(R.id.four);
		btn5 = (Button) this.findViewById(R.id.five);
		btn6 = (Button) this.findViewById(R.id.six);
		btn7 = (Button) this.findViewById(R.id.seven);
		btn8 = (Button) this.findViewById(R.id.eight);
		btn9 = (Button) this.findViewById(R.id.nine);
		btn0 = (Button) this.findViewById(R.id.zero);
		btnc = (Button) this.findViewById(R.id.c);
		btnadd = (Button) this.findViewById(R.id.add);
		btnsub = (Button) this.findViewById(R.id.subtract);
		btnmul = (Button) this.findViewById(R.id.multiple);
		btndiv = (Button) this.findViewById(R.id.division);
		btneq = (Button) this.findViewById(R.id.eq);
		btnpoint = (Button) this.findViewById(R.id.point);
		btndel = (Button) this.findViewById(R.id.del);
		tv1 = (TextView) this.findViewById(R.id.calculator_result_tv);
		tv_calculate = (TextView) this.findViewById(R.id.calculator_calculate_tv);
		btnback = (Button) this.findViewById(R.id.calculator_btn_back);

		btn0.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btnc.setOnClickListener(this);
		btnadd.setOnClickListener(this);
		btndel.setOnClickListener(this);
		btndiv.setOnClickListener(this);
		btneq.setOnClickListener(this);
		btnmul.setOnClickListener(this);
		btnpoint.setOnClickListener(this);
		btnsub.setOnClickListener(this);
		btnback.setOnClickListener(this);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem exit = menu.add("EXIT");
		exit.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				System.exit(0);
				return true;
			}
		});
		return true;
	}

	@Override
	public void onClick(View v) {
		//如果返回的是“error”或者达到无穷大则设为0
		if ("error".equals(tv_calculate.getText().toString())
				|| "∞".equals(tv_calculate.getText().toString())) {
			string = "0";
		}
		if (v == this.btn0) {
			string = judge.digit_judge(string, "0", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn1) {
			string = judge.digit_judge(string, "1", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn2) {
			string = judge.digit_judge(string, "2", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn3) {
			string = judge.digit_judge(string, "3", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn4) {
			string = judge.digit_judge(string, "4", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn5) {
			string = judge.digit_judge(string, "5", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn6) {
			string = judge.digit_judge(string, "6", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn7) {
			string = judge.digit_judge(string, "7", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn8) {
			string = judge.digit_judge(string, "8", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btn9) {
			string = judge.digit_judge(string, "9", flag);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btneq) {
			//计算“=”
			tv1.setText(string + "=");
			string = getValue.alg_dispose(string);	//获取运算得到的值
			string = judge.digit_dispose(string);	//对结果进行判断
			flag = true;
			tv_calculate.setText(string);
		} else if (v == this.btnc) {
			//清空各项
			string = "";
			tv1.setText(string);
			string = "0";
			tv_calculate.setText(string);
			flag = false;
		} else if (v == this.btnpoint) {
			//判断对“.”进行什么样的处理
			string = judge.judgePoint(string);
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btndel) {
			//删除运算
			if (!"0".equals(string)) {
				string = string.substring(0, string.length() - 1);
				if (0 == string.length())
					string = "0";
			}
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btnadd) {
			string = judge.judge(string, "+");
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btnsub) {
			string = judge.judge(string, "-");
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btnmul) {
			string = judge.judge(string, "×");
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btndiv) {
			string = judge.judge(string, "÷");
			flag = false;
			tv_calculate.setText(string);
		} else if (v == this.btnback) {
			Intent intent = new Intent(CalculatorActivity.this, MainActivity.class);
			startActivity(intent);
			//overridePendingTransition(R.anim.main_back, R.anim.add_back);
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& keyCode == KeyEvent.KEYCODE_HOME
				|| event.getRepeatCount() == 0) {
			Intent intent = new Intent(CalculatorActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return false;
		}
		return false;
	}
}
