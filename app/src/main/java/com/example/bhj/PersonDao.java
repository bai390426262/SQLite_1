package com.example.bhj;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bhj on 2018/9/15.
 */


/**
 * SQLiteDatabase类概述
     公开管理SQLite数据库的方法。
     SQLiteDatabase有创建、删除、执行SQL命令和执行其他常见数据库管理任务的方法

 *只有通过SQLiteOpenHelper类来得到SQLiteDatabase对象,得到这个对象来连接数据库
 *
 * 所以在PersonDao构造函数中把PersonSQLiteOpenHelper来初始化,得到他的一个对象
 */

public class PersonDao {
    private  PersonSQLiteOpenHelper mOpenHelper; // 数据库的帮助类对象

    public PersonDao(Context context){
        mOpenHelper = new PersonSQLiteOpenHelper(context);
    }

    /**
     * 添加到person表一条数据
     * @param person  传入person的一个实体类,里面定义了id,name,age
     */
    public void insert(Person person){

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        if (db.isOpen()){ //如果数据库打开,执行添加的操作

            db.execSQL("INSERT INTO person(name,age) values(?,?);",new Object[]{person.getName(),person.getAge()});

            db.close();//关闭数据库
        }
    }


    /**
     * 更据id删除记录
     * @param id
     */
    public void delete(int id) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();	// 获得可写的数据库对象
        if(db.isOpen()) {	// 如果数据库打开, 执行添加的操作

            db.execSQL("delete from person where id =?;", new Integer[]{id});

            db.close();	// 数据库关闭
        }
    }

    /**
     * 根据id找到记录, 并且修改姓名
     * @param id
     * @param name
     */
    public void update(int id, String name) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        if(db.isOpen()) {	// 如果数据库打开, 执行添加的操作

            db.execSQL("update person set name = ? where id = ?;", new Object[]{name, id});

            db.close();	// 数据库关闭
        }
    }


    /**
     * 查询所有的数据
     * @return  person
     */
    public List<Person> queryAll(){

        SQLiteDatabase db = mOpenHelper.getReadableDatabase(); // 获得一个只读的数据库对象

        if (db.isOpen()){

            Cursor cursor = db.rawQuery("select id, name, age from person;", null);

            if (cursor != null && cursor.getCount()>0){//如果游标读到的行有数据

                List<Person> personList = new ArrayList<Person>();
                int id;
                String name;
                int age;

                while (cursor.moveToNext()){

                    id = cursor.getInt(0);//取第0列的数据 id  列是从0开始的,有数据的行第一行是1
                    name = cursor.getString(1);
                    age = cursor.getInt(2);

                    personList.add(new Person(id,name,age));
                }
                cursor.close();
                db.close();
                return personList;

            }

            db.close();
        }

        return null;
    }

    public Person queryItem(int id){

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();// 获得一个只读的数据库对象

        if (db.isOpen()){

            Cursor cursor = db.rawQuery("select id,name,age from person where id=? ;",new String[]{id+""});

            if (cursor!=null&&cursor.moveToFirst()){
                int _id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);
                db.close();
                return new Person(_id, name, age);
            }
            cursor.close();
            db.close();
        }

        return null;
    }

}
