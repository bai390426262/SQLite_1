package com.example.bhj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView mListView=findViewById(R.id.list_view);




        //得到数据库的数据
        PersonDao dao = new PersonDao(this);

        for (int i = 0;i<50;i++){
            dao.insert(new Person(i,"陈子龙"+i,i));
        }

        personList = dao.queryAll();

        //把view层对象ListView和控制器BaseAdapter关联起来
        mListView.setAdapter(new MyAdapter());
    }


    /**
     * 数据适配器
     */
    class MyAdapter extends BaseAdapter{

        @Override

        /**
         * 定义ListView的数据长度，也就是item的个数
         */
        public int getCount() {
            return personList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * 返回ListView中指定某一行的view对象，也就是item对象
         * @param position 当前view的索引位置
         * @param convertView  缓存对象
         * @param parent ListView
         * @return
         */
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView mTextView = null;

            if (convertView != null){ //判断缓存对象是否null，不为null时已经缓存了对象

                mTextView = (TextView) convertView;

            }else {  //等于null，说明第一次显示，新创建

                mTextView = new TextView(MainActivity.this);

            }

            mTextView.setTextSize(20);

            Person person = personList.get(position);//获得指定位置的数据，进行对TextView的绑定

            mTextView.setText(person.toString());

            return mTextView;
        }
    }

}

