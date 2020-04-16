package com.gforeroc.dondeorlando

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.domain.ProductOrder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dialog_check_order_item.view.*
import java.util.*

class CheckOrderAdapter(private var taskList: ArrayList<ProductOrder>) :
    RecyclerView.Adapter<CheckVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dialog_check_order_item, parent, false)
        return CheckVH(view)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: CheckVH, position: Int) {
        val task = taskList[position]
        with(holder.containerView) {
            if (task.isAdditional) {
                val aditional = " --Adicional"
                product.text = task.product.Nombre.plus(aditional)
                quantity.text = task.quantity.toString()
                price.text = (task.product.Adicional.toInt() * task.quantity).toString()
            } else {
                product.text = task.product.Nombre
                quantity.text = task.quantity.toString()
                price.text = task.product.Precio
            }
        }
    }
}

class CheckVH(override val containerView: View) :
    RecyclerView.ViewHolder(containerView),
    LayoutContainer {
}
