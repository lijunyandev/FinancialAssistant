package com.jackli.financialassistant.notepad;

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
import com.jackli.financialassistant.mainactivity.MainActivity;
import com.jackli.financialassistant.util.DBManager;
import com.jackli.financialassistant.util.FontManager;

public class NotepadActivity extends Activity {
	
	private Button notepadBack;
	private Button notepadNew;
	private String[] nTime = DBManager.getDateNote();
	private String[] nContent = DBManager.getContentNote();
	private int[] nId = DBManager.getIdNote();
	private ListView noteList;
	private boolean delNoteFlag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		if (nTime.length == 0) {
			setContentView(R.layout.notepad_nothing);
		} else {
			setContentView(R.layout.notepad_main);
			List<Map<String, Object>> noteListItems = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < nTime.length; i++) {
				Map<String, Object> noteListItem = new HashMap<String, Object>();
				noteListItem.put("nTime", nTime[i]);
				noteListItem.put("nContent", nContent[i]);
				noteListItems.add(noteListItem);
			}
			SimpleAdapter simpleAdapter = new SimpleAdapter(this,noteListItems, 
					R.layout.notepad_list_item, new String[] {"nTime", "nContent" },
					new int[] {R.id.notelist_time, R.id.notelist_content});
			noteList = (ListView) findViewById(R.id.note_main_list);
			noteList.setAdapter(simpleAdapter);

			noteList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String[] noteStr = new String[2];
					noteStr[0] = nTime[position];
					noteStr[1] = nContent[position];
					listDialog(noteStr);
				}
			});
			
			noteList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
				@Override
				public void onCreateContextMenu(ContextMenu menu, View v,
						ContextMenuInfo menuInfo) {
					menu.setHeaderTitle("执行操作");
					menu.add(0, 0, 0, "删除");

				}
			});

			FontManager.initTypeface(this);
			ViewGroup vg = FontManager.getContentView(this);
			FontManager.changeFont(vg, this);
		}

		notepadBack = (Button) findViewById(R.id.notepad_main_back_btn);
		notepadBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NotepadActivity.this, MainActivity.class);
				startActivity(intent);
				//overridePendingTransition(R.anim.main_back, R.anim.add_back);
				finish();
			}
		});
		
		notepadNew = (Button) findViewById(R.id.notepad_main_new);
		notepadNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NotepadActivity.this, NotepadEditActivity.class);
				startActivity(intent);
				NotepadActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = contextMenuInfo.position;
		switch (item.getItemId()) {
		case 0:
			delNoteFlag = DBManager.deleteNoteList(nId[position]);
			if (delNoteFlag) {
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
			//重启一个自身活动，刷新
			Intent intent = new Intent(NotepadActivity.this, NotepadActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	private void listDialog(String[] info) {
		new AlertDialog.Builder(this).setTitle("详细信息")
				.setMessage(info[0] + "\n\n\t" + info[1])
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
					}
				}).show();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("详细信息");
		builder.setMessage(info[0] + "\n\n\t" + info[1]);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		builder.show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				|| keyCode == KeyEvent.KEYCODE_HOME
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent(NotepadActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			return false;
		}
		return false;
	}
}
