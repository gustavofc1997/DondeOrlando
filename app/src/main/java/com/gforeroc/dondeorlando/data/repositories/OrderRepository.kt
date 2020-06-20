package com.gforeroc.dondeorlando.data.repositories

import android.util.Log
import com.gforeroc.dondeorlando.domain.NewOrder
import com.gforeroc.dondeorlando.domain.myOrders.MyOrder
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class OrderRepository(override var remoteDB: FirebaseFirestore) :
    IOrderRepository {

    companion object {
        const val MENU_ORDERS = "orders"
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
        updateStock(order)
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

    fun updateStock(order: NewOrder) {
        remoteDB.runBatch { batch ->
            order.items.forEach {
                val sfRef = remoteDB.document(it.product.path)
                batch.update(sfRef, "Amount", (it.product.Amount.toInt() - it.quantity).toLong())
            }
        }
    }

    override fun deleteOrders(): Completable {
        return Completable.create { emitter ->
            remoteDB.collection(MENU_ORDERS).whereEqualTo("visibility", 0)
                .get()
                .addOnSuccessListener {
                    val batch: WriteBatch = remoteDB.batch()
                    val list: List<DocumentSnapshot> = it.documents
                    for (document in list) {
                        batch.delete(document.reference)
                    }
                    batch.commit()
                        .addOnSuccessListener {
                            if (!emitter.isDisposed) {
                                emitter.onComplete()
                            }
                        }
                        .addOnFailureListener { batchIt ->
                            if (!emitter.isDisposed) {
                                emitter.onError(batchIt)
                            }
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
    fun deleteOrders(): Completable
}