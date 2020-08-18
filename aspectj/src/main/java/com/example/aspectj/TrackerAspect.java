package com.example.aspectj;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Field;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.Window.ID_ANDROID_CONTENT;

/**
 * @author youer
 * @date 2020/8/10
 */
@Aspect
public class TrackerAspect {

    private static final String TAG = "TrackerAspect";

    /**
     * 监控OnClick点击事件
     */
    @Pointcut
    @After("execution(* android.view.View.OnClickListener+.onClick(..))")
    public void afterOnClickMethodCall(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        View view = (View) args[0];
        Log.e(TAG, "afterOnClickMethodCall -> ContentDescription:" + view.getContentDescription());
    }

    /**
     * 监控OnLongClick点击事件
     */
    @Pointcut
    @After("execution(* android.view.View.OnLongClickListener+.onLongClick(..))")
    public void afterOnLongClickMethodCall(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        View view = (View) args[0];
        Log.e(TAG, "afterOnLongClickMethodCall -> ContentDescription:" + view.getContentDescription());
    }

    /**
     * 监控OnCheckChange点击事件
     */
    @Pointcut
    @After("execution(* android.widget.CompoundButton.OnCheckedChangeListener+.onCheckedChanged(..))")
    public void afterOnCheckChangeMethodCall(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        View view = (View) args[0];
        Log.e(TAG, "afterOnCheckChangeMethodCall -> ContentDescription:" + view.getContentDescription());
    }

    /**
     * 监控文本输入事件
     */
    @Pointcut
//    @After("execution(* android.widget.EditText.setText(..))")
    @After("execution(* android.text.TextWatcher.onTextChanged(..))")
    public void afterOnTextChangedMethodCall(JoinPoint joinPoint) {
        // TODO: 2020/8/17 拿到他的contentDescription
        // 方法的几个参数
        Object[] args = joinPoint.getArgs();
        Log.e(TAG, "afterOnTextChangedMethodCall -> CharSequence:" + args[0]);
        Log.e(TAG, "afterOnTextChangedMethodCall getTarget->" + joinPoint.getTarget().toString());// 被切面的animal对象
        Log.e(TAG, "afterOnTextChangedMethodCall getThis->" + joinPoint.getThis()); //切面代码运行所在的类对象
        Log.e(TAG, "afterOnTextChangedMethodCall getKind->" + joinPoint.getKind());//切面的类型 method-call
        Log.e(TAG, "afterOnTextChangedMethodCall getSourceLocation->" + joinPoint.getSourceLocation());//源码位置 MainActivity.java:26
        Log.e(TAG, "afterOnTextChangedMethodCall getDeclaringTypeName->" + joinPoint.getSignature().getDeclaringTypeName());//com.wandering.sample.aspectj.Animal
        Log.e(TAG, "afterOnTextChangedMethodCall getModifiers->" + joinPoint.getSignature().getModifiers());//方法修饰符 1--public
        Log.e(TAG, "afterOnTextChangedMethodCall getName->" + joinPoint.getSignature().getName());//方法名 run
        Log.e(TAG, "afterOnTextChangedMethodCall getDeclaringType->" + joinPoint.getSignature().getDeclaringType());//Animal.class
    }


    int scrollerState = 0;
    int scrollerX = 0;
    int scrollerY = 0;

    /**
     * 监控android.support.v7.widget.RecyclerView的滑动事件
     *
     * @param joinPoint
     */
    @Pointcut
    @After("call(* android.support.v7.widget.RecyclerView.onScrollStateChanged(..))")
    public void afterRecycleViewOnScrollStateChangedMethodCall1(JoinPoint joinPoint) {
        // 方法的几个参数
        Object[] args = joinPoint.getArgs();
        scrollerState = (int) args[0];
        switch (scrollerState) {
            case RecyclerView.SCROLL_STATE_DRAGGING:
                break;
            case RecyclerView.SCROLL_STATE_IDLE:
                // 当页面滚动停止的时候进行数据上报，并且置零参数1
                Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall1: " + scrollerX);
                Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall1: " + scrollerY);
                Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall getThis->" + ((RecyclerView) joinPoint.getThis()).getContentDescription()); //切面代码运行所在的类对象
                scrollerX = 0;
                scrollerY = 0;
                break;
            default:
        }
    }

