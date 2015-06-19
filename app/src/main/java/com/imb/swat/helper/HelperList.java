package com.imb.swat.helper;

import com.imb.swat.adapter.AdapterList;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by marcelsantoso.
 * <p/>
 * 6/12/15
 */
public class HelperList {
    public static final String TAG_OPEN  = "";
    public static final String TAG_CLOSE = ",";

    public static String parseText(String text) {
        return TAG_OPEN + text + TAG_CLOSE;
    }

    public static String deparseText(String text) {
        if (text.contains(TAG_OPEN)) {
            return text.replace(TAG_OPEN, "").replace(TAG_CLOSE
                    , "");
        }

        return text;
    }

    public static boolean addToFav(AdapterList adapter, String rawObj) {
        boolean isAdded = HelperList.addToFav(Preference.getInstance(adapter.getContext()), rawObj);
        adapter.reload();

        return isAdded;
    }

    public static boolean addToFav(Preference pref, String rawObj) {
        boolean isAdded;
        String  data = pref.getString(Preference.LIST_FAV);
        String  text = HelperList.parseText(rawObj);
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

    public static void addToRecent(Preference pref, String rawObj) {
        String data = pref.getString(Preference.LIST_RECENT);
        String text = HelperList.parseText(rawObj);

        int count;
        if (!Helper.isEmpty(data)) {
            try {
                if (data.contains(text)) {
                    data = data.replace((text), "");
                }
                JSONArray jArr = new JSONArray("[" + data + "]");
                count = jArr.length();

                if (count > 4) {
                    jArr.remove(4);
                }

                data = jArr.toString().substring(1, jArr.toString().length() - 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        data = text + data;
        pref.setString(Preference.LIST_RECENT, data);
    }

}
