package com.kotlin.samples.kotlinapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kotlin.samples.kotlinapp.R;
import com.kotlin.samples.kotlinapp.model.Order;
import com.kotlin.samples.kotlinapp.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : Eduardo Medina
 * @see : https://github.com/googlesamples/android-RecyclerView/blob/master/Application/src/main/java/com/example/android/recyclerview/CustomAdapter.java
 * @since : 8/10/18
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final static String TAG="CATEGORYADAP";

    private List<Order> orders;

    public OrderAdapter(List<Order> mOrders) {
        this.orders = mOrders;
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_orders, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        final Order order= orders.get(position);
        User user= order.getClientId();
        String orderDate= order.getRegDate();
        Date date=null;
        try {
            date=new SimpleDateFormat("yyyy-mm-dd").parse(orderDate);//2019-04-08T22:37:12.607Z
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(user!=null){
            viewHolder.getTextViewName().setText(user.fullName());
        }else{
            viewHolder.getTextViewName().setText("");
        }
        if(date!=null){
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yy");
            String nDate=dateFormat.format(date);
            viewHolder.getTextViewDate().setText(nDate);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewClientName;
        private final TextView textViewDate;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });*/
            textViewClientName =  v.findViewById(R.id.textViewClientName);
            textViewDate =  v.findViewById(R.id.textViewDate);
        }

        public TextView getTextViewName() {
            return textViewClientName;
        }
        public TextView getTextViewDate() { return textViewDate; }
    }
}
