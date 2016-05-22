package com.jackli.financialassistant.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FontManager {
    public static Typeface typeface;    // 定义自定义字体

    //初始化字体(方正卡通简体)
    public static void initTypeface(Activity activity){
        if (typeface == null){
            typeface = Typeface.createFromAsset(activity.getAssets(),"fonts/newfont.ttf");
        }
    }

    // 转换字体
    public static void changeFont(ViewGroup viewGroup,Activity activity){
        View view;
        for (int i = 0;i < viewGroup.getChildCount();i++ ){
            view = viewGroup.getChildAt(i);
            if (view instanceof TextView) {             //instanceof是Java的一个二元操作符，和==，>，<是同一类东西。由于它是由字母组成的，
                ((TextView)view).setTypeface(typeface); // 所以也是Java的保留关键字。它的作用是测试它左边的对象是否是它右边的类的实例。
            }else if (view instanceof EditText){        // 返回boolean类型的数据。
                ((EditText)view).setTypeface(typeface);
            }else if (view instanceof Button){
                ((Button) view).setTypeface(typeface);
            }else if (view instanceof ViewGroup) {
                changeFont((ViewGroup) view, activity);
            }
        }
    }

    //获得传过来的activity,若该activity的内容大于0并且其中的控件属于ViewGroup，则获取该控件并返回
    public static ViewGroup getContentView(Activity activity) {
        ViewGroup systemContent = (ViewGroup) activity.getWindow().getDecorView()
                .findViewById(android.R.id.content);
        ViewGroup content = null;
        if (systemContent.getChildCount() > 0
                && systemContent.getChildAt(0) instanceof ViewGroup) {
            content = (ViewGroup) systemContent.getChildAt(0);
        }
        return content;
    }
    

}
