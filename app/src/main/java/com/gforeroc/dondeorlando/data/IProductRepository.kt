package com.gforeroc.dondeorlando.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProductRepository {

    fun getAllProducts(): Single<List<Product>>
    fun addProduct(product: Product): Completable
    fun deleteProduct(productId: String): Completable
    fun getChangeObservable(): Observable<List<Product>>
}