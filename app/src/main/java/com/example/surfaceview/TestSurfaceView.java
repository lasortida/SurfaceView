package com.example.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private boolean touched = false;
    private float touchX;
    private float touchY;
    private float r;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public Thread my;

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        my = new Thread(this);
        my.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try{
            my.join();
        } catch (InterruptedException e){

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){
        touchX = event.getX();
        touchY = event.getY();
        r = 0;
        touched = true;
        return false;
    }

    @Override
    public void run() {
        while(true){
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null){
                try{
                    canvas.drawColor(Color.BLUE);
                    Paint paint = new Paint();
                    paint.setColor(Color.YELLOW);
                    if (touched){
                        canvas.drawCircle(touchX, touchY, r, paint);
                    }
                } finally {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
            try {
                r += 5;
                Thread.sleep(1000);
            } catch (InterruptedException e){

            }
        }
    }
}