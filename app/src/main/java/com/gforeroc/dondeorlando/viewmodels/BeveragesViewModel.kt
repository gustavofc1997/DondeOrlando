package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers

class BeveragesViewModel(override var repository: IProductRepository) : BaseViewModel() {

    private val beveragesList = MutableLiveData<List<Product>>()
    val beverages: LiveData<List<Product>>
        get() = beveragesList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { productList ->
                    beveragesList.value = productList.filter { it.Amount.toInt() > 0 }
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