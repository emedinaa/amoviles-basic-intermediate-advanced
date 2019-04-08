package com.emedinaa.myrestaurant.model.entity;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/11/18
 */
public class Item extends OrderViewType {

    private String order;
    private int amount;
    private String dishId;
    private String name;
    private double price;
    private double total;

    public Item(int amount) {
        this.amount = amount;
    }

    public Item(int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    public Item() {
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return this.price*this.amount;
    }

    @Override
    public boolean isItem() {
        return true;
    }

    @Override
    public String toString() {
        return "Item{" +
                "order='" + order + '\'' +
                ", amount=" + amount +
                ", dishId='" + dishId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", total=" + total +
                '}';
    }
}
