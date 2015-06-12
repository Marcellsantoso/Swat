package com.imb.swat.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by marcelsantoso.
 * <p/>
 * 6/12/15
 */
public class Preference {
    public final static String PREF_NAME   = "imbpref";
    public final static String LIST_DATA   = "imblistdata";
    public final static String LIST_RECENT = "imblistrecent";
    public final static String LIST_FAV    = "imblistfav";
    private        Context    context;
    private static Preference pref;

    public static Preference getInstance(Context context) {
        if (pref == null) {
            pref = new Preference(context);
        }

        return pref;
    }

    private Preference(Context context) {
        this.context = context;
    }

    // ================================================================================
    // Base Preference
    // ================================================================================
    private SharedPreferences getPreference() {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor editPreference() {
        return getPreference().edit();
    }

    public void clear() {
        editPreference().clear().commit();
    }

    // ================================================================================
    // Data
    // ================================================================================
    public void setString(String key, String text) {
        editPreference().putString(key, text).commit();
    }

    public String getString(String key) {
        return getPreference().getString(key, "");
    }

    public void setInt(String key, int value) {
        editPreference().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return getPreference().getInt(key, 0);
    }

    public void setBoolean(String key, boolean bool) {
        editPreference().putBoolean(key, bool).commit();
    }

    public boolean getBoolean(String key) {
        return getPreference().getBoolean(key, false);
    }

}
