package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import com.gforeroc.dondeorlando.data.models.Product
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers

class MeatViewModel(override var repository: IProductRepository) : BaseViewModel() {

    private val meatsList = MutableLiveData<List<Product>>()
    val meats: LiveData<List<Product>>
        get() = meatsList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { productList ->
                    meatsList.value = productList.filter { it.Amount.toInt() > 0 }
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