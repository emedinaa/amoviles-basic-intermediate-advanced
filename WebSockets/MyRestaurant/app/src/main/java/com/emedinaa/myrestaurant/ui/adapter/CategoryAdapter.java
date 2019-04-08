package com.emedinaa.myrestaurant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.model.entity.Category;

import java.util.List;

/**
 * @author : Eduardo Medina
 * @see : https://github.com/googlesamples/android-RecyclerView/blob/master/Application/src/main/java/com/example/android/recyclerview/CustomAdapter.java
 * @since : 8/10/18
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final static String TAG="CATEGORYADAP";

    private List<Category> categories;

    public CategoryAdapter(List<Category> mCategories) {
        this.categories = mCategories;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_category, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.d(TAG, "Element " + position + " set.");

        final Category category= categories.get(position);
        viewHolder.getTextViewName().setText(category.getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });*/
            textViewName =  v.findViewById(R.id.textViewName);
        }

        public TextView getTextViewName() {
            return textViewName;
        }
    }
}
