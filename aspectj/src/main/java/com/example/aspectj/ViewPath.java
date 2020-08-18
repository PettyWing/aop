package com.example.aspectj;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;

import androidx.appcompat.widget.ContentFrameLayout;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author youer
 * @date 2020/8/17
 */
public class ViewPath {

    private static final String TAG = "ViewPath";

    /**
     * 对每个View添加埋点的监听
     *
     * @param view
     */
    public static void setViewTracker(View view) {
        view.setContentDescription(getPath(view));
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void sendAccessibilityEvent(View host, int eventType) {
                super.sendAccessibilityEvent(host, eventType);
                // TODO: 2020/8/18 输入的方法通过TextWatch拿不到View对象，暂时通过该方法获取
                if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
                    Log.d("TrackerAspect", "postUTSendKey: " + host.getContentDescription() + "|value:" + ((TextView) host).getText().toString());
                }
            }
        });
        Log.d(TAG, "setViewTracker: " + view.getContentDescription());
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setViewTracker(((ViewGroup) view).getChildAt(i));
            }
            if (!(view instanceof RecyclerView)) {
                // 给ViewGroup添加监听，如果有页面元素的变化，重新构建其tracker
                ((ViewGroup) view).setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
                    @Override
                    public void onChildViewAdded(View parent, View child) {
                        setViewTracker(parent);
                    }

                    @Override
                    public void onChildViewRemoved(View parent, View child) {
                        setViewTracker(parent);
                    }
                });
            }
        }
    }

    /**
     * 对RecycleView的每个item添加埋点的监听
     *
     * @param view
     */
    public static void setRecycleViewTracker(View view, RecyclerView recyclerView, int position) {
        view.setContentDescription(getPath(view, recyclerView, position));
        Log.d(TAG, "seRecycleViewTracker: " + view.getContentDescription());
        if (view instanceof ViewGroup) {
            int childCount = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < childCount; i++) {
                setViewTracker(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    /**
     * 获取view的viewTree
     * 优化点：
     * 如果这个item有id，则返回id，如果没有，则返回他在ViewTree的第几个
     *
     * @param view
     * @return
     */
    private static String getPath(View view) {
        String parentPath = "";
        if (view == null) {
            return parentPath;
        }
        if (view.getParent() instanceof ContentFrameLayout) {
            parentPath = "rootView";
        } else {
            parentPath = String.valueOf(((View) view.getParent()).getContentDescription());
        }
        String viewType = view.getClass().getSimpleName();
        int index = indexOfChild((ViewGroup) view.getParent(), view);
        viewType = viewType + "[" + index + "]";
        return parentPath + "/" + viewType;
    }

    /**
     * @param view
     * @param recyclerView
     * @param position
     * @return
     */
    private static String getPath(View view, RecyclerView recyclerView, int position) {
        String parentPath = "";
        if (view == null) {
            return parentPath;
        }
        if (view.getParent() instanceof ContentFrameLayout) {
            parentPath = "rootView";
        } else {
            parentPath = String.valueOf(recyclerView.getContentDescription());
        }
        String viewType = view.getClass().getSimpleName();
        viewType = viewType + "[position" + position + "]";
        return parentPath + "/" + viewType;
    }


    /**
     * 获取子view在viewTree的第几个
     * 优化点
     * 1：index从"兄弟节点的第几个”优化为:“相同类型兄弟节点的第几个
     *
     * @param parent
     * @param child
     * @return
     */
    private static int indexOfChild(ViewGroup parent, View child) {
        if (parent == null) {
            return 0;
        }
        final int count = parent.getChildCount();
        int j = 0;
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if (child.getClass().isInstance(view)) {
                if (view == child) {
                    return j;
                }
                j++;
            }
        }
        return -1;
    }
}
