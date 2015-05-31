package com.imb.swat.generics;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by marcelsantoso on 5/31/15.
 */
public class Helper {
    // ================================================================================
    // Common utilities
    // ================================================================================
    /**
     * Check if a string is empty (length == 0)
     *
     * @param string , to be checked
     * @return true if empty
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * Hide keyboard
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                                                         .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
