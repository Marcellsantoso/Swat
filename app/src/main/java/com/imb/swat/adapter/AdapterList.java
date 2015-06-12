package com.imb.swat.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.imb.swat.Bean.BeanImb;
import com.imb.swat.R;
import com.imb.swat.generics.BaseConstants;
import com.imb.swat.helper.HelperList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdapterList
        extends BaseAdapterList {
    public AdapterList(Context context) {
        super(context, new ArrayList<BeanImb>(), R.layout.cell);
    }

    @Override
    public int setAppTheme() {
        // TODO Auto-generated method stub
        return R.style.CustomActionBarTheme;
    }

    @Override
    public Object setViewHolder() {
        // TODO Auto-generated method stub
        return new ViewHolder();
    }

    @Override
    public View initView(View view, Object objectHolder) {
        ViewHolder holder = (ViewHolder) objectHolder;
        holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        holder.tvDesc = (TextView) view.findViewById(R.id.tvDesc);

        return view;
    }

    @Override
    public void setView(Object objectHolder, int position) {
        BeanImb    bean   = (BeanImb) getItem(position);
        ViewHolder holder = (ViewHolder) objectHolder;

        holder.tvTitle.setText(bean.getName());
        holder.tvDesc.setText(bean.getDescShort());
    }

    public void convert(String text) {
        convertWithFilter(text, null);
    }

    public void convertWithFilter(String text, String filter) {
        try {
            JSONObject json = new JSONObject(text);

            String server = json.getString("server");
            JSONArray jArr = json.getJSONArray(BaseConstants.RESULTS);
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject j = jArr.getJSONObject(i);
                BeanImb b = new BeanImb();
                b.setId(j.getInt("id"));
                if (filter == null || filter.contains(HelperList.parseId(b.getId()))) {
                    b.setName(j.getString("name"));
                    b.setDescShort(j.getString("desc_short"));
                    b.setDescLong(j.getString("desc_long"));
                    b.setLatitude(j.getDouble("latitude"));
                    b.setLongitude(j.getDouble("longitude"));
                    b.setPhone(j.getString("phone"));
                    b.setEmail(j.getString("email"));
                    b.setAddress(j.getString("address"));
                    b.setUrl(j.getString("url"));
                    if (j.getString("img_main").length() > 0)
                        b.setImg(server + j.getString("img_main"));
                    if (j.getString("img_multiple").length() > 0)
                        b.setImgMultiple(server + j.getString("img_multiple").replace(";", ";" + server));

                    this.add(b);
                }
            }

            this.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder {
        TextView tvTitle;
        TextView tvDesc;
    }
}
