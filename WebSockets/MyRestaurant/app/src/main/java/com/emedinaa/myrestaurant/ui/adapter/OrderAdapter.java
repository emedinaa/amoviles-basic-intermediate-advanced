package com.emedinaa.myrestaurant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.model.entity.Item;
import com.emedinaa.myrestaurant.model.entity.OrderViewFooter;
import com.emedinaa.myrestaurant.model.entity.OrderViewType;

import java.util.List;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/11/18
 */
public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<OrderViewType> objects;

    private final int HEADER = 0;
    private final int ITEM = 1;
    private final int FOOTER = 2;

    public OrderAdapter(List<OrderViewType> objects) {
        this.objects = objects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case HEADER:
                View rowHeaderView = inflater.inflate(R.layout.row_cart_header, parent, false);
                viewHolder = new HeaderViewHolder(rowHeaderView);
                break;
            case ITEM:
                View rowItemView = inflater.inflate(R.layout.row_cart_item, parent, false);
                viewHolder = new ItemViewHolder(rowItemView);
                break;
            case FOOTER:
                View rowFooterView = inflater.inflate(R.layout.row_cart_footer, parent, false);
                viewHolder = new FooterViewHolder(rowFooterView);
                break;
            default:
                View defaultView = inflater.inflate(R.layout.row_cart_item, parent, false);
                viewHolder = new ItemViewHolder(defaultView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                renderHeader(headerViewHolder, position);
                break;
            case ITEM:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                renderItem(itemViewHolder, position);
                break;
            case FOOTER:
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                renderFooter(footerViewHolder, position);
                break;
            default:
                ItemViewHolder defaultViewHolder = (ItemViewHolder) holder;
                renderItem(defaultViewHolder, position);
                break;
        }
    }

    private void renderFooter(FooterViewHolder footerViewHolder, int position){
        OrderViewFooter orderViewFooter= (OrderViewFooter)objects.get(position);
        if(orderViewFooter!=null){
            footerViewHolder.textViewTotal.setText("S/."+orderViewFooter.getTotal());
        }
    }

    private void renderHeader(HeaderViewHolder headerViewHolder, int position){

    }

    private void renderItem(ItemViewHolder itemViewHolder, int position){
        Item item= (Item) objects.get(position);
        if(item!=null){
            itemViewHolder.textViewAmount.setText(String.valueOf(item.getAmount()));
            itemViewHolder.textViewName.setText(item.getName());
            itemViewHolder.textViewPrice.setText("S/."+item.getPrice());
            itemViewHolder.textViewTotal.setText("S/."+item.getTotal());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (objects.get(position).isHeader()) {
            return HEADER;
        } else if (objects.get(position).isItem()) {
            return ITEM;
        }else if (objects.get(position).isFooter()) {
            return FOOTER;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return this.objects.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewAmount;
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewTotal;
        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewAmount=  itemView.findViewById(R.id.textViewAmount);
            textViewName=  itemView.findViewById(R.id.textViewName);
            textViewPrice=  itemView.findViewById(R.id.textViewPrice);
            textViewTotal=  itemView.findViewById(R.id.textViewTotal);
        }
    }
    class HeaderViewHolder extends RecyclerView.ViewHolder{

        //public TextView tviTitle;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            //tviTitle= (TextView) itemView.findViewById(R.id.tviTitle);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewTotal;
        public FooterViewHolder(View itemView) {
            super(itemView);
            textViewTotal=  itemView.findViewById(R.id.textViewTotal);
        }
    }
}
