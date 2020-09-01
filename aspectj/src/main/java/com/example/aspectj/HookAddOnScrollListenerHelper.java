package com.example.aspectj;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;


/**
 * @author youer
 * @date 2020/8/20
 */
public class HookAddOnScrollListenerHelper {

    private static final String TAG = "HookAddOnScrollListener";

    public static void hook(final RecyclerView recyclerView) {//
        List<RecyclerView.OnScrollListener> mScrollListeners = (List<RecyclerView.OnScrollListener>) SuperClassReflectionUtils.getFieldValue(recyclerView, "mScrollListeners");
        if (mScrollListeners != null && !mScrollListeners.isEmpty()) {
            for (RecyclerView.OnScrollListener listener : mScrollListeners) {
                // 去掉重复的滑动监听
                if (listener instanceof HookOnScrollListener) {
                    return;
                }
            }
        }
        recyclerView.addOnScrollListener(new HookOnScrollListener());
    }

    static class HookOnScrollListener extends RecyclerView.OnScrollListener {
        private static final String TAG = "OnScrollListener";
        int scrollerState = 0;
        int scrollerX = 0;
        int scrollerY = 0;

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            scrollerState = newState;
            switch (scrollerState) {
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    break;
                case RecyclerView.SCROLL_STATE_IDLE:
                    // 当页面滚动停止的时候进行数据上报，并且置零参数1
                    Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall1: " + scrollerX);
                    Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall1: " + scrollerY);
                    Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall getThis->" + recyclerView.getContentDescription()); //切面代码运行所在的类对象
                    scrollerX = 0;
                    scrollerY = 0;
                    break;
                default:
            }

        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (scrollerState == RecyclerView.SCROLL_STATE_DRAGGING || scrollerState == RecyclerView.SCROLL_STATE_SETTLING) {
                scrollerX += dx;
                scrollerY += dy;
            }
        }
    }
}


