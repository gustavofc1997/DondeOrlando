package com.gforeroc.dondeorlando.data.repositories.base

import com.gforeroc.dondeorlando.data.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProductRepository {
    var remoteDB: FirebaseFirestore
    fun getAllProducts(): Single<List<Product>>
    fun getChangeObservable(): Observable<List<Product>>
    fun updateStock(quantity: Long, id:String): Completable
    fun updateStockCheck(id: String): Completable
}