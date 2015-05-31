package Generics;

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
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                                                         .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
