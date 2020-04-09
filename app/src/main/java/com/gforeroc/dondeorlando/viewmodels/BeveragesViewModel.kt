package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.gforeroc.dondeorlando.data.IProductRepository
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import java.text.FieldPosition

class BeveragesViewModel(override var repository: IProductRepository) : BaseViewModel() {

    private val beveragesList = MutableLiveData<List<Product>>()
    val beverages: LiveData<List<Product>>
        get() = beveragesList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                   beveragesList.value = it.filter { it.Cantidad.toInt() > 0 }
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