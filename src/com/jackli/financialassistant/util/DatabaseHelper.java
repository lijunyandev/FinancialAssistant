package com.jackli.financialassistant.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	 //数据库名
    private static String DATABASE_NAME = "FinancialAssistant.db";
    //数据库版本号
    private static final int VERSION = 1;


    // 创建支出表
    String tablepay = "create table if not exists accountpay("
        + "id integer PRIMARY KEY,"
        + "moneypay varchar(10),"
        + "categorypay varchar(20),"
        + "accountnumberpay varchar(20),"
        + "timepay varchar(50),"
        + "projectpay varchar(20),"
        + "memberpay varchar(20),"
        + "notepay varchar(200));";

    // 创建收入表
    String tableincome = "create table if not exists accountincome("
        + "id integer PRIMARY KEY,"
        + "moneyincome varchar(10),"
        + "categoryincome varchar(20),"
        + "accountnumberincome varchar(20),"
        + "timeincome varchar(50),"
        + "projectincome varchar(20),"
        + "memberincome varchar(20),"
        + "noteincome varchar(200));";

    // 创建备忘录表
    String tablenote = "create table if not exists notepad(" +
            "id integer PRIMARY KEY," +
            "timenote varchar(20)," +
            "contentnote varchar(300));";


    public DatabaseHelper(Context context) {

        // 数据库实际被创建是在getWritableDatabase()或getReadableDatabase()方法调用时
        super(context, DATABASE_NAME, null, VERSION);
        // CursorFactory设置为null,使用系统默认的工厂类


    }

    /* 调用时间：数据库第一次创建时onCreate()方法会被调用
     * onCreate方法有一个 SQLiteDatabase对象作为参数，根据需要对这个对象填充表和初始化数据
     * 这个方法中主要完成创建数据库后对数据库的操作
     * 即便程序修改重新运行，只要数据库已经创建过，就不会再进入这个onCreate方法
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tablepay);

        db.execSQL(tableincome);

        db.execSQL(tablenote);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
	
}
