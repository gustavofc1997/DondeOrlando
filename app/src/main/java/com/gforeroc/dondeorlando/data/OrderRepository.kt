package com.gforeroc.dondeorlando.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import io.reactivex.Completable

class OrderRepository : IOrderRepository {

    companion object {
        const val MENU_ORDERS = "Ordenes"
    }

    private val remoteDB = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build()
    }

    override fun getOrderlist() {

    }

    override fun sendOrder(): Completable {
        return Completable.create { emitter ->
            val taskData = HashMap<String, Any>()

            taskData.put("Fecha", "22-02-2020 8:49")
            taskData.put("Total", "12121")
            taskData.put("items", "sa")
            remoteDB.collection(MENU_ORDERS)
                .add(taskData)
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

    fun getOrderlist()
    fun sendOrder(): Completable

}