package com.example.liwenguang.lwg.c_CustomView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liwenguang.lwg.R;

import java.util.ArrayList;

public class Custom_z_i_activity extends AppCompatActivity {

    private ListView lv_main;
    private ArrayList<Custom_z_i_MyBean> mybeans;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_custom_i_main);
        lv_main = (ListView) findViewById(R.id.c_i_lv_main);

        //设置适配器
        //准备数据
        mybeans = new ArrayList<Custom_z_i_MyBean>();
        for (int i=0; i<100; i++){
            mybeans.add(new Custom_z_i_MyBean("Content" + i));
        }

        myAdapter = new MyAdapter();
        lv_main.setAdapter(myAdapter);

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mybeans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = View.inflate(Custom_z_i_activity.this, R.layout.item_custom_i_main, null);
                viewHolder.item_content = (TextView) convertView.findViewById(R.id.c_i_item_content);
                viewHolder.item_menu = (TextView) convertView.findViewById(R.id.c_i_item_menu);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到内容
            final Custom_z_i_MyBean mybean = mybeans.get(position);
            viewHolder.item_content.setText(mybean.getName());
            viewHolder.item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Custom_z_i_MyBean myBean1 = mybeans.get(position);
                    Toast.makeText(Custom_z_i_activity.this, "content" + mybean.getName(), Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.item_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Custom_z_i_SlideLayout slideLayout = (Custom_z_i_SlideLayout) v.getParent();
                    slideLayout.closeMenu();
                    mybeans.remove(mybean);
                    //myAdapter.notifyDataSetChanged();
                    notifyDataSetChanged();
                }
            });

            Custom_z_i_SlideLayout slideLayout = (Custom_z_i_SlideLayout) convertView;
            slideLayout.setOnStateChangeListener( new MyOnStateChangeListener());
            return convertView;
        }
    }

    private Custom_z_i_SlideLayout slideLayout;

    class MyOnStateChangeListener implements Custom_z_i_SlideLayout.OnStateChangeListener{

        @Override
        public void onClose(Custom_z_i_SlideLayout slide) {
            if (slideLayout == slide){
                slideLayout = null;
            }
        }

        @Override
        public void onDown(Custom_z_i_SlideLayout slide) {
            if (slideLayout != null && slideLayout != slide){
                slideLayout.closeMenu();
            }
        }

        @Override
        public void onOpen(Custom_z_i_SlideLayout slide) {
            slideLayout = slide;
        }
    }
    static class ViewHolder{
        TextView item_content;
        TextView item_menu;
    }


}
