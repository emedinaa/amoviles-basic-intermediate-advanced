package com.emedinaa.myrestaurant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emedinaa.core.helpers.StringHelper;
import com.emedinaa.core.media.PicassoImageLoader;
import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.data.MediaPath;
import com.emedinaa.myrestaurant.model.entity.Dish;

import java.util.List;

/**
 * @author : Eduardo Medina
 * @see : https://github.com/googlesamples/android-RecyclerView/blob/master/Application/src/main/java/com/example/android/recyclerview/CustomAdapter.java
 * @since : 8/10/18
 */
public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {

    private final static String TAG="DISHADAP";
    //private final static String PATH="platos/";

    private List<Dish> dishes;
    private String imagePath;
    private PicassoImageLoader picassoImageLoader;
    private StringHelper stringHelper;

    public DishAdapter(List<Dish> mDishes) {
        this.dishes = mDishes;
        picassoImageLoader= new PicassoImageLoader();
        stringHelper= new StringHelper();
    }

    public DishAdapter(List<Dish> mDishes,String mImagePath) {
        this.dishes = mDishes;
        this.imagePath= mImagePath;
        picassoImageLoader= new PicassoImageLoader();
        stringHelper= new StringHelper();
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_dish, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        final Dish dish= dishes.get(position);
        final String dishPath= MediaPath.getInstance().dishes()+dish.getPhoto();
        //final String price= "S/."+dish.getPrice();
        //final CharSequence price= stringHelper.spannableStringBuilder("S/."+dish.getPrice());
        SpannableString price= new SpannableString("S/."+dish.getPrice());
        price.setSpan(new SuperscriptSpan(),0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        price.setSpan(new RelativeSizeSpan(0.5f), 0,3, 0); // set size

        viewHolder.getTextViewTitle().setText(dish.getName());
        viewHolder.getTextViewDesc().setText(dish.getDesc());
        viewHolder.getTextViewPrice().setText(price);

        //picassoImageLoader.load(imagePath+PATH+dish.getPhoto(),viewHolder.getImageViewDish());
        picassoImageLoader.load(dishPath,viewHolder.getImageViewDish());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDesc;
        private final TextView textViewPrice;
        private final ImageView imageViewDish;

        public ViewHolder(View v) {
            super(v);
            textViewTitle =  v.findViewById(R.id.textViewTitle);
            textViewDesc =  v.findViewById(R.id.textViewDesc);
            textViewPrice =  v.findViewById(R.id.textViewPrice);
            imageViewDish =  v.findViewById(R.id.imageViewDish);
        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewDesc() {
            return textViewDesc;
        }

        public TextView getTextViewPrice() {
            return textViewPrice;
        }

        public ImageView getImageViewDish() {
            return imageViewDish;
        }
    }
}
