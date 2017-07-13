package com.blink.dagger.justcolor.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.blink.dagger.justcolor.R;
import com.blink.dagger.justcolor.util.DensityUtil;

/**
 * Created by lucky on 2017/7/13.
 */
public class CanvasView extends View {

    String color = "#FF4081";
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path path = new Path();
    private int width;
    private int height;

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST){
            widthSize = Math.min(getSuggestedMinimumWidth(),widthSize);
        }
        if (heightMode == MeasureSpec.AT_MOST){
            heightSize = Math.min(getSuggestedMinimumHeight(),heightSize);
        }
        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.color_view);
        paint.setColor(Color.parseColor(color));
        paint.setStyle(Paint.Style.FILL);
        Rect src = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        Rect dest = new Rect(0,0,width,height);
        canvas.drawBitmap(bitmap,src,dest,paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(20);
        canvas.drawText(color,0,color.length()-1,width/2,height/2,paint);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
