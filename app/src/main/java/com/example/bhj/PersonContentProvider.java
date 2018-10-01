package com.example.bhj;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by bhj on 2018/9/29.
 */

public class PersonContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.bhj" + ".PersonContentProvider";
    private static UriMatcher uriMatcher;
    private PersonSQLiteOpenHelper mOpenHelper;

    private static final int PERSON_INSERT_CODE = 0;//操作person表添加操作的uri匹配码
    private static final int PERSON_DELETE_CODE = 1;
    private static final int PERSON_UPDATE_CODE = 2;
    private static final int PERSON_QUERY_ALL_CODE = 3;
    private static final int PERSON_QUERY_ITEM_CODE = 4;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);//匹配不成功返回NO_MATCH，-1

        //添加一些uri（分机号）   AUTHORITY这和相当于总手机号，具体的业务操作由下面决定

        //content://com.example.bhj.PersonContentProvider/person/insert
        uriMatcher.addURI(AUTHORITY, "person/insert", PERSON_INSERT_CODE);
        uriMatcher.addURI(AUTHORITY, "person/delete", PERSON_DELETE_CODE);
        uriMatcher.addURI(AUTHORITY, "person/update", PERSON_UPDATE_CODE);
        uriMatcher.addURI(AUTHORITY, "person/queryAll", PERSON_QUERY_ALL_CODE);
        // content://com.example.bhj.PersonContentProvider/person/query/#
        uriMatcher.addURI(AUTHORITY, "person/query/#", PERSON_QUERY_ITEM_CODE);

    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new PersonSQLiteOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {

            case PERSON_QUERY_ALL_CODE:// 查询所有人的uri

                if (db.isOpen()) {

                    Cursor cursor = db.query("person", projection, selection, selectionArgs,
                            null, null, sortOrder);

                    return cursor;

                    // db.close();  返回cursor结果集时候，不可以关闭数据库
                }

                break;

            case PERSON_QUERY_ITEM_CODE:    // 查询的是单条数据, uri末尾出有一个id
                if (db.isOpen()) {

                    long id = ContentUris.parseId(uri);

                    Cursor cursor = db.query("person", projection, "id = ?",

                            new String[]{id + ""}, null, null, sortOrder);

                    return cursor;
                }
                break;

            default:

                throw new IllegalArgumentException("uri不匹配" + uri);
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {

            case PERSON_QUERY_ALL_CODE:

                return "vnd.android.cursor.dir/person";//表示返回多条数据

            default:

                break;
        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        switch (uriMatcher.match(uri)) {

            case PERSON_INSERT_CODE: //添加人到person表中 case语句后面必须是常量，使用final修饰

                SQLiteDatabase db = mOpenHelper.getWritableDatabase();

                if (db.isOpen()) {

                    long id = db.insert("person", null, values);

                    db.close();

                    //返回 content://com.example.bhj.PersonContentProvider/person/insert/id
                    return ContentUris.withAppendedId(uri, id);
                }


                break;

            default:
                throw new IllegalArgumentException("uri不匹配" + uri);

        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable
            String[]
            selectionArgs) {

        switch (uriMatcher.match(uri)) {

            case PERSON_DELETE_CODE: //删除操作

                SQLiteDatabase db = mOpenHelper.getWritableDatabase();

                if (db.isOpen()) {

                    int count = db.delete("person", selection, selectionArgs);

                    db.close();

                    return count;
                }


                break;

            default:
                throw new IllegalArgumentException("uri不匹配" + uri);
        }

        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,

                      @Nullable String selection,

                      @Nullable String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {

            case PERSON_UPDATE_CODE: // 更新person表的操作

                SQLiteDatabase db = mOpenHelper.getWritableDatabase();

                if (db.isOpen()) {

                    int count = db.update("person", values, selection,
                            selectionArgs);
                    db.close();
                    return count;
                }
                break;
            default:
                throw new IllegalArgumentException("uri不匹配: " + uri);
        }

        return 0;
    }
}
