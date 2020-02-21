package com.gforeroc.dondeorlando.utils
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.domain.ProductOrder

interface IProductSelected {
    fun onProductSelected(product: Product)
}