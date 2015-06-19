package com.imb.swat.Bean;

/**
 * Created by marcelsantoso.
 * <p/>
 * 6/18/15
 */
public class BeanAttr {
    private String key, desc;

    public String getDesc() {
        return desc;
    }

    public BeanAttr setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getKey() {

        return key;
    }

    public BeanAttr setKey(String key) {
        this.key = key;
        return this;
    }
}