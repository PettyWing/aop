package com.example.aspectj;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;

/**
 * @author youer
 * @date 2020/8/10
 */
@Aspect
public class TrackerAspect {

    private static final String TAG = "TrackerAspect";

    @After("execution(* android.app.Activity+.onResume(..))")
    public void onActivityResume(JoinPoint joinPoint) {
        Log.d(TAG, "onActivityResume: ");
        Tracker.startViewTracker((Activity) joinPoint.getThis());
    }

    @After("execution(* android.app.Dialog+.onCreate(..))")
    public void onDialogShow(JoinPoint joinPoint) {
        Log.d(TAG, "onDialogShow: ");
        Dialog dialog = (Dialog) joinPoint.getThis();
        Tracker.startViewTracker(dialog.getWindow().getDecorView());
    }

    @After("call(* android.widget.PopupWindow+.showAsDropDown(..))")
    public void onPopupWindowShow(JoinPoint joinPoint) {
        Log.d(TAG, "onPopupWindowShow: ");
        PopupWindow popupWindow = (PopupWindow) joinPoint.getTarget();
        try {
            Field field = SuperClassReflectionUtils.getDeclaredField(popupWindow, "mDecorView");
            field.setAccessible(true);
            FrameLayout popupDecorView = (FrameLayout) field.get(popupWindow);
            Tracker.startViewTracker(popupDecorView);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
