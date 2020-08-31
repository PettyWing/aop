package com.example.aop2.hock;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.ContentFrameLayout;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author youer
 * @date 2020/8/19
 */
public class ViewPath {
    /**
     * 获取view的viewTree
     * 优化点：
     * 如果这个item有id，则返回id，如果没有，则返回他在ViewTree的第几个
     *
     * @param view
     * @return
     */
    public static String getPath(View view) {
        String parentPath = "";
        if (view == null) {
            return parentPath;
        }
        if (view.getParent() == null) {
            parentPath = "rootView";
        } else {
            if (view.getParent() instanceof ContentFrameLayout) {
                parentPath = "rootView";
            } else {
                if (TextUtils.isEmpty(((View) view.getParent()).getContentDescription())) {
                    parentPath = view.getParent().getClass().getSimpleName();
                } else {
                    parentPath = String.valueOf(((View) view.getParent()).getContentDescription());
                }
            }
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
    public static String getPath(View view, RecyclerView recyclerView, int position) {
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
