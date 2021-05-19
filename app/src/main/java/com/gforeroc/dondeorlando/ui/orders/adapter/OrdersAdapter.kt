package com.gforeroc.dondeorlando.ui.orders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.utils.ProductDiffUtilCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.header_orders_layout.view.*
import kotlinx.android.synthetic.main.recycler_item_orders.view.*

class OrdersAdapter : RecyclerView.Adapter<OrdersVH>() {

    private val ordersList = emptyList<Product>().toMutableList()
    private val TYPE_HEAD: Int = 0
    private val TYPE_LIST: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersVH {
        val view: View
        if (viewType == TYPE_LIST) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_item_orders, parent, false)
            return OrdersVH(view, viewType)
        } else if (viewType == TYPE_HEAD) {
            view = LayoutInflater.from(parent.context)
                .inflate(R.layout.header_orders_layout, parent, false)
            return OrdersVH(view, viewType)
        }
        return OrdersVH(parent, viewType)
    }

    override fun getItemCount() = ordersList.size

    override fun onBindViewHolder(holder: OrdersVH, position: Int) {
        val task = ordersList[position]
        holder.bind(task)
    }

    fun setItems(newTaskList: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffUtilCallback(ordersList, newTaskList))
        ordersList.clear()
        ordersList.addAll(newTaskList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_LIST
    }
}

class OrdersVH(override val containerView: View, private var viewType: Int) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(product: Product) {
        if (viewType == 1) {
            with(containerView) {
                val productName = product.Name
                val qty = product.Quantity.toString()
                text_name_product.text = "$productName: $qty"
            }
        } else if (viewType == 0) {
            with(containerView) {
                val productNameCourtesy = product.Name
                val qtyCourtesy = product.Quantity.toString()
                text_name_product_head.text = "$productNameCourtesy: $qtyCourtesy"
            }
        }
    }
}
