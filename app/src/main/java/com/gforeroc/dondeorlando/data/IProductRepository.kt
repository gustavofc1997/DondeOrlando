package com.gforeroc.dondeorlando.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProductRepository {
    var remoteDB: FirebaseFirestore
    suspend fun getAllProducts()
    fun getChangeObservable(): Observable<List<Product>>
    suspend fun updateStock(quantity: Long, id: String): Completable
}