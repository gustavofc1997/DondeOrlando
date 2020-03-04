package com.gforeroc.dondeorlando.data

import com.gforeroc.dondeorlando.domain.NewOrder
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable

class OrderRepository(override var remoteDB: FirebaseFirestore) : IOrderRepository {

    companion object {
        const val MENU_ORDERS = "Ordenes"
    }


    override fun getOrderlist() {
    }

    override fun sendOrder(order: NewOrder): Completable {
        return Completable.create { emitter ->
            remoteDB.collection(MENU_ORDERS).document().set(order)
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onComplete()
                    }
                }
                .addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
    }

}

interface IOrderRepository {
    var remoteDB: FirebaseFirestore

    fun getOrderlist()
    fun sendOrder(order: NewOrder): Completable

}