package com.gforeroc.dondeorlando.domain

class NewOrder(
    var items: ArrayList<ProductOrder> = arrayListOf(),
    total: Long = 0,
    fecha: String? = null
) {
    fun addProduct(productOrder: ProductOrder) {
        items.add(productOrder)
    }
}