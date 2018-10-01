package com.example.bhj;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bhj on 2018/9/29.
 */
public class TestPersonDao extends AndroidTestCase {
    @Before
    public void setUp() throws Exception {
        // 数据库什么时候创建
        PersonSQLiteOpenHelper openHelper = new PersonSQLiteOpenHelper(getContext());

        // 第一次连接数据库时创建数据库文件. onCreate会被调用
        openHelper.getReadableDatabase();
    }

    @Test
    public void testInsert() throws Exception {

        PersonDao dao = new PersonDao(getContext());
        dao.insert(new Person(0, "张三", 88));
        dao.insert(new Person(1, "李四", 77));
        dao.insert(new Person(2, "王五", 66));
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void queryAll() throws Exception {
    }

    @Test
    public void queryItem() throws Exception {
    }

}