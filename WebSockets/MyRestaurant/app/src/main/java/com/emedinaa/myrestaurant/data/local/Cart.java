package com.emedinaa.myrestaurant.data.local;

import android.support.annotation.NonNull;
import android.util.Log;

import com.emedinaa.myrestaurant.model.entity.Dish;
import com.emedinaa.myrestaurant.model.entity.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author : Eduardo Medina
 * @see : http://www.javapractices.com/topic/TopicAction.do?Id=56
 * @since : 8/11/18
 */
public class Cart{
    private static @NonNull String orderId;
    private static @NonNull String userId;
    private static List<Item> items= new ArrayList<>();

    public static void createOrder(@NonNull String mUserId){
        orderId= UUID.randomUUID().toString();
        userId= mUserId;
        items= new ArrayList<>();
    }

    public static void addItem(Item item){
        item.setOrder(orderId);
        items.add(item);
    }

    public static void removeItem(Item item){
        items.remove(item);
    }

    public static double total(){
        double amountTotal=0;
        if(items==null)return amountTotal;

        for (Item item:items) {
           //amountTotal+=(item.getTotal());
           amountTotal+=(item.getAmount()*item.getPrice());
            Log.v("CONSOLE","item "+item.getName()+
            " amount "+item.getAmount()+ " price "+item.getPrice());
        }

        return amountTotal;
    }

    public static List<Item> getItems() {
        return items;
    }

    public static boolean isEmpty(){
        if(items==null)return true;
        return items.isEmpty();
    }

    public static void clear(){
        orderId= UUID.randomUUID().toString();
        items= new ArrayList<>();
    }

    public static JSONObject makeOrder(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("cliente_id", userId);
            JSONObject objItem= null;
            JSONArray jsonArray= new JSONArray();
            for (Item item:items) {
                objItem= new JSONObject();
                objItem.put("plato_id",item.getDishId());
                objItem.put("nombre",item.getName());
                objItem.put("precio",item.getPrice());
                objItem.put("cantidad",item.getAmount());
                jsonArray.put(objItem);
            }
            obj.put("orden",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return obj;
    }
    public static class CardMapper{

       public Item convert(Dish dish){
           Item item= new Item();
           if(dish==null) return item;
           item.setName(dish.getName());
           item.setDishId(dish.getId());
           item.setPrice(Double.parseDouble(dish.getPrice()));
           return item;
       }
    }

}
