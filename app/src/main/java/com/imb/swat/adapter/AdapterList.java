package com.imb.swat.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.imb.swat.Bean.BeanImb;
import com.imb.swat.R;
import com.imb.swat.generics.BaseConstants;
import com.imb.swat.helper.Helper;
import com.imb.swat.helper.HelperList;
import com.imb.swat.views.ImageViewLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class AdapterList
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
        holder.img = (ImageViewLoader) view.findViewById(R.id.img);
        holder.btnFav = (ImageButton) view.findViewById(R.id.btnFav);

        return view;
    }

    @Override
    public void setView(Object objectHolder, int position) {
        BeanImb    bean   = (BeanImb) getItem(position);
        ViewHolder holder = (ViewHolder) objectHolder;

        holder.tvTitle.setText(bean.getName());
        holder.tvDesc.setText(bean.getDescShort());
        holder.img.loadImage(bean.getImg(), true);
        holder.img.setPopupOnClick(true);

        // Remove touch feedback
        holder.img.setImageOverlay(0);

        if (!bean.isFav())
            holder.btnFav.setVisibility(View.GONE);
        else {
            holder.btnFav.setVisibility(View.VISIBLE);
            holder.btnFav.setOnClickListener(new ListenerFav(bean.getId(), this));
        }
    }

    public void convert(String text, String textFav) {
        convertWithFilter(text, textFav, null);
    }

    public void convertWithFilter(String text, String textFav, String filter) {
        try {
            this.clear();

            JSONObject json = new JSONObject(text);

            String server = json.getString("server");
            JSONArray jArr = json.getJSONArray(BaseConstants.RESULTS);
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject j = jArr.getJSONObject(i);
                BeanImb b = new BeanImb();
                b.setId(j.getInt("id"));
                b.setRaw(j.toString());
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
                    b.setFav(textFav.contains(HelperList.parseId(b.getId())));
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

    public void convertWithFilterSort(String text, String textFav, String filter) {
        try {
            if (Helper.isEmpty(filter) || Helper.isEmpty(text))
                return;

            this.clear();

            JSONObject json = new JSONObject(text);

            String server = json.getString("server");
            JSONArray jArr = json.getJSONArray(BaseConstants.RESULTS);

            filter = filter.replace("{", "").replace("}", ",");
            String[] arr = filter.split(",");
            for (String id : arr) {
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject j = jArr.getJSONObject(i);
                    BeanImb b = new BeanImb();
                    b.setId(j.getInt("id"));
                    b.setRaw(j.toString());
                    if (Integer.parseInt(id) == b.getId()) {
                        b.setName(j.getString("name"));
                        b.setDescShort(j.getString("desc_short"));
                        b.setDescLong(j.getString("desc_long"));
                        b.setLatitude(j.getDouble("latitude"));
                        b.setLongitude(j.getDouble("longitude"));
                        b.setPhone(j.getString("phone"));
                        b.setEmail(j.getString("email"));
                        b.setAddress(j.getString("address"));
                        b.setUrl(j.getString("url"));
                        b.setFav(textFav.contains(HelperList.parseId(b.getId())));
                        if (j.getString("img_main").length() > 0)
                            b.setImg(server + j.getString("img_main"));
                        if (j.getString("img_multiple").length() > 0)
                            b.setImgMultiple(server + j.getString("img_multiple").replace(";", ";" + server));

                        this.add(b);
                    }
                }
            }


            this.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public abstract void reload();

    public class ViewHolder {
        TextView        tvTitle;
        TextView        tvDesc;
        ImageViewLoader img;
        ImageButton     btnFav;
    }

    public class ListenerFav implements View.OnClickListener {
        int         id;
        AdapterList adapter;

        public ListenerFav(int id, AdapterList adapter) {
            this.id = id;
            this.adapter = adapter;
        }

        @Override
        public void onClick(View v) {
            HelperList.addToFav(adapter, id);
            AdapterList.this.notifyDataSetChanged();
        }
    }
}
