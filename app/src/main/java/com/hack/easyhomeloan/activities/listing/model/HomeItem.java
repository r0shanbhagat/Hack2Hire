package com.hack.easyhomeloan.activities.listing.model;

public class HomeItem {
    private int product_id;
    private String product_name;
    private int product_image;
    private float product_price;
    private float product_intrest;
    private String homeType;

    public HomeItem(int product_id, String homeType, String product_name, int product_image, float product_price, float product_intrest) {
        this.product_id = product_id;
        this.homeType = homeType;
        this.product_name = product_name;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_intrest = product_intrest;
    }

    public HomeItem() {
    }

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public float getShippingPrice() {
        return product_intrest;
    }

    public void setShippingPrice(float shipping_price) {
        this.product_intrest = shipping_price;
    }

    public int getProductId() {
        return product_id;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public int getProductImage() {
        return product_image;
    }

    public void setProductImage(int product_image) {
        this.product_image = product_image;
    }

    public float getProductPrice() {
        return product_price;
    }

    public void setProduct_price(float product_price) {
        this.product_price = product_price;
    }
}
