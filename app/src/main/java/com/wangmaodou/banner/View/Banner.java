package com.wangmaodou.banner.View;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Maodou on 2016/8/25.
 */
public class Banner extends View {

    private Bitmap[] bitmaps;
    private int mWidth,mHeight;
    private int mCurrentBitmapIndex=0;
    private ValueAnimator mAnimation;
    private float mControlValue=0;
    private boolean isRunning=false;
    private long mStayTime=1500l;
    private long mDuration=700l;

    public Banner(Context context){
        this(context,null);
    }

    public Banner(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        init();
    }

    public void init(){
        mAnimation=createValueAnmation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=MeasureSpec.getSize(widthMeasureSpec);
        mHeight=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        if(heightMode==MeasureSpec.AT_MOST||widthMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(mWidth,mHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmaps==null){
            canvas.save();
            canvas.drawColor(Color.GRAY);
            canvas.restore();
        }else{
            drawBitmap(canvas,mControlValue);
        }

        whenFinishAnimation();
    }

    private void drawBitmap(Canvas canvas,float percentage){
        Bitmap lb=bitmaps[mCurrentBitmapIndex];
        Bitmap rb;
        if(mCurrentBitmapIndex==bitmaps.length-1) {
            rb = bitmaps[0];
        }else {
            rb=bitmaps[mCurrentBitmapIndex+1];
        }
        if(lb!=null&&rb!=null)
            drawTwoBitmap(canvas,lb,rb,percentage);
    }

    private void drawTwoBitmap(Canvas canvas,Bitmap leftBitmap,Bitmap rightBitmap,float percentage){

        int lw=leftBitmap.getWidth();
        int lh=leftBitmap.getHeight();
        int rw=rightBitmap.getWidth();
        int rh=rightBitmap.getHeight();

        Rect srcLeftRect=new Rect((int)(percentage*lw),0,lw,lh);
        RectF leftRectf=new RectF(0,0,mWidth*(1f-percentage),mHeight);
        Rect srcRightRect=new Rect(0,0,(int)(percentage*rw),rh);
        RectF rightRectf=new RectF(mWidth*(1f-percentage),0,mWidth,mHeight);

        canvas.save();
        canvas.drawBitmap(leftBitmap,srcLeftRect,leftRectf,null);
        canvas.drawBitmap(rightBitmap,srcRightRect,rightRectf,null);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setBitmaps(Bitmap[] bs){
        this.bitmaps=bs;
    }

    public int getCurrentImageIndex(){
        return mCurrentBitmapIndex;
    }

    public void startScroll(){

        if (!mAnimation.isRunning()){
            mAnimation.start();
        }
    }

    private void whenFinishAnimation(){

        if(mControlValue==1){
            if(mCurrentBitmapIndex==bitmaps.length-1){
                mCurrentBitmapIndex=0;
            }else {
                mCurrentBitmapIndex++;
            }
            Log.d("=====END",mCurrentBitmapIndex+"////");
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    startScroll();
                }
            },mStayTime);
        }
    }

    private ValueAnimator createValueAnmation(){
        final ValueAnimator va=ValueAnimator.ofFloat(0,1);
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value=(float) valueAnimator.getAnimatedValue();
                mControlValue=value;
                invalidate();
                Log.d("====",value+"///"+mCurrentBitmapIndex);
            }
        });
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        return va;
    }
}
