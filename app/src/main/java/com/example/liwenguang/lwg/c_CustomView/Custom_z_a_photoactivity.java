package com.example.liwenguang.lwg.c_CustomView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.liwenguang.lwg.R;

/**
 * Created by liwenguang on 2016/11/10.
 */

public class Custom_z_a_photoactivity extends AppCompatActivity {

    private ImageView icon_home;
    private ImageView icon_menu;
    private RelativeLayout level1;
    private RelativeLayout level2;
    private RelativeLayout level3;
    private boolean isShowLevel3 = true;
    private boolean isShowLevel2 = true;
    private boolean isShowLevel1 = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_custom_a_photo);

        icon_home = (ImageView) findViewById(R.id.c_CustomView_one_icon_home);
        icon_menu = (ImageView) findViewById(R.id.c_CustomView_two_icon_menu);
        level1 = (RelativeLayout) findViewById(R.id.c_CustomView_one_rl);
        level2 = (RelativeLayout) findViewById(R.id.c_CustomView_two_rl);
        level3 = (RelativeLayout) findViewById(R.id.c_CustomView_three_rl);
        MyOnClickListener myOnClickListener = new MyOnClickListener();
        icon_home.setOnClickListener(myOnClickListener);
        icon_menu.setOnClickListener(myOnClickListener);
        level1.setOnClickListener(myOnClickListener);
        level2.setOnClickListener(myOnClickListener);
        level3.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.c_CustomView_one_icon_home:
                    //如果三级菜单和二级菜单是显示的，则都设置隐藏
                    if (isShowLevel2){
                        //隐藏二级菜单
                        isShowLevel2 = false;
                        Tools.hideView(level2);
                        if(isShowLevel3){
                            //隐藏三级菜单
                            isShowLevel3 = false;
                            Tools.hideView(level3, 200);
                        }
                    }else{
                        //如果都是隐藏的，则设置二级菜单显示
                        //显示二级菜单
                        isShowLevel2 = true;
                        Tools.showView(level2);
                    }
                    break;
                case R.id.c_CustomView_two_icon_menu:
                    if (isShowLevel3){
                        //隐藏
                        isShowLevel3 = false;
                        Tools.hideView(level3);
                    }else{
                        //显示
                        isShowLevel3 = true;
                        Tools.showView(level3);
                    }
                    break;
                case R.id.c_CustomView_one_rl:
                    break;
                case R.id.c_CustomView_two_rl:
                    break;
                case R.id.c_CustomView_three_rl:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU){
            //如果一级、二级、三级菜单是显示的就全部隐藏
            if (isShowLevel1){
                isShowLevel1 = false;
                Tools.hideView(level1);
                if (isShowLevel2){
                    //隐藏二级菜单
                    isShowLevel2 = false;
                    Tools.hideView(level2, 200);
                    if (isShowLevel3){
                        //隐藏三级菜单
                        isShowLevel3 = false;
                        Tools.hideView(level3, 300);
                    }
                }
            }else{
                //如果一级、二级菜单是隐藏的就显示
                if (!isShowLevel2){
                    isShowLevel2 = true;
                    Tools.showView(level2);
                    if (!isShowLevel1){
                        //显示一级菜单
                        isShowLevel1 = true;
                        Tools.showView(level2,200);
                    }
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
