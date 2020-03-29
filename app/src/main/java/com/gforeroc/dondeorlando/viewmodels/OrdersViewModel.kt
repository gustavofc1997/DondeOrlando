package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gforeroc.dondeorlando.data.IOrderRepository
import com.gforeroc.dondeorlando.domain.NewOrder
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class OrdersViewModel(var repository: IOrderRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val ordersList = MutableLiveData<List<NewOrder>>()

    val allOrders: LiveData<List<NewOrder>>
        get() = ordersList

    fun sendOrder(order: NewOrder) {
        repository.sendOrder(order).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                {
                    it.printStackTrace()
                }
            )
            .addTo(disposable)
    }

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    ordersList.value = it
                },
                {
                    it.printStackTrace()
                }
            )
            .addTo(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}