package com.gforeroc.dondeorlando.domain

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.gforeroc.dondeorlando.BR
import java.text.SimpleDateFormat
import java.util.*

class NewOrder(
    var items: ArrayList<ProductOrder> = arrayListOf(),
    var total: Long = 0,
    var date: String? = null
) : BaseObservable() {


    @Bindable
    var visibility = View.GONE
    fun addProduct(productOrder: ProductOrder) {
        items.add(productOrder)
        changeVisibility()

    }

    fun setDate() {
        val s = SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.getDefault())
        val format: String = s.format(Date())
        date = format
    }

    private fun changeVisibility() {
        visibility = if (items.isEmpty())
            View.GONE
        else View.VISIBLE
        notifyPropertyChanged(BR.visibility)
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
