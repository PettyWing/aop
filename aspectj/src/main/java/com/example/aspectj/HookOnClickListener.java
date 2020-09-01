package com.example.aspectj;

import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/**
 * @author youer
 * @date 2020/8/28
 */
public class HookOnClickListener {
    private static final String TAG = "HookOnClickListener";

    public static void hook(View view) {
        view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void sendAccessibilityEvent(View host, int eventType) {
                super.sendAccessibilityEvent(host, eventType);
                switch (eventType) {
                    // 页面点击
                    case AccessibilityEvent.TYPE_VIEW_CLICKED:
                        Log.d(TAG, "sendAccessibilityEvent: " + host.getContentDescription());
                        break;
                    // 页面输入
//                    case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
//                        TrackEvent.postUTSendKey(host.getContentDescription().toString(), ((TextView) host).getText().toString());
//                        Log.d(TAG, "postUTSendKey: " + host.getContentDescription() + "|value:" + ((TextView) host).getText().toString());
//                    break;
                    // 其他
                    default:
                        break;
                }
            }
        });
    }
}
