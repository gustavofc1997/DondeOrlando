package com.gforeroc.dondeorlando.utils
import com.gforeroc.dondeorlando.data.models.Product

interface IProductSelected {
    fun onProductSelected(product: Product)
}