package com.example.liwenguang.lwg.c_CustomView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.liwenguang.lwg.R;

import java.util.ArrayList;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_z_c_popactivity extends AppCompatActivity {

    private EditText et_input;
    private ImageView iv_down;
    private PopupWindow popupWindow;
    private ListView listview;

    private ArrayList<String> msgs;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_custom_c_pop);

        et_input = (EditText) findViewById(R.id.c_CustomView_c_one_et);
        iv_down = (ImageView) findViewById(R.id.c_CustomView_c_one_iv);
        iv_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null){
                    popupWindow = new PopupWindow(Custom_z_c_popactivity.this);
                    popupWindow.setWidth(et_input.getWidth());
                    int height = DensityUtil.dip2px(Custom_z_c_popactivity.this, 200);
                    popupWindow.setHeight(height);
                    popupWindow.setContentView(listview);
                    popupWindow.setFocusable(true); //设置焦点
                }

                popupWindow.showAsDropDown(et_input, 0, 0);
            }
        });

        listview = new ListView(this);
        listview.setBackgroundResource(R.drawable.c_c_listview_background);

        msgs = new ArrayList<String>();
        for (int i=0; i<500; i++){
            msgs.add(i+"--aaaaaaaaa--"+i);
        }
        myAdapter = new MyAdapter();
        listview.setAdapter(myAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1得到数据
                String msg = msgs.get(position);
                //设置输入框
                et_input.setText(msg);
                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return msgs.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = View.inflate(Custom_z_c_popactivity.this, R.layout.item_custom_list, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
                viewHolder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            final String msg = msgs.get(position);
            viewHolder.tv_msg.setText(msg);

            //设置删除
            viewHolder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从集合删除
                    //刷新UI-适配器
                    msgs.remove(msg);
                    myAdapter.notifyDataSetChanged();   //getcount -> getview
                }
            });
            return convertView;
        }
    }
    static class ViewHolder{
        TextView tv_msg;
        ImageView iv_delete;
    }
}
