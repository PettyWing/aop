package com.example.aop2;

import android.util.Log;

/**
 * @author youer
 * @date 2020/8/7
 */
public class Animal {
    private static final String TAG = "Animal";

    public void fly() {
        Log.e(TAG, "animal fly method:" + this.toString() + "#fly");
    }
}
