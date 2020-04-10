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
    RecyclerView.Adapter<ProductsViewHolder>() {

    private var productList = emptyList<Product>().toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ProductsViewHolder(view)
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
        with(holder.containerView) {
            this.setOnClickListener {
                productClickListener?.onProductSelected(product)
            }
        }
    }

    fun setItems(newTaskList: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffUtilCallback(productList, newTaskList))
        productList.clear()
        productList.addAll(newTaskList)
        diffResult.dispatchUpdatesTo(this)
    }
}

class ProductsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bind(product: Product) {
        with(containerView) {
            productTitle.text = product.Nombre
            when {
                product.Cantidad <= 20 -> {
                    tv_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count))
                }
                product.Cantidad > 80 -> {
                    tv_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count_green))
                }
                product.Cantidad <= 80 -> {
                    tv_quantity.setBackgroundDrawable(resources.getDrawable(R.drawable.item_count_orange))
                }
            }
            tv_quantity.text = product.Cantidad.toString()
        }
        setColorView()
    }

    fun setColorView() {
        val colorsArray = containerView.context.resources.getIntArray(R.array.rainbow)
        val randomColor = Random.nextInt(colorsArray.size)
        val color = colorsArray[randomColor]
        containerView.cardView.setCardBackgroundColor(color)
    }
}