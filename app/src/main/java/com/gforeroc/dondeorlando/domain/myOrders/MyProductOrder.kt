package com.gforeroc.dondeorlando.domain.myOrders

data class MyProductOrder(
    var product: MyProduct = MyProduct(),
    var quantity: Long = 0,
    var additional: Boolean = false
)