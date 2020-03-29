package com.gforeroc.dondeorlando.domain.myOrders

data class MyProductOrder(
    var product: MyProduct = MyProduct(),
    val quantity: Long = 0,
    var additional: Boolean = false
)