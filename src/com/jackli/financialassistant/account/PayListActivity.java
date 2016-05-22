package com.jackli.financialassistant.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.jackli.financialassistant.R;
import com.jackli.financialassistant.util.DBManager;
import com.jackli.financialassistant.util.FontManager;



public class PayListActivity extends Activity {
	
	private String[] pMoney = DBManager.getMoneyPay();
	private String[] pCategory = DBManager.getCategoryPay();
	private String[] pTime = DBManager.getTimePay();
	private int[] pId = DBManager.getIdPay();
	private ListView payListView;
	private Button btn_payback;
	private Button btn_newPayList;
	private boolean delPayFlag = false;

	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
//		if (pTime.length == 0) {
//			setContentView(R.layout.paylistnothing);
//		} else {
			setContentView(R.layout.accout_pay_list);
			List<Map<String, Object>> payListItems = new ArrayList<Map<String, Object>>();
			Log.d("PayListActivity", "onCreate pTime ="+pTime);
			Log.d("PayListActivity", "onCreate pTime.length ="+pTime.length);
			if (pTime.length != 0) {
				for (int i = 0; i < pTime.length; i++) {
					Map<String, Object> payListItem = new HashMap<String, Object>();
					payListItem.put("pTime", pTime[i]);
					payListItem.put("pCategory", pCategory[i]);
					payListItem.put("pMoney", pMoney[i]);
					payListItems.add(payListItem);
				}
				//构造适配器
				SimpleAdapter simpleAdapter = new SimpleAdapter(this, payListItems,
						R.layout.account_list_item, new String[] { "pTime", "pCategory","pMoney" },
						new int[] { R.id.list_time,R.id.list_category, R.id.list_money });
				
				payListView = (ListView) findViewById(R.id.paylist_list);
				payListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						String[] str = DBManager.getPayListStr(pTime[position],
								pCategory[position], pMoney[position]);
						for (int i = 0; i < str.length; i++) {
							Log.d("PayListActivity", "onCreate str[] = "+str[i]);
						}
						listDialog(str);
					}
				});
				//??????????????
				payListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenuInfo menuInfo) {
						menu.setHeaderTitle("执行操作");
						menu.add(0, 0, 0, "删除");

					}
				});
				payListView.setAdapter(simpleAdapter);
			}
//		}

		//新建按钮，跳转到账户活动
		btn_newPayList = (Button) findViewById(R.id.btn_paylist_new);
		btn_newPayList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PayListActivity.this, AccountActivity.class);
//			1.  Bundle bundle = new Bundle();
//				bundle.putInt("viewItem", 0);
//				intent.putExtras(bundle);
//	        2.	
				intent.putExtra("viewItem", 0);
				startActivity(intent);
				finish();
			}
		});
		
		//返回
		btn_payback = (Button) findViewById(R.id.btn_paylist_back);
		btn_payback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PayListActivity.this, AccountActivity.class);
				startActivity(intent);
				finish();
			}
		});

		FontManager.initTypeface(this);
		ViewGroup vg = FontManager.getContentView(this);
		FontManager.changeFont(vg, this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& keyCode == KeyEvent.KEYCODE_HOME
				|| event.getRepeatCount() == 0) {
			Intent intent = new Intent(PayListActivity.this, AccountActivity.class);
			startActivity(intent);
			PayListActivity.this.finish();
			return false;
		}
		return false;
	}

	//???????????????????????????
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = contextMenuInfo.position;
		switch (item.getItemId()) {
		case 0:
			delPayFlag = DBManager.deletePayList(pId[position]);
			
			if (delPayFlag) {
				Toast toast = Toast.makeText(getApplicationContext(), "删除成功",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				Toast toast = Toast.makeText(getApplicationContext(), "删除失败",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			Intent intent = new Intent(PayListActivity.this, PayListActivity.class);
			startActivity(intent);
			PayListActivity.this.finish();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	//显示选择的账单的详细信息
	private void listDialog(String[] info) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("详细信息");
		builder.setMessage(info[0] + "\n\n类型:\t" + 
						   info[1] + "\n金额:\t" + info[2] + "元" + "\n消费账户:" +
						   info[3] + "\n消费项目:\t"+ info[4] + "\n消费成员:\t" + 
						   info[5] + "\n\n备注:  "+ info[6]);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}

}
