package com.emedinaa.myrestaurant.model.entity;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/11/18
 */
public class OrderViewFooter extends OrderViewType {
    private double total;

    public OrderViewFooter(double total) {
        this.total = total;
    }

    public OrderViewFooter() {
    }

    @Override
    public boolean isFooter() {
        return true;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
