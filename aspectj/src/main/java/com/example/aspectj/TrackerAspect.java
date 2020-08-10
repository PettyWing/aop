package com.example.aspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author youer
 * @date 2020/8/10
 */
@Aspect
public class TrackerAspect {

    private static final String TAG = "TrackerAspect";

    /**
     * 监控点击事件
     */
    @Pointcut("execution(* android.view.View.OnClickListener+.onClick(..))")
    public void callOnClickMethod() {
    }

    @After("callOnClickMethod()")
    public void afterOnClickMethodCall(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Log.e(TAG, "beforeOnClickMethodCall -> view:" + args[0]);
        Log.e(TAG, "beforeOnClickMethodCall getTarget->" + joinPoint.getTarget().toString());// 被切面的animal对象
        Log.e(TAG, "beforeOnClickMethodCall getThis->" + joinPoint.getThis()); //切面代码运行所在的类对象
        Log.e(TAG, "beforeOnClickMethodCall getKind->" + joinPoint.getKind());//切面的类型 method-call
        Log.e(TAG, "beforeOnClickMethodCall getSourceLocation->" + joinPoint.getSourceLocation());//源码位置 MainActivity.java:26
        Log.e(TAG, "beforeOnClickMethodCall getDeclaringTypeName->" + joinPoint.getSignature().getDeclaringTypeName());//com.wandering.sample.aspectj.Animal
        Log.e(TAG, "beforeOnClickMethodCall getModifiers->" + joinPoint.getSignature().getModifiers());//方法修饰符 1--public
        Log.e(TAG, "beforeOnClickMethodCall getName->" + joinPoint.getSignature().getName());//方法名 run
        Log.e(TAG, "beforeOnClickMethodCall getDeclaringType->" + joinPoint.getSignature().getDeclaringType());//Animal.class
    }

    /**
     * 监控点击事件
     */
    @Pointcut("execution(* android.widget.CompoundButton.OnCheckedChangeListener+.onCheckedChanged(..))")
    public void callOnCheckChangeMethod() {
    }

    @After("callOnCheckChangeMethod()")
    public void afterOnCheckChangeMethodCall(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Log.e(TAG, "afterOnCheckChangeMethodCall -> view:" + args[0]);
        Log.e(TAG, "afterOnCheckChangeMethodCall getTarget->" + joinPoint.getTarget().toString());// 被切面的animal对象
        Log.e(TAG, "afterOnCheckChangeMethodCall getThis->" + joinPoint.getThis()); //切面代码运行所在的类对象
        Log.e(TAG, "afterOnCheckChangeMethodCall getKind->" + joinPoint.getKind());//切面的类型 method-call
        Log.e(TAG, "afterOnCheckChangeMethodCall getSourceLocation->" + joinPoint.getSourceLocation());//源码位置 MainActivity.java:26
        Log.e(TAG, "afterOnCheckChangeMethodCall getDeclaringTypeName->" + joinPoint.getSignature().getDeclaringTypeName());//com.wandering.sample.aspectj.Animal
        Log.e(TAG, "afterOnCheckChangeMethodCall getModifiers->" + joinPoint.getSignature().getModifiers());//方法修饰符 1--public
        Log.e(TAG, "afterOnCheckChangeMethodCall getName->" + joinPoint.getSignature().getName());//方法名 run
        Log.e(TAG, "afterOnCheckChangeMethodCall getDeclaringType->" + joinPoint.getSignature().getDeclaringType());//Animal.class
    }
}
