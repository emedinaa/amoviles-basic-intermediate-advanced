package com.emedinaa.myrestaurant.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emedinaa.core.ui.BasicFragment;
import com.emedinaa.core.ui.decorators.DividerItemDecoration;
import com.emedinaa.core.ui.dialog.SimpleDialog;
import com.emedinaa.myrestaurant.R;
import com.emedinaa.myrestaurant.data.DataInjector;
import com.emedinaa.myrestaurant.data.local.Cart;
import com.emedinaa.myrestaurant.data.socket.SocketConstant;
import com.emedinaa.myrestaurant.data.socket.SocketManager;
import com.emedinaa.myrestaurant.data.socket.SocketMapper;
import com.emedinaa.myrestaurant.data.socket.SocketResponse;
import com.emedinaa.myrestaurant.model.entity.OrderViewFooter;
import com.emedinaa.myrestaurant.model.entity.OrderViewHeader;
import com.emedinaa.myrestaurant.model.entity.OrderViewType;
import com.emedinaa.myrestaurant.ui.adapter.OrderAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.socket.client.Ack;
import io.socket.client.Socket;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {} interface
 * to handle interaction events.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BasicFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG= "ORDERFRAGMENT";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SocketManager socketManager;
    private Socket socket;

    private RecyclerView recyclerViewOrder;
    private View textOrder;
    protected RecyclerView.LayoutManager mLayoutManager;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        View view= inflater.inflate(R.layout.fragment_order, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view) {
        recyclerViewOrder = view.findViewById(R.id.recyclerViewOrder);
        textOrder = view.findViewById(R.id.textOrder);
        mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerViewOrder.setLayoutManager(mLayoutManager);
        recyclerViewOrder.addItemDecoration(new DividerItemDecoration(
                ContextCompat.getDrawable(getActivity(),R.drawable.line)));

        //events
        textOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Cart.isEmpty()){
                    sendOrder();
                }else{
                    //cart empty
                    Toast.makeText(getContext(),"Carrito vacío",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        socketManager= DataInjector.getInstance().socketManager();
        socket= socketManager.socket();
        renderOrder();
        /*List<OrderViewType> tmp= new ArrayList<>();
        tmp.add(new OrderViewHeader());
        tmp.add(new Item(1, "Arroz con Pato"));
        tmp.add(new Item(2, "Arroz con Pollo"));
        tmp.add(new Item(1, "Ceviche"));
        tmp.add(new Item(1, "Estofado de Carne"));
        tmp.add(new Item(2, "Arroz con Pollo"));
        tmp.add(new Item(1, "Ceviche"));
        tmp.add(new Item(1, "Arroz con Pato"));
        tmp.add(new Item(2, "Arroz con Pollo"));
        tmp.add(new Item(2, "Arroz con Pollo"));
        tmp.add(new Item(2, "Arroz con Pollo"));
        tmp.add(new OrderViewFooter());
        recyclerViewOrder.setAdapter(new OrderAdapter(tmp));*/
    }

    private void renderOrder(){
        if(Cart.getItems()==null)return;

        double total= Cart.total();
        List<OrderViewType> tmp= new ArrayList<>();
        tmp.add(new OrderViewHeader());
        tmp.addAll(Cart.getItems());
        tmp.add(new OrderViewFooter(total));

        recyclerViewOrder.setAdapter(new OrderAdapter(tmp));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    private void sendOrder(){
        Log.v("CONSOLE", "sendOrder ... socketManager :"+socketManager +
                " isConnected "+socketManager.isConnected());
       if(socketManager!=null && socketManager.isConnected()){
           JSONObject jsonObject= Cart.makeOrder();
           /*socket.emit(SocketConstant.EMIT_ORDER, jsonObject, new Ack() {
               @Override
               public void call(Object... args) {
                   Log.v("CONSOLE", "EMIT registrarOrdenDeCompra "+args);
               }
           });*/
           /*socketManager.on(SocketConstant.EMIT_ORDER, new Emitter.Listener() {
               @Override
               public void call(Object... args) {
                   Log.v("CONSOLE", "ON registrarOrdenDeCompra "+args);
               }
           });*/
           socketManager.emit(SocketConstant.EMIT_ORDER, jsonObject, new Ack() {
               @Override
               public void call(Object... args) {
                   Log.v("CONSOLE", "EMIT registrarOrdenDeCompra "+args);

                   JSONObject data = (JSONObject) args[0];
                   final SocketResponse socketResponse= new SocketMapper().convert(data);
                   Log.v("CONSOLE", "sendOrder "+socketResponse.toString());
                   if(socketResponse.isSuccess()){
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               onSuccess();
                           }
                       });
                   }else{
                       getActivity().runOnUiThread(new Runnable() {
                           @Override
                           public void run() {

                           }
                       });
                   }
               }
           });
       }
    }

    public void clearCart(){
        Cart.clear();
        //ui
        renderOrder();
    }
    private void onSuccess(){
        Toast.makeText(getContext(),"Se envió el pedido correctamente",
                Toast.LENGTH_LONG).show();
        Bundle bundle= new Bundle();
        bundle.putString("TITLE","MyRestaurant");
        bundle.putString("MESSAGE","Orden enviada correctamente");
        SimpleDialog simpleDialog= new SimpleDialog();
        simpleDialog.setArguments(bundle);
        simpleDialog.show(getActivity().getSupportFragmentManager(), "SimpleDialog");
    }
}
