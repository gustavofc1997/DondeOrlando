package com.gforeroc.dondeorlando.utils

import androidx.recyclerview.widget.DiffUtil
import com.gforeroc.dondeorlando.data.Product

class ProductDiffUtilCallback(
    private val oldList: MutableList<Product>,
    private val newList: List<Product>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].Name == newList[newItemPosition].Name
    }
}