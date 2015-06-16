package com.imb.swat.Bean;

/**
 * Created by marcelsantoso.
 * <p/>
 * 6/10/15
 */
public class BeanImb extends Object {
    private int    id;
    private String name, img, imgMultiple, descShort, descLong, phone, email, address, url, raw;
    private double latitude, longitude;
    private boolean fav;

    public String getName() {
        return name;
    }

    public BeanImb setName(String name) {
        this.name = name;
        return this;
    }

    public String getImg() {
        return img;
    }

    public BeanImb setImg(String img) {
        this.img = img;
        return this;
    }

    public String getImgMultiple() {
        return imgMultiple;
    }

    public BeanImb setImgMultiple(String imgMultiple) {
        this.imgMultiple = imgMultiple;
        return this;
    }

    public String getDescShort() {
        return descShort;
    }

    public BeanImb setDescShort(String descShort) {
        this.descShort = descShort;
        return this;
    }

    public String getDescLong() {
        return descLong;
    }

    public BeanImb setDescLong(String descLong) {
        this.descLong = descLong;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public BeanImb setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public BeanImb setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public BeanImb setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public BeanImb setUrl(String url) {
        this.url = url;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public BeanImb setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public BeanImb setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public int getId() {
        return id;
    }

    public BeanImb setId(int id) {
        this.id = id;
        return this;
    }

    public boolean isFav() {
        return fav;
    }

    public BeanImb setFav(boolean fav) {
        this.fav = fav;
        return this;
    }

    public String getRaw() {
        return raw;
    }

    public BeanImb setRaw(String raw) {
        this.raw = raw;
        return this;
    }

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
}
