package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.ViewModel
import com.gforeroc.dondeorlando.data.IOrderRepository
import com.gforeroc.dondeorlando.data.IProductRepository
import com.gforeroc.dondeorlando.data.OrderRepository
import com.gforeroc.dondeorlando.domain.NewOrder
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class OrdersViewModel(var repository: IOrderRepository) : ViewModel() {

    private val disposable = CompositeDisposable()

    fun sendOrder(order: NewOrder) {
        repository.sendOrder(NewOrder()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
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