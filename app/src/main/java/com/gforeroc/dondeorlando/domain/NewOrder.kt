package com.gforeroc.dondeorlando.domain

import androidx.databinding.BaseObservable
import com.gforeroc.dondeorlando.BR
import java.text.SimpleDateFormat
import java.util.*

class NewOrder(
    var items: ArrayList<ProductOrder> = arrayListOf(),
    var total: Long = 0,
    var date: String? = null
) : BaseObservable() {

    fun addProduct(productOrder: ProductOrder) {
        items.add(productOrder)
    }

    fun getCarVisibility(visible: Int){
        notifyPropertyChanged(BR.newOrder)
    }

    fun clearData() {
        items.clear()
        total = 0
        date?.isEmpty()
    }

    fun setDate() {
        val s = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault())
        val format: String = s.format(Date())
        date = format
    }

    fun calculateTotals() {
        total = items.map {
            if (it.isAdditional) {
                it.quantity * (it.product.Adicional.toInt())
            } else {
                it.quantity * (it.product.Precio.toInt())
            }
        }.sum().toLong()
    }
}
