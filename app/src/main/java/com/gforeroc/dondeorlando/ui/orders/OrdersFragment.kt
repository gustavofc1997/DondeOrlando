package com.gforeroc.dondeorlando.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.domain.myOrders.MyOrder
import com.gforeroc.dondeorlando.ui.orders.adapter.OrdersAdapter
import com.gforeroc.dondeorlando.viewmodels.OrdersViewModel
import kotlinx.android.synthetic.main.fragment_orders.*
import org.koin.android.viewmodel.ext.android.viewModel


class OrdersFragment : Fragment() {

    private val ordersAdapter = OrdersAdapter()
    private val ordersViewModel: OrdersViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_orders.setHasFixedSize(true)
        recycler_orders.adapter = ordersAdapter
        recycler_orders.layoutManager = LinearLayoutManager(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       ordersViewModel.allOrders.observe(this, Observer {
           ordersAdapter.setItems(mapArray(it))
       })
    }

    private fun mapArray(args: List<MyOrder>): List<Product> {
        val myMap = HashMap<String, Long>()
        args.forEach { orderData ->
            orderData.items.forEach { product ->
                val myKey = product.product.nombre
                if (myMap.containsKey(myKey)) {
                    var quantity = myMap[myKey] ?: 0L
                    quantity += product.product.cantidad
                    myMap[myKey] = quantity
                } else {
                    myMap[myKey] = product.product.cantidad
                }
            }
        }
        return myMap.map {
            val product = Product()
            product.Nombre = it.key
            product.Cantidad = it.value
            product
        }
    }
}