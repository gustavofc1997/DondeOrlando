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
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
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
        holder.itemView.card_view_stock.setOnClickListener {
            productClickListenerStock?.onProductSelected(product)
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
            product_title_stock.text = product.Name
            tv_stock_quantity.text = product.Amount.toString()
            product_id_stock.text = product.id
            Picasso.get().load(product.Image).fit().centerCrop().into(iv_stock)
            when {
                product.Amount <= 20 -> {
                    tv_stock_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count))
                }
                product.Amount > 80 -> {
                    tv_stock_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count_green))
                }
                product.Amount <= 80 -> {
                    tv_stock_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count_orange))
                }
            }
        }
    }
}
