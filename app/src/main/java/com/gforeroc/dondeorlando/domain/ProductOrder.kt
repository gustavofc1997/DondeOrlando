package com.gforeroc.dondeorlando.domain

import com.gforeroc.dondeorlando.data.models.Product

data class ProductOrder(val product: Product, val quantity: Int, var isAdditional: Boolean = false)