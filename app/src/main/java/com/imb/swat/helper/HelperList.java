package com.imb.swat.helper;

/**
 * Created by marcelsantoso.
 * <p/>
 * 6/12/15
 */
public class HelperList {
    public static String parseId(int id) {
        return "{" + Integer.toString(id) + "}";
    }

    public static boolean addToFav(Preference pref, int id) {
        boolean isAdded = false;
        String  data    = pref.getString(Preference.LIST_FAV);
        String  text    = HelperList.parseId(id);
        if (data.contains(text)) {
            data = data.replace(text, "");
            isAdded = false;
        } else {
            data += text;
            isAdded = true;
        }

        pref.setString(Preference.LIST_FAV, data);

        return isAdded;
    }

    public static void addToRecent(Preference pref, int id) {
        String data = pref.getString(Preference.LIST_RECENT);
        String text = HelperList.parseId(id);
        if (data.contains(text)) {
            data = data.replace(text, "");
        }
        data = text + data;

        pref.setString(Preference.LIST_RECENT, data);
    }

}
