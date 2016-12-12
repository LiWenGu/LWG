package com.example.liwenguang.lwg.d_H5;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.liwenguang.lwg.R;

/**
 * Created by liwenguang on 2016/11/14.
 */

public class H5_Activity extends AppCompatActivity implements View.OnClickListener{

    private Button btnJavaAndJs;
    private Button btnJsCallJava;
    private Button btnJsCallPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_h5_a);

        findViews();
    }



    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-11-14 08:13:13 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        btnJavaAndJs = (Button)findViewById( R.id.btn_java_and_js );
        btnJsCallJava = (Button)findViewById( R.id.btn_js_call_java );
        btnJsCallPhone = (Button)findViewById( R.id.btn_js_call_phone );

        btnJavaAndJs.setOnClickListener( this );
        btnJsCallJava.setOnClickListener( this );
        btnJsCallPhone.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-11-14 08:13:13 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnJavaAndJs ) {
            // Handle clicks for btnJavaAndJs
            Intent intent = new Intent(H5_Activity.this, H5_z_a_JavaAndJsCall.class);
            startActivity(intent);
        } else if ( v == btnJsCallJava ) {
            // Handle clicks for btnJsCallJava
            Intent intent = new Intent(H5_Activity.this, H5_z_b_JsCallJavaVideoActivity.class);
            startActivity(intent);
        } else if ( v == btnJsCallPhone ) {
            // Handle clicks for btnJsCallPhone
            Intent intent = new Intent(H5_Activity.this, H5_z_c_JsCallJavaCallPhoneActivity.class);
            startActivity(intent);
        }
    }

}
