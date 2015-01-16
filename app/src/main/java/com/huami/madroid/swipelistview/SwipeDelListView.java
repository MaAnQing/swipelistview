package com.huami.madroid.swipelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * Created by madroid on 15-1-14.
 */
public class SwipeDelListView extends ListView{

    private int mScreenWidth;
    private int mDownX;
    private int mDownY;
    private int mDeleteBtnWidth;
    private Scroller mScroller;

    // 删除按钮是否正在显示
    private boolean isDeleteShown;

    // 当前处理的item
    private ViewGroup mItemView;
    // 当前处理的item的LayoutParams
    private LinearLayout.LayoutParams mLayoutParams;

    public SwipeDelListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeDelListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // 获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;

        mScroller = new Scroller(context) ;

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performActionDown(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                return performActionMove(ev);
            case MotionEvent.ACTION_UP:
                performActionUp();
                break;
        }

        return super.onTouchEvent(ev);
    }

    // 处理action_down事件
    private void performActionDown(MotionEvent ev) {

        if(isDeleteShown && !isTheItem(ev)) {
            turnToNormal();
        }

        mDownX = (int) ev.getX();
        mDownY = (int) ev.getY();
        // 获取当前点的item
        mItemView = (ViewGroup) getChildAt(pointToPosition(mDownX, mDownY)
                - getFirstVisiblePosition());

        // 获取删除按钮的宽度
        mDeleteBtnWidth = mItemView.getChildAt(1).getLayoutParams().width;
        mLayoutParams = (LinearLayout.LayoutParams) mItemView.getChildAt(0)
                .getLayoutParams();
        // 重新设置textView的layout_width 等于屏幕宽度
        mLayoutParams.width = mScreenWidth;
        mItemView.getChildAt(0).setLayoutParams(mLayoutParams);
    }

    // 处理action_move事件
    private boolean performActionMove(MotionEvent ev) {
        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();
        if(Math.abs(nowX - mDownX) > Math.abs(nowY - mDownY)) {
            // 如果向左滑动
            if(  !isDeleteShown && nowX < mDownX) {
                // 计算要偏移的距离
                int rightScroll = (nowX - mDownX) / 2;
                // 如果大于了删除按钮的宽度， 则最大为删除按钮的宽度
                if(-rightScroll >= mDeleteBtnWidth) {
                    rightScroll = -mDeleteBtnWidth;
                }
                // 重新设置leftMargin
                mLayoutParams.leftMargin = rightScroll;
                mItemView.getChildAt(0).setLayoutParams(mLayoutParams);
                mItemView.setBackgroundColor(getResources().getColor(R.color.background_floating_material_dark));
            }
            //when delete button is shown and right move
            if(isDeleteShown && nowX > mDownX){
                int leftScroll = (nowX - mDownX)/2 ;
                if(leftScroll >= mDeleteBtnWidth){
                    leftScroll = mDeleteBtnWidth ;
                }
                // 重新设置leftMargin
                mLayoutParams.leftMargin =  -mDeleteBtnWidth +leftScroll;
                mItemView.getChildAt(0).setLayoutParams(mLayoutParams);
                mItemView.setBackgroundColor(getResources().getColor(R.color.background_floating_material_dark));
            }

            return true;
        }
//        if(isDeleteShown){
//            turnToNormal();
//        }
        return super.onTouchEvent(ev);
    }

    // 处理action_up事件
    private void performActionUp() {
        // 偏移量大于button的一半，则显示button
        // 否则恢复默认
        if(-mLayoutParams.leftMargin >= mDeleteBtnWidth / 2) {
            mLayoutParams.leftMargin = -mDeleteBtnWidth;
            isDeleteShown = true;
        }else {
            turnToNormal();
        }

        mItemView.getChildAt(0).setLayoutParams(mLayoutParams);
    }

    /**
     * 变为正常状态
     */
    public void turnToNormal() {
        mLayoutParams.leftMargin = 0;
        mItemView.getChildAt(0).setLayoutParams(mLayoutParams);
        mItemView.setBackgroundColor(getResources().getColor(R.color.background_material_light));
        isDeleteShown = false;

    }

/*
    */
/**
     * 变为正常状态
     *//*

    public void turnToNormalByScroll(){

        rightScroll();
        turnToNormal();
    }

    //right remove Animation
    private void rightScroll(){

        final int delta = (mScreenWidth - mItemView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        mScroller.startScroll(mItemView.getScrollX(), 0, -delta, 0,
                Math.abs(delta));
        postInvalidate();
        //mItemView.scrollTo(0,0) ;
    }
*/


    /**
     * 判断当前item是否为isDeleteButton的Item
     */
    public boolean isTheItem(MotionEvent ev){
        if(mItemView == getItemPosition(ev)){
            return true;
        }else {
            return false;
        }

    }

    private ViewGroup getItemPosition(MotionEvent ev){

        int nowX = (int) ev.getX();
        int nowY = (int) ev.getY();
        return (ViewGroup) getChildAt(pointToPosition(nowX, nowY)
                - getFirstVisiblePosition());
    }

    /**
     * 当前是否可点击
     * @return 是否可点击
     */
    public boolean canClick() {
        return !isDeleteShown;
    }
}
