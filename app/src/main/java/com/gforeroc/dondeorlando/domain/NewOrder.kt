package com.gforeroc.dondeorlando.domain

import java.text.SimpleDateFormat
import java.util.*

class NewOrder(
    var items: ArrayList<ProductOrder> = arrayListOf(),
    var total: Long = 0,
    var date: String? = null
) {
    fun addProduct(productOrder: ProductOrder) {
        items.add(productOrder)
    }

    fun setDate() {
        val s = SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault())
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
