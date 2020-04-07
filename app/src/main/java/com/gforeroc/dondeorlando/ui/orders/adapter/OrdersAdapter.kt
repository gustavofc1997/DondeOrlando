package com.gforeroc.dondeorlando.ui.orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.Product
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycler_item_orders.view.*

class OrdersAdapter : RecyclerView.Adapter<OrdersVH>() {

    private val ordersList = emptyList<Product>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item_orders, parent, false)
        return OrdersVH(view)
    }

    override fun getItemCount() = ordersList.size

    override fun onBindViewHolder(holder: OrdersVH, position: Int) {
        val task = ordersList[position]
        with(holder.containerView) {
            text_name_product.text = task.Nombre
            text_count.text = task.Cantidad.toString()
        }
    }

    fun setItems(newTaskList: List<Product>) {
        ordersList.clear()
        ordersList.addAll(newTaskList)
    }
}

class OrdersVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
}
