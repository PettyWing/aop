package com.example.aspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 示例基本用法 全局监听Activity生命周期
 * 在有继承结构的体系中，代码会被织入多次
 *
 */
@Aspect
public class MethodAspect6 {
    private static final String TAG = "MethodAspect6";

    @Pointcut("execution(* androidx.appcompat.app.AppCompatActivity+.on*(..))")
    public void callMethod() {
    }

    @After("callMethod()")
    public void aroundMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, "around 1->");

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            Log.e(TAG, "arg->:" + arg);
        }

        Log.e(TAG, "around getTarget->" + joinPoint.getTarget().toString());// 被切面的animal对象
        Log.e(TAG, "around getThis->" + joinPoint.getThis()); //切面代码运行所在的类对象
        Log.e(TAG, "around getKind->" + joinPoint.getKind());//切面的类型 method-call
        Log.e(TAG, "around getSourceLocation->" + joinPoint.getSourceLocation());//源码位置 MainActivity.java:26
        Log.e(TAG, "around getDeclaringTypeName->" + joinPoint.getSignature().getDeclaringTypeName());//com.wandering.sample.aspectj.Animal
        Log.e(TAG, "around getModifiers->" + joinPoint.getSignature().getModifiers());//方法修饰符 1--public
        Log.e(TAG, "around getName->" + joinPoint.getSignature().getName());//方法名 run
        Log.e(TAG, "around getDeclaringType->" + joinPoint.getSignature().getDeclaringType());//Animal.class
    }

}