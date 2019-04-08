package com.emedinaa.myrestaurant.ui;

import android.os.Bundle;
import android.util.Log;

import com.emedinaa.core.ui.BasicActivity;
import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.data.local.Cart;
import com.emedinaa.myrestaurant.model.entity.Dish;
import com.emedinaa.myrestaurant.model.entity.Item;
import com.emedinaa.myrestaurant.ui.dialog.NumberPickerDialog;
import com.emedinaa.myrestaurant.ui.listeners.DishDetailListener;
import com.emedinaa.myrestaurant.ui.listeners.NumberPickerListener;

public class DishDetailActivity extends BasicActivity  implements
                        DishDetailListener,NumberPickerListener{

    private Dish dish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);
        enableBack();
        //checkExtras();
    }


    private void checkExtras(){
        if(getIntent() !=null && getIntent().getExtras()!=null){
            Bundle bundle= getIntent().getExtras();
            if(bundle.containsKey("DISH")){
                dish= (Dish) bundle.getSerializable("DISH");
            }
        }
    }

    @Override
    public void addDish(Dish mDish) {
        dish= mDish;
        NumberPickerDialog numberPickerDialog = new NumberPickerDialog();
        numberPickerDialog.show(getSupportFragmentManager(), "numberPickerDialog");
    }

    @Override
    public void onDialogOk(int value) {
        int amount =value;
        addDishToCard(amount);
    }

    @Override
    public void onDialogCancel(Object object) {

    }

    private void addDishToCard(int amount){
        Log.v("CONSOLE","amount "+amount);
        Item item= new Cart.CardMapper().convert(dish);
        item.setAmount(amount);
        Cart.addItem(item);

        Log.v("CONSOLE","item "+item.toString());
    }
}
