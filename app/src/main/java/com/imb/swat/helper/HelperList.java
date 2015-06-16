package com.imb.swat.helper;

import com.imb.swat.adapter.AdapterList;

/**
 * Created by marcelsantoso.
 * <p/>
 * 6/12/15
 */
public class HelperList {
    public static String parseId(int id) {
        return "{" + Integer.toString(id) + "}";
    }

    public static boolean addToFav(AdapterList adapter, int id) {
        boolean isAdded;
        String  data = Preference.getInstance(adapter.getContext()).getString(Preference.LIST_FAV);
        String  text = HelperList.parseId(id);
        if (data.contains(text)) {
            data = data.replace(text, "");
            isAdded = false;
        } else {
            data += text;
            isAdded = true;
        }

        Preference.getInstance(adapter.getContext()).setString(Preference.LIST_FAV, data);
        adapter.reload();

        return isAdded;
    }

    public static void addToRecent(Preference pref, int id) {
        String data = pref.getString(Preference.LIST_RECENT);
        String text = HelperList.parseId(id);


        if (data.contains(text)) {
            data = data.replace(text, "");
        } else {
            // Count how many records
            int lastIndex = 0;
            int count = 0;
            while (lastIndex != -1) {
                lastIndex = data.indexOf("{", lastIndex);
                if (lastIndex != -1) {
                    count++;
                    lastIndex += "{".length();
                }
            }
            if (count >= 5) {
                data = data.substring(0, data.lastIndexOf("{") - 1);
            }
        }

        data = text + data;

        pref.setString(Preference.LIST_RECENT, data);
    }

}
