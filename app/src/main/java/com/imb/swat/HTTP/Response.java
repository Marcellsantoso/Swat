package com.imb.swat.HTTP;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.List;
import java.util.Map;

public class Response {
    private int statusCode = 400;
    private JSONObject content;
    private Map<String, List<String>> headerContent;

    public static final Response getDummyResponse() {
        return new Response(200, "");
    }

    public Response(int statusCode, String content) {
        this.statusCode = statusCode;
        try {
            this.content = new JSONObject(new JSONTokener(content));
        } catch (JSONException e) {

            e.printStackTrace();

            try {
                JSONArray jAr = new JSONArray(new JSONTokener(content));
                this.content = new JSONObject();
                this.content.put(HTTPAsyncTask.RESULTS, jAr);
            } catch (JSONException ex) {
                this.content = new JSONObject();
                ex.printStackTrace();

                Log.e("Response", content);
            }
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public JSONObject getContent() {
        return content;
    }

    public void setHeaderContent(Map<String, List<String>> headerContent) {
        this.headerContent = headerContent;
    }

}
