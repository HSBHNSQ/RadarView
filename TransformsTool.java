package com.wmhsb.drawviewtest;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

/**
 * Created by heshaobo on 2017/7/26.
 */

public class TransformsTool implements View.OnTouchListener {

    private static final String LOG_TAG  = TransformsTool.class.getSimpleName();
    private View mTrfView;
    private ViewGroup mParentLayout;
    private int mTouchAtViewX;
    private int mTouchAtViewY;
    private int mOldWidth;
    private int mOldHeight;
    public View mSecView;
    private TransformsTool(){}

    public TransformsTool(ViewGroup parent,View trfView){
        super();
        mParentLayout = parent;
        mTrfView = trfView;
        mParentLayout.setOnTouchListener(this);
        mOldHeight = mTrfView.getHeight();
        mOldWidth = mTrfView.getWidth();
    }

    public  void scale(float scale){
        if (scale <= 0){
            Log.d(LOG_TAG,"scale不能小于等于0");
            return;
        }
        if (mOldHeight <= 0 || mOldWidth <= 0){
            mOldHeight = mTrfView.getHeight();
            mOldWidth = mTrfView.getWidth();
        }
        float centerX = mTrfView.getX() + mTrfView.getWidth()/2;
        float centerY = mTrfView.getY() + mTrfView.getHeight()/2;
        float width = mOldWidth * scale;
        float height = mOldHeight * scale;
        float left = centerX - width / 2.0f;
        float top = centerY - height / 2.0f;
        float right = left + width;
        float bottom = top + height;
        mTrfView.layout((int)left, (int)top, (int)right, (int)bottom);
        if (mSecView != null){
            mSecView.layout((int)left, (int)top, (int)right, (int)bottom);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getActionMasked()){
            case ACTION_DOWN:
                mTouchAtViewX = (int) (motionEvent.getX() - mTrfView.getX());
                mTouchAtViewY = (int) (motionEvent.getY() - mTrfView.getY());
                break;
            case ACTION_MOVE:
                Log.d(LOG_TAG,"transforms onTouchMove");
                moveViewByLayout(mTrfView,(int) motionEvent.getX(),(int)motionEvent.getY());
                break;
            case ACTION_UP:
                break;
        }

        return true;
    }
    private void moveViewByLayout(View view, int X, int Y) {
        if (    X < view.getX() ||
                X > view.getX()+view.getWidth()||
                Y < view.getY() ||
                Y > view.getY()+view.getHeight()
                ){
            return;
        }
        int left = X - mTouchAtViewX;
        int top = Y - mTouchAtViewY;
        int width = left + (int) view.getWidth();
        int height = top + (int) view.getHeight();
        view.layout(left, top, width, height);
        if (mSecView != null){
            mSecView.layout(left, top, width, height);
        }
    }
}
