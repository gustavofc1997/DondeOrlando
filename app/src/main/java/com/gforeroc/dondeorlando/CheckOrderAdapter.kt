package com.gforeroc.dondeorlando

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.domain.ProductOrder
import com.gforeroc.dondeorlando.utils.ADDITIONAL
import com.gforeroc.dondeorlando.utils.convertToMoney
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_check_order_item.view.*
import java.util.*

class CheckOrderAdapter(private var items: ArrayList<ProductOrder>) :
    RecyclerView.Adapter<CheckVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dialog_check_order_item, parent, false)
        return CheckVH(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CheckVH, position: Int) {
        val task = items[position]
        with(holder.containerView) {
            if (task.isAdditional) {
                product.text = task.product.Name.plus(ADDITIONAL)
                quantity.text = task.quantity.toString()
                price.text = task.product.Additional.toInt().convertToMoney()
                price_sum.text = (task.product.Additional.toInt() * task.quantity).convertToMoney()
            } else {
                product.text = task.product.Name
                quantity.text = task.quantity.toString()
                price.text = task.product.Price.toInt().convertToMoney()
                price_sum.text = (task.product.Price.toInt() * task.quantity).convertToMoney()
            }
        }
    }
}

class CheckVH(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer