package com.jude.easyrecyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class extRecyclerView extends RecyclerView {

    private static final int INVALID_POINTER = -1;

    private int mScrollState = SCROLL_STATE_IDLE;
    private int mScrollPointerId = INVALID_POINTER;
    private int mInitialTouchX;
    private int mInitialTouchY;
    private int mLastTouchX;
    private int mLastTouchY;
    private int mDistX;
    private int mDistY;


    private boolean canScroll;
    private int scrollDistanceTrigger;
    private int scrollDistance;

    public extRecyclerView(Context context) {
        super(context);
        canScroll = true;
    }

    public extRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        canScroll = true;
    }

    public extRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        canScroll = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        Log.d("extRecyclerView", "onInterceptTouchEvent");


        final int action = MotionEventCompat.getActionMasked(e);
        final int actionIndex = MotionEventCompat.getActionIndex(e);

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                Log.d("extRecyclerView", "ACTION_DOWN");

                mScrollPointerId = e.getPointerId(0);
                mInitialTouchX = mLastTouchX = (int) (e.getX() + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (e.getY() + 0.5f);

                mDistX = 0;
                mDistY = 0;

                Log.d("mInitialTouchX", String.valueOf(mInitialTouchX));
                Log.d("mInitialTouchY", String.valueOf(mInitialTouchY));


                break;

            case MotionEventCompat.ACTION_POINTER_DOWN:

                Log.d("extRecyclerView", "ACTION_POINTER_DOWN");

                mScrollPointerId = e.getPointerId(actionIndex);
                mInitialTouchX = mLastTouchX = (int) (e.getX(actionIndex) + 0.5f);
                mInitialTouchY = mLastTouchY = (int) (e.getY(actionIndex) + 0.5f);

                mDistX = 0;
                mDistY = 0;

                Log.d("mInitialTouchX", String.valueOf(mInitialTouchX));
                Log.d("mInitialTouchY", String.valueOf(mInitialTouchY));

                break;

            case MotionEvent.ACTION_MOVE: {

                Log.d("extRecyclerView", "ACTION_MOVE");

                final int index = e.findPointerIndex(mScrollPointerId);
                if (index < 0) {
                    Log.e("%%%", "Error processing scroll; pointer index for id " +
                            mScrollPointerId + " not found. Did any MotionEvents get skipped?");
                    return false;
                }

                final int x = (int) (e.getX(index) + 0.5f);
                final int y = (int) (e.getY(index) + 0.5f);
                mDistX = x - mInitialTouchX;
                mDistY = y - mInitialTouchY;

                Log.d("x", String.valueOf(x));
                Log.d("y", String.valueOf(y));

                Log.d("dx", String.valueOf(mDistX));
                Log.d("dy", String.valueOf(mDistY));

            } break;

            case MotionEventCompat.ACTION_POINTER_UP: {
                //onPointerUp(e);
                Log.d("extRecyclerView", "ACTION_POINTER_UP");
            } break;

            case MotionEvent.ACTION_UP: {
                //mVelocityTracker.clear();
                //stopNestedScroll();
                Log.d("extRecyclerView", "ACTION_UP");


                if(canScroll){
                    if(mDistY > 300){

                        //super.smoothScrollBy(mInitialTouchX, mInitialTouchY + 100);
                        super.scrollBy(0, -30);
                        //super.stopScroll();
                    } else if (mDistY < -300){
                        //super.smoothScrollBy(mInitialTouchX, mInitialTouchY - 100);
                        super.scrollBy(0, (int) 30);
                        //super.stopScroll();
                    }
                }

            } break;

            case MotionEvent.ACTION_CANCEL: {
                //cancelTouch();
                Log.d("extRecyclerView", "ACTION_CANCEL");
            }
        }





        //return super.onInterceptTouchEvent(e);
        return false;
    }

    @Override
    public void onScrollStateChanged(int state) {
        if(state == 0){
            canScroll = true;
        } else {
            canScroll = false;
        }
    }

    public void setScrollDistanceTrigger(int scrollDistanceTrigger) {
        this.scrollDistanceTrigger = scrollDistanceTrigger;
    }

    public void setScrollDistance(int scrollDistance) {
        this.scrollDistance = scrollDistance;
    }

    public int getScrollDistanceTrigger() {
        return scrollDistanceTrigger;
    }

    public int getScrollDistance() {
        return scrollDistance;
    }
}
