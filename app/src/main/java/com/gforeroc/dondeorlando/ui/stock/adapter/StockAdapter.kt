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
import kotlinx.android.synthetic.main.recycler_item.view.*
import kotlinx.android.synthetic.main.recycler_item_stock.view.*
import kotlin.random.Random

class StockAdapter(private val productClickListenerStock: IProductSelected?) :
    RecyclerView.Adapter<StockVH>() {

    private var stockProductList = emptyList<Product>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_stock, parent, false)
        return StockVH(view)
    }

    override fun getItemCount() = stockProductList.size

    override fun onBindViewHolder(holder: StockVH, position: Int) {
        val product = stockProductList[position]
        holder.bind(product)
        with(holder.containerView) {
            this.setOnClickListener {
                productClickListenerStock?.onProductSelected(product)
            }
        }
    }

    fun setItems(newTaskList: List<Product>) {
        val diffResult =
            DiffUtil.calculateDiff(ProductDiffUtilCallback(stockProductList, newTaskList))
        stockProductList.clear()
        stockProductList.addAll(newTaskList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class StockVH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(product: Product) {
        with(containerView) {
            product_title_stock.text = product.Nombre
            tv_stock_quantity.text = product.Cantidad.toString()
            when {
                product.Cantidad <= 20 -> {
                    tv_stock_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count))
                }
                product.Cantidad > 80 -> {
                    tv_stock_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count_green))
                }
                product.Cantidad <= 80 -> {
                    tv_stock_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count_orange))
                }
            }
        }
        setColorView()
    }

    fun setColorView() {
        val colorsArray = containerView.context.resources.getIntArray(R.array.rainbow)
        val randomColor = Random.nextInt(colorsArray.size)
        val color = colorsArray[randomColor]
        containerView.card_view_stock.setCardBackgroundColor(color)
    }
}
