package com.gforeroc.dondeorlando.data

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OthersRepository (override var remoteDB: FirebaseFirestore) : IProductRepository {

    companion object {
        private const val MENU_COLLECTION = "menu"
        private const val OTHERS_DOCUMENT = "Otros"
        private const val ITEMS = "items"
    }

    private val changeObservable =
        BehaviorSubject.create { emitter: ObservableEmitter<List<DocumentSnapshot>> ->
            val listeningRegistration =
                remoteDB.collection(MENU_COLLECTION).document(OTHERS_DOCUMENT).collection(ITEMS)
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

    override suspend fun getAllProducts() {
        GlobalScope.launch(Dispatchers.IO) {
            remoteDB.collection(MENU_COLLECTION).document(OTHERS_DOCUMENT).collection(ITEMS)
                .get().await().map(::mapDocumentToRemoteTask)
                .toList()
        }
    }

    private fun mapDocumentToRemoteTask(document: DocumentSnapshot) =
        document.toObject(Product::class.java)!!.apply { id = document.id }

    override fun getChangeObservable(): Observable<List<Product>> =
        changeObservable.hide()
            .observeOn(Schedulers.io())
            .map { list ->
                list.map(::mapDocumentToRemoteTask)
            }

    override fun updateStock(quantity: Long, id:String): Completable {
        remoteDB.collection(MENU_COLLECTION).document(OTHERS_DOCUMENT).collection(ITEMS).document(id).update(
            mapOf("Amount" to quantity))
        return Completable.complete()
    }
}