    /**
     * 监控RecycleView的滑动事件
     */
    @Pointcut
    @After("call(* android.support.v7.widget.RecyclerView.onScrolled(..))")
    public void afterRecycleViewOnScrolledMethodCall1(JoinPoint joinPoint) {
        // 方法的几个参数
        Object[] args = joinPoint.getArgs();
        // 当滚动状态为1或者2的时候，移动距离进行叠加。
        if (scrollerState == RecyclerView.SCROLL_STATE_DRAGGING || scrollerState == RecyclerView.SCROLL_STATE_SETTLING) {
            scrollerX += (int) args[0];
            scrollerY += (int) args[1];
        }
    }

    /**
     * 监控Androidx RecycleView的滑动事件
     *
     * @param joinPoint
     */
    @Pointcut
    @After("call(* androidx.recyclerview.widget.RecyclerView.onScrollStateChanged(..))")
    public void afterRecycleViewOnScrollStateChangedMethodCall(JoinPoint joinPoint) {
        // 方法的几个参数
        Object[] args = joinPoint.getArgs();
        scrollerState = (int) args[0];

        switch (scrollerState) {
            case RecyclerView.SCROLL_STATE_DRAGGING:
                break;
            case RecyclerView.SCROLL_STATE_IDLE:
                // 当页面滚动停止的时候进行数据上报，并且置零参数1
                Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall: " + scrollerX);
                Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall: " + scrollerY);
                Log.d(TAG, "afterRecycleViewOnScrollStateChangedMethodCall getThis->" + ((RecyclerView) joinPoint.getThis()).getContentDescription()); //切面代码运行所在的类对象
                scrollerX = 0;
                scrollerY = 0;
                break;
            default:
        }
    }

    /**
     * 监控RecycleView的滑动事件
     */
    @Pointcut
    @After("call(* androidx.recyclerview.widget.RecyclerView.onScrolled(..))")
    public void afterRecycleViewOnScrolledMethodCall(JoinPoint joinPoint) {
        // 方法的几个参数
        Object[] args = joinPoint.getArgs();
        // 当滚动状态为1或者2的时候，移动距离进行叠加。
        if (scrollerState == RecyclerView.SCROLL_STATE_DRAGGING || scrollerState == RecyclerView.SCROLL_STATE_SETTLING) {
            scrollerX += (int) args[0];
            scrollerY += (int) args[1];
        }
    }

//    @Pointcut
//    @After("execution(* android.view.View+.onLayout(..))")
//    public void onCallViewLifecycle(JoinPoint joinPoint) {
//        if (joinPoint.getThis() instanceof View || joinPoint.getThis() instanceof ViewGroup) {
//            setContentDescription((View) joinPoint.getThis());
//        }
//        Log.e(TAG, "onCallViewLifecycle getThis->" + joinPoint.getThis().getClass().getSimpleName());
//    }

    /**
     * 初始化布局的监控
     *
     * @param joinPoint
     */
    @Pointcut
    @After("execution(* androidx.appcompat.app.AppCompatDelegate+.setContentView(..))")
    public void onCallActivityLifecycle(JoinPoint joinPoint) {
        ViewGroup rootView = ((AppCompatDelegate) joinPoint.getThis()).findViewById(ID_ANDROID_CONTENT);
        if (rootView != null) {
            ViewPath.setViewTracker(rootView.getChildAt(0));
        }
    }

    /**
     * 初始化布局的监控
     *
     * @param joinPoint
     */
    @Pointcut
    @After("execution(* androidx.recyclerview.widget.RecyclerView.Adapter.onBindViewHolder(..))")
    public void afterRecycleAdapterOnBind(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        // 获取listView对象
        try {
            HashMap<String, Field> fieldMap = new HashMap<>();
            Class tempClass = args[0].getClass();
            while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
                for (Field field : tempClass.getDeclaredFields()) {
                    fieldMap.put(field.getName(), field);
                }
                tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
            }
            Field recyclerViewField = fieldMap.get("mOwnerRecyclerView");
            recyclerViewField.setAccessible(true);
            ViewPath.setRecycleViewTracker(((RecyclerView.ViewHolder) args[0]).itemView, (RecyclerView) recyclerViewField.get(args[0]), (Integer) args[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
