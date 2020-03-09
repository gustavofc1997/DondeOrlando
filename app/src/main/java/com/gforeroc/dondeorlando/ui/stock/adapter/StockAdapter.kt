package com.gforeroc.dondeorlando.ui.stock.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gforeroc.dondeorlando.R
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.utils.IProductSelected
import com.gforeroc.dondeorlando.utils.ProductDiffUtilCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recycler_item_stock.view.*
import kotlin.random.Random

class StockAdapter(private val productClickListenerStock: IProductSelected?) :
    RecyclerView.Adapter<StockVH>() {

    private var taskList = emptyList<Product>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_stock, parent, false)
        return StockVH(view)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: StockVH, position: Int) {
        val task = taskList[position]
        holder.setColorView()
        with(holder.containerView) {
            this.setOnClickListener {
                productClickListenerStock?.onProductSelected(task)
            }
            product_title_stock.text = task.Nombre
            text_count_stock.text = task.Cantidad.toString()
        }
    }

    fun setItems(newTaskList: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffUtilCallback(taskList, newTaskList))
        taskList.clear()
        taskList.addAll(newTaskList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class StockVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun setColorView() {
        val colorsArray = containerView.context.resources.getIntArray(R.array.rainbow)
        val randomColor = Random.nextInt(colorsArray.size)
        val color = colorsArray[randomColor]
        containerView.card_view_stock.setCardBackgroundColor(color)
    }
}
