package com.gforeroc.dondeorlando.data.repositories

import android.util.Log
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class BeveragesRepository(override var remoteDB: FirebaseFirestore) :
    IProductRepository {

    companion object {
        private const val MENU_COLLECTION = "menu"
        private const val MEATS_DOCUMENT = "Bebidas"
        private const val ITEMS = "items"
    }

    private val changeObservable =
        BehaviorSubject.create<List<DocumentSnapshot>> { emitter: ObservableEmitter<List<DocumentSnapshot>> ->
            val listeningRegistration =
                remoteDB.collection(MENU_COLLECTION).document(
                    MEATS_DOCUMENT
                ).collection(ITEMS)
                    .addSnapshotListener { value, error ->
                        if (value == null || error != null) {
                            return@addSnapshotListener
                        }
                        if (!emitter.isDisposed) {
                            emitter.onNext(value.documents)
                        }
                        value.documentChanges.forEach {
                            Log.d(
                                "FirestoreTaskRepository",
                                "Data changed type ${it.type} document ${it.document.id}"
                            )
                        }
                    }

            emitter.setCancellable { listeningRegistration.remove() }
        }

    override fun getAllProducts(): Single<List<Product>> {
        return Single.create<List<DocumentSnapshot>> { emitter ->
            remoteDB.collection(MENU_COLLECTION).document(
                MEATS_DOCUMENT
            ).collection(ITEMS).get()
                .addOnSuccessListener {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(it.documents)
                    }
                }
                .addOnFailureListener {
                    if (!emitter.isDisposed) {
                        emitter.onError(it)
                    }
                }
        }
            .observeOn(Schedulers.io())
            .flatMapObservable { Observable.fromIterable(it) }
            .map(::mapDocumentToRemoteTask)
            .toList()
    }

    private fun mapDocumentToRemoteTask(document: DocumentSnapshot) =
        document.toObject(Product::class.java)!!.apply {
            id = document.id
            path = document.reference.path
        }


    override fun getChangeObservable(): Observable<List<Product>> =
        changeObservable.hide()
            .observeOn(Schedulers.io())
            .map { list ->
                list.map(::mapDocumentToRemoteTask)
            }

    override fun updateStock(quantity: Long, id:String): Completable {
        remoteDB.collection(MENU_COLLECTION).document(
            MEATS_DOCUMENT
        ).collection(ITEMS).document(id).update(
            mapOf("Amount" to quantity))
        return Completable.complete()
    }
}