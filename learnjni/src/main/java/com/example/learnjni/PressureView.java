package com.example.learnjni;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by li-pc on 2016/12/19.
 */

public class PressureView extends View {

    private int pressure;
    private Paint paint;

    public PressureView(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(25);
    }

    public void setPressure(int pressure){
        this.pressure = pressure;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (pressure > 220){
            canvas.drawText("出现故障", 10,getHeight()/2, paint);
        }else{
            paint.setColor(Color.GRAY);
            canvas.drawRect(10, 10, 60, 260, paint);
            if (pressure < 200){
                paint.setColor(Color.GREEN);
                canvas.drawRect(10, 260-pressure, 60, 260, paint);
            }else if (pressure >= 200){
                paint.setColor(Color.RED);
                canvas.drawRect(10, 260-pressure, 60, 260, paint);
            }
        }
    }
}
