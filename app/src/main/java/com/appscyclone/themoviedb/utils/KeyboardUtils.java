package com.appscyclone.themoviedb.utils;
/*
 * Created by HoangDong on 30/11/2017.
 */

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtils {

    public static void setupUI(View view, final Activity activity) {
        setupUI(view, activity, false);
    }

    public static void setupUI(View view, final Activity activity, final boolean isClearFocus) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                if(view.getTag()!=null)
                    return false;
                if (isClearFocus && view != null)
                    view.requestFocus();
                hideSoftKeyboard(activity);
                return false;
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity,isClearFocus);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null && inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
        }
    }
}
