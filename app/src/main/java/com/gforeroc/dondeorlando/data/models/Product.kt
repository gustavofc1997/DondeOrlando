package com.gforeroc.dondeorlando.data.models

data class Product(
    var Amount: Long = 0,
    var Name: String = "",
    val Price: String = "",
    var id: String = "",
    val Additional: String = "",
    var Quantity: Long = 0,
    var Image: String = "http://www.cpsglobalgroup.com/wp-content/uploads/2017/11/placeholder.jpg",
    var path: String = ""
)