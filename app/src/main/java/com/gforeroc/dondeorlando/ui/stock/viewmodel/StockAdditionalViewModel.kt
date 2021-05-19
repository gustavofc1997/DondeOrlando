package com.gforeroc.dondeorlando.ui.stock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import com.gforeroc.dondeorlando.utils.addTo
import com.gforeroc.dondeorlando.viewmodels.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class StockAdditionalViewModel(override var repository: IProductRepository) : BaseViewModel() {

    private val stockAdditionalList = MutableLiveData<List<Product>>()
    val stockAdditional: LiveData<List<Product>>
        get() = stockAdditionalList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stockAdditionalList.value = it
                },
                {
                    it.printStackTrace()
                }
            ).addTo(disposable)
    }

    fun updateQuantity(setUpdateQuantity: Long, id: String) {
        repository.updateStock(setUpdateQuantity, id)
    }

    fun updateQuantityCheck(id: String){
        repository.updateStockCheck(id)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}