package com.emedinaa.myrestaurant.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.emedinaa.core.media.PicassoImageLoader;
import com.emedinaa.core.ui.BasicFragment;
import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.data.MediaPath;
import com.emedinaa.myrestaurant.data.local.PreferencesHelper;
import com.emedinaa.myrestaurant.model.entity.Dish;
import com.emedinaa.myrestaurant.ui.listeners.DishDetailListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {} interface
 * to handle interaction events.
 */
public class DishDetailFragment extends BasicFragment {

    //private OnFragmentInteractionListener mListener;
    public static final String TAG= "DISHDETAILFRAGMENT";

    private ImageView imageViewDish;
    private TextView textViewTitle;
    private TextView textViewPrice;
    private TextView textViewDesc;
    private View buttonFavorites,buttonOrder;

    private Dish dish;
    private String imagesPath;
    private PicassoImageLoader picassoImageLoader;
    private DishDetailListener dishDetailListener;

    public DishDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dish_detail, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view){
        imageViewDish= view.findViewById(R.id.imageViewDish);
        textViewTitle= view.findViewById(R.id.textViewTitle);
        textViewPrice= view.findViewById(R.id.textViewPrice);
        textViewDesc= view.findViewById(R.id.textViewDesc);
        buttonFavorites= view.findViewById(R.id.buttonFavorites);
        buttonOrder= view.findViewById(R.id.buttonOrder);

        //events
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dishDetailListener!=null && dish!=null){
                    dishDetailListener.addDish(dish);
                }
            }
        });

        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { }
        });

        if(PreferencesHelper.session(getActivity())==null){
            buttonOrder.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkExtras();
        //imagesPath= ((MyRestaurantApp)getActivity().getApplication()).getImagesPath();
        imagesPath= MediaPath.getInstance().dishes();
        picassoImageLoader= new PicassoImageLoader();
        populate();
    }

    private void populate(){
        if(dish==null)return;

        SpannableString price= new SpannableString("S/."+dish.getPrice());
        price.setSpan(new SuperscriptSpan(),0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        price.setSpan(new RelativeSizeSpan(0.5f), 0,3, 0); // set size

        textViewTitle.setText(dish.getName());
        textViewDesc.setText(dish.getDesc());
        //textViewPrice.setText(dish.getPrice());
        textViewPrice.setText(price);
        picassoImageLoader.load(imagesPath+dish.getPhoto(),imageViewDish);
        setCustomTitle(dish.getName());
    }

    private void checkExtras(){
        if(getActivity()!=null && getActivity().getIntent() !=null && getActivity().getIntent().getExtras()!=null){
            Bundle bundle= getActivity().getIntent().getExtras();
            if(bundle.containsKey("DISH")){
                dish= (Dish) bundle.getSerializable("DISH");
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DishDetailListener) {
            dishDetailListener = (DishDetailListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DishDetailListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dishDetailListener = null;
    }

}
