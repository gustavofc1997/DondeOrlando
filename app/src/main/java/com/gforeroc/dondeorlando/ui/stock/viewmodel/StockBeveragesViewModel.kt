package com.gforeroc.dondeorlando.ui.stock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import com.gforeroc.dondeorlando.utils.addTo
import com.gforeroc.dondeorlando.viewmodels.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class StockBeveragesViewModel(override var repository: IProductRepository) : BaseViewModel() {

    private val stockBeveragesList = MutableLiveData<List<Product>>()
    val stockBeverages: LiveData<List<Product>>
        get() = stockBeveragesList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stockBeveragesList.value = it
                },
                {
                    it.printStackTrace()
                }
            )
            .addTo(disposable)
    }

    fun updateQuantity(setUpdateQuantity: Long, id: String) {
        repository.updateStock(setUpdateQuantity, id)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}