package com.example.bhj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类,用于创建和管理数据库.
 * Created by bhj on 2018/9/11.
 */

public class PersonSQLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * 数据库的构造方法
     * @param context
     * name 数据库名称
     * factory 游标工程
     * version 数据库版本,不能小于1
     */
    public PersonSQLiteOpenHelper(Context context) {
        super(context, "bhj.db", null, 2);
    }


    /**
     * 数据库创建时回调此方法
     * 初始化一些表格
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        //操作数据库
         String spl = "create table person(id integer primary key,name varchar(20),age integer);";
         db.execSQL(spl);//生成一个名叫person的表
    }


    /**
     * 数据库的版本号更新时回调此方法
     * 更新数据库的内容(删除表,增加表,修改表)
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion == 1 && newVersion == 2){

            // 在person表中添加一个余额列balance
            db.execSQL("alter table person add balance integer;");
        }
    }
}

