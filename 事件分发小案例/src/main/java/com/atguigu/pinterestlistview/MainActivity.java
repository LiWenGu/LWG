package com.atguigu.pinterestlistview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView lv1;
	private ListView lv2;
	private ListView lv3;
	private int[] tu = {R.drawable.default1, R.drawable.girl1, R.drawable.girl2, R.drawable.girl3};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView lv2 = (ListView) findViewById(R.id.lv2);
		ListView lv1 = (ListView) findViewById(R.id.lv1);
		ListView lv3 = (ListView) findViewById(R.id.lv3);

		lv2.setAdapter(new MyAdapter());
		lv1.setAdapter(new MyAdapter());
		lv3.setAdapter(new MyAdapter());
	}

	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 3000;
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
			ImageView imageView = (ImageView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.lv_item, null);
			int resId = (int) (Math.random() * 4);
			Log.d("tag",resId + "") ;
			imageView.setImageResource(tu[resId]);
			return imageView;
		}
	}
}
