package com.gforeroc.dondeorlando.domain.myOrders

data class MyProduct(
    var cantidad: Long = 0,
    var name: String = "",
    val precio: String = "",
    var id: String = "",
    val adicional: String = ""
)