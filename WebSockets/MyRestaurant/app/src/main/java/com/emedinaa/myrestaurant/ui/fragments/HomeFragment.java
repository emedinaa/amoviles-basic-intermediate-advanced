package com.emedinaa.myrestaurant.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.emedinaa.core.ui.BasicFragment;
import com.emedinaa.core.ui.events.RecyclerClickListener;
import com.emedinaa.core.ui.events.RecyclerTouchListener;
import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.data.callback.DataCallback;
import com.emedinaa.myrestaurant.data.local.PreferencesHelper;
import com.emedinaa.myrestaurant.model.entity.Category;
import com.emedinaa.myrestaurant.model.entity.User;
import com.emedinaa.myrestaurant.model.interactors.CategoriesRemoteInteractor;
import com.emedinaa.myrestaurant.ui.adapter.CategoryAdapter;
import com.emedinaa.myrestaurant.ui.listeners.HomeListener;

import java.util.List;

/**
 * link : https://github.com/googlesamples/android-RecyclerView/blob/master/Application/src/main/java/com/example/android/recyclerview/RecyclerViewFragment.java
 */
public class HomeFragment extends BasicFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG= "HOMEFRAGMENT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView textViewEmpty;
    private RecyclerView recyclerViewCategories;
    protected RecyclerView.LayoutManager mLayoutManager;

    private List<Category> categories;
    private CategoriesRemoteInteractor categoriesRemoteInteractor;
    private HomeListener homeListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view){
        textViewEmpty= view.findViewById(R.id.textViewEmpty);
        recyclerViewCategories= view.findViewById(R.id.recyclerViewCategories);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewCategories.setLayoutManager(mLayoutManager);
        setUpAnimation();

        //events
        recyclerViewCategories.addOnItemTouchListener(new RecyclerTouchListener(
                getActivity(), recyclerViewCategories, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(categories!=null){
                    Category category= categories.get(position);
                    selectCategory(category);
                }
            }

            @Override
            public void onLongClick(View view, int position) { }
        }
        ));
    }

    private void setUpAnimation(){
        AnimationSet animationSet= new AnimationSet(true);
        TranslateAnimation translateAnimation=new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(500);//1000
        animationSet.addAnimation(translateAnimation);

        LayoutAnimationController layoutAnimationController=new LayoutAnimationController(animationSet, 0.2f);
        recyclerViewCategories.setLayoutAnimation(layoutAnimationController);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpInteractors();
        //retrieveCategories();
        retrieveCategoriesWT();
    }

    private void retrieveCategoriesWT(){
        User user= PreferencesHelper.session(getContext());
        if(user==null)return;

        categoriesRemoteInteractor.categoriesWT(user.getToken(),new DataCallback() {
            @Override
            public void onSuccess(Object object) {
                if(object !=null && object instanceof List<?>){
                    List<Category> mCategories= (List<Category>) object;
                    if(mCategories.isEmpty()){
                        empty();
                    }else {
                        renderCategories(mCategories);
                    }
                }else{}
            }

            @Override
            public void onFailure(Exception exception) {
                showMessage(exception.getMessage());
            }
        });
    }

    private void retrieveCategories(){
        categoriesRemoteInteractor.categories(new DataCallback() {
            @Override
            public void onSuccess(Object object) {
                if(object !=null && object instanceof List<?>){
                    List<Category> mCategories= (List<Category>) object;
                    if(mCategories.isEmpty()){
                        empty();
                    }else {
                        renderCategories(mCategories);
                    }
                }else{}
            }

            @Override
            public void onFailure(Exception exception) {
                showMessage(exception.getMessage());
            }
        });
    }

    private void setUpInteractors(){
        categoriesRemoteInteractor= new CategoriesRemoteInteractor();
    }

    private void renderCategories(List<Category> mCategories){
        this.categories=mCategories;
        recyclerViewCategories.setAdapter(new CategoryAdapter(categories));
    }

    private void empty(){
        recyclerViewCategories.setVisibility(View.GONE);
        textViewEmpty.setVisibility(View.VISIBLE);
    }

    /*private void showMessage(@NonNull String message){
        Toast.makeText(getActivity(), message,Toast.LENGTH_SHORT).show();
    }*/

    private void selectCategory(Category category){
        if(homeListener!=null){
            homeListener.selectCategory(category);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            homeListener = (HomeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
        if(categoriesRemoteInteractor!=null){
            categoriesRemoteInteractor.cancelOperation();
        }
    }
}
