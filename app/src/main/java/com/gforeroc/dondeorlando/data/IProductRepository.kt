package com.gforeroc.dondeorlando.data

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProductRepository {
    var remoteDB: FirebaseFirestore
    fun getAllProducts(): Single<List<Product>>
    fun getChangeObservable(): Observable<List<Product>>
}