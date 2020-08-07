package com.example.aspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author youer
 * @date 2020/8/7
 */
@Aspect  //①
public class MethodAspect {

    private static final String TAG = "MethodAspect";

    @Pointcut("call(* com.example.aop2.Animal.fly(..))")//②
    public void callMethod() {
        Log.e(TAG, "callMethod->");
    }

    @Before("callMethod()")//③
    public void beforeMethodCall(JoinPoint joinPoint) {
        Log.e(TAG, "before->" + joinPoint.getTarget().toString()); //④
    }
}