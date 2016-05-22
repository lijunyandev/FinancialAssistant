package com.jackli.financialassistant.welcomepage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.jackli.financialassistant.R;
import com.jackli.financialassistant.mainactivity.MainActivity;
import com.jackli.financialassistant.util.Constants;
import com.jackli.financialassistant.util.FontManager;

public class WelcomePageActivity extends Activity {
	  private Bitmap bitmap;
	    private ImageView imageView;
	    private Handler handler;
	    private boolean isFirstIn = false;

	    private static final int LOWBRIGHTNESS = -230;  //设置一个亮度最小值
	    private static final double everycut = 5;
	    private int brightness = 70;        //亮度值

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
	        setContentView(R.layout.welcome_page);
	        //从资源获取图片的Bitmap
	        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_android);
	        imageView = (ImageView)findViewById(R.id.iv_welcome);
	        handler = new Handler(){
	            @Override
	            public void handleMessage(Message msg) {
	                switch (msg.what){
	                    case Constants.DECREASE:
	                        brightness -= everycut * 1.05;  //减小亮度参数的值
	                        brightChanged(brightness, bitmap, imageView);   //设置图片亮度为新的值
	                        break;
	                    case Constants.GO_INDEX:
	                        goIndex();
	                        break;
	                    case Constants.GO_GUIDE:
	                        goGuide();
	                        break;
	                }
	                super.handleMessage(msg);
	            }
	        };
	        new Thread(){       //开启改变图片亮度参数的线程
	            @Override
	            public void run() {
	                while (true) {
	                    handler.sendEmptyMessage(Constants.DECREASE);// Handler发出请求
	                    if (brightness < LOWBRIGHTNESS) {   //判断亮度是否小于定义的最小值
	                        init();             //亮度小于定义的最小值则执行初始化方法
	                        break;              //结束线程
	                    }
	                    try {
	                        Thread.sleep(100);
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	                super.run();
	            }
	        }.start();
	        
	        //设置字体为方正卡通
	        FontManager.initTypeface(this);
	        ViewGroup vg = FontManager.getContentView(this);
	        FontManager.changeFont(vg,this);
	    }

	    public void init(){
	        SharedPreferences pref = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME,MODE_PRIVATE);
	        isFirstIn = pref.getBoolean("isFirstIn",true);
	        if (isFirstIn){
	            //如果是第一次进则发送要跳转到应用的引导页的消息，发送消息的延迟时间为100毫秒
	            handler.sendEmptyMessageDelayed(Constants.GO_GUIDE,Constants.KEEP_INDEX_TIME);
	        }else{
	            //如果不是第一次进则发送要跳转到应用的首页的消息，发送消息的延迟时间为100毫秒
	            handler.sendEmptyMessageDelayed(Constants.GO_INDEX,Constants.KEEP_INDEX_TIME);
	        }
	    }

	    //?????????????
	    public void brightChanged(int brightness, Bitmap bitmap,ImageView imageView) {
	        //获得图片的高和宽
	        int imgHeight = bitmap.getHeight();
	        int imgWidth = bitmap.getWidth();
	        //创建一个新的Bitmap对象
	        Bitmap bmp = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888); //宽、高、色彩模式
	        ColorMatrix cMatrix = new ColorMatrix();    //创建颜色矩阵
	        cMatrix.set(                                //设置亮度
	                new float[] {
	                        1, 0, 0, 0, brightness, // 改变亮度
	                        0, 1, 0, 0, brightness,
	                        0, 0, 1, 0, brightness,
	                        0, 0, 0, 1, 0
	                });

	        Paint paint = new Paint();      //创建画笔
	        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));  //设置画笔的滤镜效果
	        Canvas canvas = new Canvas(bmp);    //以Bitmap对象创建画布
	        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了
	        canvas.drawBitmap(bitmap, 0, 0, paint);
	        imageView.setImageBitmap(bmp);  //在ImageView中加载图片
	    }

	    private void goIndex() {
	        Intent intent = new Intent(WelcomePageActivity.this, MainActivity.class);
	        startActivity(intent);
	        finish();
	    }

	    private void goGuide() {
	        Intent intent = new Intent(WelcomePageActivity.this, GuideActivity.class);
	        startActivity(intent);
	        finish();
	    }
}
