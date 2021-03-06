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
        val resultAdditional = items.find {
            it.product.id == productOrder.product.id &&
            it.isAdditional == productOrder.isAdditional
        }
        if (resultAdditional != null) {
            resultAdditional.quantity = resultAdditional.quantity.plus(productOrder.quantity)
        }else{
            items.add(productOrder)
        }
        changeVisibility()
    }

    fun clearData() {
        items.clear()
        total = 0
        date = null
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
                it.quantity * (it.product.Additional.toInt())
            } else {
                it.quantity * (it.product.Price.toInt())
            }
        }.sum().toLong()
    }
}