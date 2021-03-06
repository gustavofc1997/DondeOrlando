package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers

class SidesViewModel(override var repository: IProductRepository) : BaseViewModel() {

    private val sidesList = MutableLiveData<List<Product>>()
    val sides: LiveData<List<Product>>
        get() = sidesList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { product ->
                    sidesList.value = product.filter { it.Amount.toInt() > 0 }
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