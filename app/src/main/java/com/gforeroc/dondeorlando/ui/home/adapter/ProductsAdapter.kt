package com.gforeroc.dondeorlando.ui.home.adapter

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
import kotlinx.android.synthetic.main.recycler_item.view.*
import kotlin.random.Random


class ProductsAdapter(private val productClickListener: IProductSelected?) :
    RecyclerView.Adapter<TaskViewHolder>() {

    private var taskList = emptyList<Product>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.setColorView()
        with(holder.containerView) {
            this.setOnClickListener {
                productClickListener?.onProductSelected(task)
            }
            productTitle.text = task.Nombre
            when {
                task.Cantidad <= 20 -> {
                    text_count.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count))
                }
                task.Cantidad > 80 -> {
                    text_count.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count_green))
                }
                task.Cantidad <= 80 -> {
                    text_count.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count_orange))
                }
            }
            text_count.text = task.Cantidad.toString()
        }
    }

    fun setItems(newTaskList: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffUtilCallback(taskList, newTaskList))
        taskList.clear()
        taskList.addAll(newTaskList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class TaskViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun setColorView() {
        val colorsArray = containerView.context.resources.getIntArray(R.array.rainbow)
        val randomColor = Random.nextInt(colorsArray.size)
        val color = colorsArray[randomColor]
        containerView.cardView.setCardBackgroundColor(color)
    }
}