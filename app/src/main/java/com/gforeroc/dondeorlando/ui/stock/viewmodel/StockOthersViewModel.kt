package com.gforeroc.dondeorlando.ui.stock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import com.gforeroc.dondeorlando.utils.addTo
import com.gforeroc.dondeorlando.viewmodels.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class StockOthersViewModel(override var repository: IProductRepository) : BaseViewModel() {

    private val stockOthersList = MutableLiveData<List<Product>>()
    val stockOther: LiveData<List<Product>>
        get() = stockOthersList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    stockOthersList.value = it
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