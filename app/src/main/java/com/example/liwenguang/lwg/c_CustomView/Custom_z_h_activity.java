package com.example.liwenguang.lwg.c_CustomView;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liwenguang.lwg.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.view.View.GONE;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_z_h_activity extends AppCompatActivity{

    private ListView lv_main;
    private TextView tv_word;
    private Custom_z_h_IndexView iv_word;

    private Handler handler = new Handler();

    private ArrayList<Custom_z_h_Person> persons;
    private  IndexAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_custom_h_main);

        lv_main = (ListView) findViewById(R.id.c_h_listview);
        tv_word = (TextView) findViewById(R.id.c_h_tv);
        iv_word = (Custom_z_h_IndexView) findViewById(R.id.c_h_iv_words);

        iv_word.setOnIndexChangeListener(new MyOnIndexChangeListener());

        initData();
        //设置适配器
        adapter = new IndexAdapter();
        lv_main.setAdapter(adapter);
    }

    class IndexAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return persons.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = LayoutInflater.from(Custom_z_h_activity.this).inflate(R.layout.item_custom_h_main, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_word = (TextView) convertView.findViewById(R.id.c_h_item_tv_word);
                viewHolder.tv_name = (TextView) convertView.findViewById(R.id.c_h_item_tv_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String word = persons.get(position).getPinyin().substring(0,1);
            String name = persons.get(position).getName();
            viewHolder.tv_word.setText(word);
            viewHolder.tv_name.setText(name);
            if (position == 0){
                viewHolder.tv_word.setVisibility(View.VISIBLE);
            }else{
                String preword = persons.get(position-1).getPinyin().substring(0,1);
                if (word.equals(preword)){
                    viewHolder.tv_word.setVisibility(GONE);
                }else{
                    viewHolder.tv_word.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
    static class ViewHolder{
        TextView tv_word;
        TextView tv_name;
    }


    class MyOnIndexChangeListener implements Custom_z_h_IndexView.OnIndexChangeListener {

        @Override
        public void onIndexChange(String word) {
            updateWord(word);
            updateListView(word);
        }
    }

    private void updateWord(String word) {
        tv_word.setVisibility(View.VISIBLE);
        tv_word.setText(word);
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //也是运行在主线程
                Log.d("Tag",Thread.currentThread().getName() + ".");
                tv_word.setVisibility(GONE);
            }
        }, 1000);
    }

    private void updateListView(String word) {
        for (int i=0; i<persons.size(); i++){
            String w = persons.get(i).getPinyin().substring(0,1);  //listview的拼音
            if (w.equals(word)){
                lv_main.setSelection(i);
                return;
            }

        }
    }


    /**
     * 初始化数据
     */
    private void initData() {

        persons = new ArrayList<>();
        persons.add(new Custom_z_h_Person("李四"));
        persons.add(new Custom_z_h_Person("张三"));
        persons.add(new Custom_z_h_Person("王五"));
        persons.add(new Custom_z_h_Person("王麻子"));

        persons.add(new Custom_z_h_Person("李文广"));
        persons.add(new Custom_z_h_Person("韩笑"));
        persons.add(new Custom_z_h_Person("周伟杰"));
        persons.add(new Custom_z_h_Person("小老板"));

        persons.add(new Custom_z_h_Person("富帅"));
        persons.add(new Custom_z_h_Person("老孔"));
        persons.add(new Custom_z_h_Person("蒋欣"));
        persons.add(new Custom_z_h_Person("陈正"));
        persons.add(new Custom_z_h_Person("王玉木"));

        persons.add(new Custom_z_h_Person("王英杰"));
        persons.add(new Custom_z_h_Person("李振南"));
        persons.add(new Custom_z_h_Person("孙仁政"));
        persons.add(new Custom_z_h_Person("唐春雷"));
        persons.add(new Custom_z_h_Person("牛鹏伟"));
        persons.add(new Custom_z_h_Person("姜宇航"));

        persons.add(new Custom_z_h_Person("刘挺"));
        persons.add(new Custom_z_h_Person("张洪瑞"));
        persons.add(new Custom_z_h_Person("张建忠"));
        persons.add(new Custom_z_h_Person("侯亚帅"));
        persons.add(new Custom_z_h_Person("刘帅"));

        persons.add(new Custom_z_h_Person("乔竞飞"));
        persons.add(new Custom_z_h_Person("徐雨健"));
        persons.add(new Custom_z_h_Person("吴亮"));
        persons.add(new Custom_z_h_Person("王兆霖"));

        persons.add(new Custom_z_h_Person("阿三"));
        persons.add(new Custom_z_h_Person("李博俊"));


        //排序
        Collections.sort(persons, new Comparator<Custom_z_h_Person>() {
            @Override
            public int compare(Custom_z_h_Person lhs, Custom_z_h_Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });

    }
}
