package com.mertha.circletapv0;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CircleDraw extends View {

    public Paint mPaint;
    public static Canvas mCanvas;
    private int radius = 20;
    private Context mContext;

    // class member variable to save the X,Y coordinates
    public float[] lastTouchDownXY = new float[2];
   // private Drawer drawer;

    public CircleDraw(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    public CircleDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircleDraw(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context) {
        //do stuff that was in your original constructor...
        this.setOnTouchListener(touchListener);
        this.setOnClickListener(clickListener);
        mContext = context;
        mPaint = new Paint();
    }

    protected void onDraw(Canvas canvas){
        mCanvas = canvas;
        super.onDraw(mCanvas);
        canvas.drawColor(Color.GRAY);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        canvas.drawCircle(lastTouchDownXY[0], lastTouchDownXY[1], radius, mPaint);

    }

    // the purpose of the touch listener is just to store the touch X,Y coordinates
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.requestFocus();
            // save the X,Y coordinates
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                lastTouchDownXY[0] = event.getX();
                lastTouchDownXY[1] = event.getY();
            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // retrieve the stored coordinates
            float x = lastTouchDownXY[0];
            float y = lastTouchDownXY[1];

            // use the coordinates for whatever
            Log.i("TAG", "onLongClick: x = " + x + ", y = " + y);
            invalidate();
        }
    };
}
