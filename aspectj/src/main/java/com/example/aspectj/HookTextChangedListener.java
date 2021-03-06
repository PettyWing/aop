package com.example.aspectj;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * @author youer
 * @date 2020/8/20
 */
public class HookTextChangedListener {
    private static final String TAG = "HookTextChangedListener";

    /**
     * @param editText
     */
    public static void hook(final EditText editText) {//
        ArrayList<TextWatcher> textWatchers = (ArrayList<TextWatcher>) SuperClassReflectionUtils.getFieldValue(editText, "mListeners");
        if (textWatchers != null && !textWatchers.isEmpty()) {
            for (TextWatcher watcher : textWatchers) {
                // 去掉重复的滑动监听
                if (watcher instanceof HookTextWatcher) {
                    return;
                }
            }
        }
        editText.addTextChangedListener(new HookTextWatcher(editText));
    }

    static class HookTextWatcher implements TextWatcher {

        private EditText editText;

        public HookTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.d(TAG, "view:" + editText.getContentDescription() + "\nword:" + s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}

