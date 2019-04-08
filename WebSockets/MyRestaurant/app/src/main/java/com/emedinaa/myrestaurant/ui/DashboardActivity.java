package com.emedinaa.myrestaurant.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.emedinaa.core.ui.dialog.SimpleDialog;
import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.data.DataInjector;
import com.emedinaa.myrestaurant.data.socket.SocketManager;
import com.emedinaa.myrestaurant.model.entity.Category;
import com.emedinaa.myrestaurant.ui.fragments.ChefFragment;
import com.emedinaa.myrestaurant.ui.fragments.DishFragment;
import com.emedinaa.myrestaurant.ui.fragments.HomeFragment;
import com.emedinaa.myrestaurant.ui.fragments.OrderFragment;
import com.emedinaa.myrestaurant.ui.listeners.HomeListener;

/**
 * @author : Eduardo Medina
 * @see : https://developer.android.com/index.html
 * @since : 8/4/18
 */
public class DashboardActivity extends BasicActivity implements HomeListener,SimpleDialog.SimpleDialogListener{
    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment=null;
    private Category category=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ui();
        setFirstItem();

        //Cart.createOrder("200");
    }

    private void setFirstItem(){
        currentFragment= new HomeFragment();
        changeFragment(currentFragment,HomeFragment.TAG);
    }

    private void ui(){
        bottomNavigationView= findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment= null;
                int tab=0;
                String tag="";

                switch (item.getItemId()){
                    case R.id.action_home :
                        tab=0;
                        tag=HomeFragment.TAG;
                        fragment = HomeFragment.newInstance("","");
                        break;
                    case R.id.action_plates:
                        tab = 1;
                        tag=DishFragment.TAG;
                        fragment = new DishFragment();
                        break;
                    case R.id.action_orders:
                        tab=2;
                        tag=OrderFragment.TAG;
                        fragment = OrderFragment.newInstance("","");
                        break;
                    case R.id.action_cheff:
                        tab=3;
                        tag=ChefFragment.TAG;
                        fragment = new ChefFragment();
                        break;
                }

                if(fragment!=null && !tag.isEmpty()){
                    changeFragment(fragment,tag);
                }
                return true;
            }
        });
    }

    private void   changeFragment(@NonNull Fragment fragment,String tag){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.flayContainer, fragment,tag);
        fragmentTransaction.commit();
    }

    //home listener
    @NonNull
    @Override
    public Category currentCategory() {
        return category;
    }

    @Override
    public void selectCategory(@NonNull Category mCategory) {
        category= mCategory;
        //change fragment
        currentFragment= new DishFragment();
        changeFragment(currentFragment,DishFragment.TAG);
    }

    @Override
    public void onAction() {
        if(getSupportFragmentManager().findFragmentByTag(OrderFragment.TAG)!=null){
            OrderFragment orderFragment=
                    (OrderFragment) getSupportFragmentManager().findFragmentByTag(OrderFragment.TAG);
            orderFragment.clearCart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SocketManager socketManager= DataInjector.getInstance().socketManager();
        socketManager.clearSession();
    }
}
