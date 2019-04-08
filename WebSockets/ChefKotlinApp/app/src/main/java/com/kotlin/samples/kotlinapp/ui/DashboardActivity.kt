package com.kotlin.samples.kotlinapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.emedinaa.core.helpers.GsonHelper
import com.emedinaa.core.ui.BasicActivity
import com.emedinaa.core.ui.decorators.DividerItemDecoration
import com.emedinaa.core.ui.events.RecyclerClickListener
import com.emedinaa.core.ui.events.RecyclerTouchListener
import com.kotlin.samples.kotlinapp.data.DataInjector
import com.kotlin.samples.kotlinapp.data.callback.DataCallback
import com.kotlin.samples.kotlinapp.data.remote.model.OrderResponse
import com.kotlin.samples.kotlinapp.data.socket.SocketConstant
import com.kotlin.samples.kotlinapp.data.socket.SocketManager
import com.kotlin.samples.kotlinapp.model.Order
import com.kotlin.samples.kotlinapp.model.interactors.OrdersRemoteInteractor
import com.kotlin.samples.kotlinapp.ui.adapter.OrderAdapter
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.json.JSONObject

import java.lang.Exception

class DashboardActivity : BasicActivity() {

    private lateinit var ordersRemoteInteractor: OrdersRemoteInteractor
    private lateinit var socketManager: SocketManager
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private var orders: List<Order>? = null
    private var orderAdapter: OrderAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.kotlin.samples.kotlinapp.R.layout.activity_dashboard)

        socketManager= DataInjector.getInstance().socketManager()
        setUpInteractors()

        ui()
        startSocket()
    }

    private fun setUpInteractors() {
        ordersRemoteInteractor = OrdersRemoteInteractor()
    }

    private fun ui(){
        mLayoutManager =  LinearLayoutManager(this)
        recyclerViewOrders.layoutManager=mLayoutManager

        recyclerViewOrders.addItemDecoration(DividerItemDecoration(
                ContextCompat.getDrawable(this, com.kotlin.samples.kotlinapp.R.drawable.line)))

        //events
        recyclerViewOrders.addOnItemTouchListener(RecyclerTouchListener(
                this, recyclerViewOrders, object : RecyclerClickListener {
            override fun onClick(view: View, position: Int) {
                if (orders != null) {
                    val order = orders?.get(position)
                    selectOrder(order)
                }
            }

            override fun onLongClick(view: View, position: Int) {}
        }
        ))
    }

    private fun selectOrder(order: Order?) {
        val orderResponse = OrderResponse()
        order?.let {
            orderResponse.clientId = it.clientId?.id
            orderResponse.clientName = it.clientId?.fullName()
            orderResponse.plates = order.plates
        }


        val bundle = Bundle()
        bundle.putSerializable("ORDER", orderResponse)
        nextActivity(Intent(this, OrderDetailsActivity::class.java), bundle, false)
    }

    private fun clearAdapter() {
        orderAdapter = OrderAdapter(emptyList())
        recyclerViewOrders.adapter=orderAdapter
    }

    private fun showLoading() {
        rlayLoading.visibility=View.VISIBLE
    }

    private fun hideLoading() {
        this.rlayLoading.visibility=View.GONE
    }

    private fun startSocket() {
        if (socketManager.isConnected) {
            socketManager.on(SocketConstant.ON_ORDERS) { args ->
                val jsonObject = args[0] as JSONObject
                val orderResponse = GsonHelper().jsonToObject(jsonObject.toString(), OrderResponse::class.java)
                Log.v("CONSOLE", "orderResponse " + orderResponse!!.toString())
                if (orderResponse != null) {
                    runOnUiThread { onSuccess(orderResponse) }
                } else {
                    runOnUiThread { onFailure() }
                }
            }
        }
    }

    private fun onSuccess(orderResponse: OrderResponse) {
        Toast.makeText(this, "Nuevo Pedido :\n$orderResponse", Toast.LENGTH_LONG).show()
        val id = orderResponse.id
        val title = "Nuevo pedido $id"
        val content = "De " + orderResponse.clientName
        //addNotification(orderResponse, id, title, content)
    }

    private fun empty() {}
    private fun onFailure() {
        Toast.makeText(this, "Ocurrió un error", Toast.LENGTH_LONG).show()
    }

    private fun renderOrders(mOrders: List<Order>) {
        this.orders = mOrders
        orderAdapter = OrderAdapter(orders)
        recyclerViewOrders.adapter = orderAdapter
    }

    private fun retrieveOrders() {
        clearAdapter()
        showLoading()
        ordersRemoteInteractor.orders(object : DataCallback {
            override fun onSuccess(obj: Any?) {
                hideLoading()
                if (obj != null && obj is List<*>) {
                    val mOrders = obj as List<Order>?
                    mOrders?.let {
                        if(it.isEmpty()){
                            empty()
                        }else{
                            renderOrders(it)
                        }
                    }
                } else {
                }
            }

            override fun onFailure(exception: Exception?) {
                hideLoading()
                showMessage(exception?.message?:"Ocurrió un error")
            }
        })
    }

    override fun onPause() {
        super.onPause()
        //overridePendingTransition(0,0);
    }

    override fun onResume() {
        super.onResume()
        retrieveOrders()
    }

    override fun onDestroy() {
        super.onDestroy()
        socketManager?.clearSession()
    }
}
