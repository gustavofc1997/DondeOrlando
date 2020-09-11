package com.gforeroc.dondeorlando.domain.myOrders

import java.util.*

data  class MyOrder(
    var items: ArrayList<MyProductOrder> = arrayListOf(),
    var total: Long = 0,
    var date: String? = null,
    var Courtesy: Boolean = false
)
