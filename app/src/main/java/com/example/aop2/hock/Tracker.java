package com.example.aop2.hock;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import static android.view.Window.ID_ANDROID_CONTENT;

/**
 * @author youer
 * @date 2020/8/20
 */
public class Tracker {
    private static final String TAG = "Tracker";

    /**
     * 开始进行页面的埋点，获取页面的根布局
     *
     * @param activity
     */
    public static void startViewTracker(Activity activity) {
        startViewTracker(activity, "rootView");
    }

    /**
     * 开始进行页面的埋点，获取页面的根布局
     *
     * @param activity
     */
    public static void startViewTracker(Activity activity, String parentContentDescription) {
        ViewGroup rootView = activity.findViewById(ID_ANDROID_CONTENT);
        rootView.setContentDescription(parentContentDescription);
        setViewTracker(rootView.getChildAt(0));
    }

    public static void startViewTracker(View view) {
        startViewTracker(view, ViewPath.getPath(view));
    }

    public static void startViewTracker(View view, String parentContentDescription) {
        setViewTracker(view, parentContentDescription);
    }

    /**
     * 给View设置setContentDescription以及监听
     *
     * @param view
     */
    public static void setViewTracker(View view) {
        setViewTracker(view, ViewPath.getPath(view));
    }

    public static void setViewTracker(View view, String contentDescription) {
        view.setContentDescription(contentDescription);
        HookOnClickListener.hook(view);
        if (view instanceof RecyclerView) {
            HookAddOnScrollListenerHelper.hook((RecyclerView) view);
            HookRecyclerViewOnBindViewHolder.hook((RecyclerView) view);
        } else if (view instanceof ViewGroup) {
            setChildViewTracker((ViewGroup) view);
        } else if (view instanceof EditText) {
            HookTextChangedListener.hook((EditText) view);
        }
    }

    /**
     * 给ViewGroup的View添加埋点
     *
     * @param viewGroup
     */
    private static void setChildViewTracker(ViewGroup viewGroup) {
        // 给ViewGroup添加监听，如果有页面元素的变化，重新构建其tracker
        viewGroup.setOnHierarchyChangeListener(new ViewGroup.OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                setViewTracker(parent);
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                setViewTracker(parent);
            }
        });
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            setViewTracker(viewGroup.getChildAt(i));
        }
    }

}
