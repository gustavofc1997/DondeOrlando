package com.gforeroc.dondeorlando.data

import android.util.Log
import com.gforeroc.dondeorlando.domain.NewOrder
import com.gforeroc.dondeorlando.domain.myOrders.MyOrder
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class OrderRepository(override var remoteDB: FirebaseFirestore) : IOrderRepository {

    companion object {
        const val MENU_ORDERS = "Ordenes"
    }

    private val changeObservable =
        BehaviorSubject.create { emitter: ObservableEmitter<List<DocumentSnapshot>> ->
            val listeningRegistration =
                remoteDB.collection(MENU_ORDERS)
                    .addSnapshotListener { value, error ->
                        if (value == null || error != null) {
                            return@addSnapshotListener
                        }
                        if (!emitter.isDisposed) {
                            emitter.onNext(value.documents)
                        }
                        value.documentChanges.forEach {
                            Log.d(
                                "Firestore Orders Repository",
                                "Data changed type ${it.type} document ${it.document.id}"
                            )
                        }
                    }
            emitter.setCancellable { listeningRegistration.remove() }
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

    private fun mapDocumentToRemoteTask(document: DocumentSnapshot) =
        document.toObject(MyOrder::class.java)!!

    override fun getChangeObservable(): Observable<List<MyOrder>> =
        changeObservable.hide()
            .observeOn(Schedulers.io())
            .map { list ->
                list.map(::mapDocumentToRemoteTask)
            }

}

interface IOrderRepository {
    var remoteDB: FirebaseFirestore
    fun sendOrder(order: NewOrder): Completable
    fun getChangeObservable(): Observable<List<MyOrder>>

}