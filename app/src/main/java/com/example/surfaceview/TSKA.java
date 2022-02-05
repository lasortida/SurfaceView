package com.example.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class TSKA extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    public TSKA(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    private Thread thread;
    private boolean isTouch = false;
    private float x;
    private Paint first = new Paint();
    private Paint second = new Paint();

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Canvas canvas = getHolder().lockCanvas();
        x = canvas.getWidth() / 2;
        getHolder().unlockCanvasAndPost(canvas);
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        try {
            thread.join();
        } catch (InterruptedException e){

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        x = event.getX();
        return false;
    }

    @Override
    public void run() {
        int count = 0;
        first.setColor(Color.BLUE);
        second.setColor(Color.RED);
        while(true){
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null){
                try {
                    if (count % 2 == 0){
                        canvas.drawRect(0, 0, x, canvas.getHeight(), first);
                        canvas.drawRect(x, 0, canvas.getWidth(), canvas.getHeight(), second);
                    }
                    else{
                        canvas.drawRect(0, 0, x, canvas.getHeight(), second);
                        canvas.drawRect(x, 0, canvas.getWidth(), canvas.getHeight(), first);
                    }
                    count++;
                } finally {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e){

            }
        }
    }
}
