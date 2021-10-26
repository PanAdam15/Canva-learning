package com.example.lab4;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import androidx.annotation.NonNull;

public class PowierzchniaRysunku extends SurfaceView implements SurfaceHolder.Callback,
        Runnable{
    private SurfaceHolder mPojemnik;
    private Thread mWatekRysujacy;
    private boolean mWatekPracuje = false;
    private Object mBlokada=new Object();
    private Paint Farba = new Paint();
    private Bitmap mBitmapa = null;
    private Canvas mKanwa = null;
    private Path mSciezka = null;


    public PowierzchniaRysunku(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPojemnik = getHolder();
        mPojemnik.addCallback(this);

        Farba.setStrokeWidth(10);
        Farba.setColor(Color.BLUE);
        Farba.setAntiAlias(true);

    }

    public void setColor(int color) {
        Farba.setColor(color);
       // invalidate();
    }

    public void wznowRysowanie(){
        mWatekRysujacy = new Thread(this);
        mWatekPracuje = true;
        mWatekRysujacy.start();

    }
    public void czysc(){
        mKanwa.drawARGB(255,255,255,255);
    }

    public void pauzujRysowanie(){
        mWatekPracuje=false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        performClick();

        synchronized (mBlokada){

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            Farba.setStyle(Paint.Style.FILL);

                            mKanwa.drawCircle(event.getX(),event.getY(),20,Farba);
                            mSciezka.moveTo(event.getX(),event.getY());
                            break;

                        case MotionEvent.ACTION_MOVE:
                            Farba.setStyle(Paint.Style.STROKE);

                            mSciezka.lineTo(event.getX(),event.getY());
                            mKanwa.drawPath(mSciezka,Farba);
                            break;

                        case MotionEvent.ACTION_UP:
                            Farba.setStyle(Paint.Style.FILL);

                            mKanwa.drawCircle(event.getX(),event.getY(),20,Farba);
                            mSciezka.reset();
                            break;
                    }
            }
        return true;
    }
    public boolean performClick(){
        return super.performClick();
    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.mPojemnik = holder;

        wznowRysowanie();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {


    }


    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        mPojemnik.removeCallback(this);
        mWatekPracuje = false;

    }

    @Override
    public void run() {

        mBitmapa = Bitmap.createBitmap(getWidth()+1,getHeight()+1,Bitmap.Config.ARGB_8888);
        mKanwa = new Canvas(mBitmapa);
        mSciezka = new Path();
        czysc();

        while(mWatekPracuje){

            Canvas kanwa = null;

            try {
                synchronized (mPojemnik) {
                    if(!mPojemnik.getSurface().isValid()) continue;
                    kanwa = mPojemnik.lockCanvas();

                    synchronized (mBlokada) {
                        if(mWatekPracuje){
                            kanwa.drawBitmap(mBitmapa,0,0,Farba);

                        }
                    }
                }
            }finally{
                if(kanwa != null) {
                    mPojemnik.unlockCanvasAndPost(kanwa);
                }
            }
            try{
                Thread.sleep(1000/25);
            } catch (InterruptedException e) { }
        }
    }
}
