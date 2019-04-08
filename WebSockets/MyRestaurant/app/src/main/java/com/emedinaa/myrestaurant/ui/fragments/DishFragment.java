package com.emedinaa.myrestaurant.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emedinaa.core.ui.BasicFragment;
import com.emedinaa.core.ui.decorators.DividerItemDecoration;
import com.emedinaa.core.ui.events.RecyclerClickListener;
import com.emedinaa.core.ui.events.RecyclerTouchListener;
import com.emedinaa.myrestaurant.MyRestaurantApp;
import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.data.callback.DataCallback;
import com.emedinaa.myrestaurant.model.entity.Category;
import com.emedinaa.myrestaurant.model.entity.Dish;
import com.emedinaa.myrestaurant.model.interactors.DishesRemoteInteractor;
import com.emedinaa.myrestaurant.ui.DishDetailActivity;
import com.emedinaa.myrestaurant.ui.adapter.DishAdapter;
import com.emedinaa.myrestaurant.ui.listeners.HomeListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {} interface
 * to handle interaction events.
 */
public class DishFragment extends BasicFragment {
    public static final String TAG= "DISHFRAGMENT";

    private RecyclerView recyclerViewDish;
    protected RecyclerView.LayoutManager mLayoutManager;

    private List<Dish> dishes;
    private DishesRemoteInteractor dishesRemoteInteractor;
    private HomeListener homeListener;

    private String imagesPath;

    public DishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dish, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view){
        recyclerViewDish= view.findViewById(R.id.recyclerViewDish);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewDish.setLayoutManager(mLayoutManager);
        recyclerViewDish.addItemDecoration(new DividerItemDecoration(
                ContextCompat.getDrawable(getActivity(),R.drawable.line)));
        //events
        recyclerViewDish.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recyclerViewDish, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(dishes!=null){
                    Dish dish= dishes.get(position);
                    goToDishDetails(dish);
                }
            }

            @Override
            public void onLongClick(View view, int position) { }
        }
        ));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imagesPath= ((MyRestaurantApp)getActivity().getApplication()).getImagesPath();
        setUpInteractors();
        retrieveDishes();
    }

    private void setUpInteractors(){
        dishesRemoteInteractor= new DishesRemoteInteractor();
    }

    private void retrieveDishes(){
        DataCallback dataCallback= new DataCallback() {
            @Override
            public void onSuccess(Object object) {
                if(object !=null && object instanceof List<?>){
                    List<Dish> mDishes= (List<Dish>) object;
                    if(mDishes.isEmpty()){
                        empty();
                    }else {
                        renderDishes(mDishes);
                    }
                }else{}
            }

            @Override
            public void onFailure(Exception exception) {
                showMessage(exception.getMessage());
            }
        };
        if(homeListener!=null && homeListener.currentCategory()!=null){
            Category category= homeListener.currentCategory();
            dishesRemoteInteractor.dishesByCategory(category.getId(),dataCallback) ;
        }else {
            dishesRemoteInteractor.dishes(dataCallback);
        }
        //dishesRemoteInteractor.dishesByCategory("",dataCallback);
    }

    private void renderDishes(List<Dish> mDishes){
        this.dishes=mDishes;
        recyclerViewDish.setAdapter(new DishAdapter(dishes,imagesPath));
        recyclerViewDish.setVisibility(View.VISIBLE);
    }

    private void empty(){
        recyclerViewDish.setVisibility(View.GONE);
        //textViewEmpty.setVisibility(View.VISIBLE);
    }
    private void goToDishDetails(Dish dish){
        Bundle bundle= new Bundle();
        bundle.putSerializable("DISH",dish);
        /*Intent intent= new Intent(getActivity(), DishDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);*/
        nextActivity(new Intent(getActivity(), DishDetailActivity.class),
                bundle,false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            homeListener = (HomeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dishesRemoteInteractor!=null){
            dishesRemoteInteractor.cancelOperation();
        }
    }
}
