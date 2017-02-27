package com.dus.banner.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.Timer;
import java.util.TimerTask;

public class BannerViewGroup extends ViewGroup {

    // 子view的个数
    private int childCount;
    private int width, height;
    private int groupWidth;
    private int x, y;
    private int index = 0;//图片索引

    private Timer timer = new Timer();

    private Scroller mScroller;
    private TimerTask task;
    // 图片轮播标志位
    private boolean isRun = true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (++index >= childCount) {
                        // 执行到最后一张图片时
                        index = 0;
                    }
                    scrollTo(index * width, 0);
                    break;
            }
        }
    };

    public BannerViewGroup(Context context) {
        this(context, null);
    }

    public BannerViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        task = new TimerTask() {
            @Override
            public void run() {
                if (isRun) {
                    mHandler.sendEmptyMessage(0);
                }
            }
        };

        timer.schedule(task, 100, 1000);// 开启任务，每隔1s发送一次消息

    }

    private void startTask() {
        isRun = true;
    }

    private void stopTask() {
        isRun = false;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断scroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            invalidate();
        }
    }

    /**
     * 在此方法中测量并设置view的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        childCount = this.getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else {
            // 测量子视图的宽和高
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            View view = getChildAt(0);
            width = view.getMeasuredWidth();
            height = view.getMeasuredHeight();
            groupWidth = width * childCount;
            setMeasuredDimension(groupWidth, height);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int leftMargin;
        if (changed) {
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                leftMargin = width * i;
                childView.layout(leftMargin, 0, leftMargin + width, height);
            }
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 返回true表示事件在此view中做处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stopTask();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                x = (int) event.getX();
                y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int newX = (int) event.getX();
                int distance = newX - x;
                // 水平滑动偏移的距离
                scrollBy(-distance, 0);
                x = newX;
                break;

            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                index = (scrollX + width / 2) / width;
                if (index < 0) {
                    index = 0;
                } else if (index > childCount - 1) {
                    index = childCount - 1;
                }
                int dx = index * width - scrollX;
                mScroller.startScroll(scrollX, 0, dx, 0);
//                scrollTo(index*width,0);
                invalidate();
                startTask();
                break;
        }
        return true;
    }
}
