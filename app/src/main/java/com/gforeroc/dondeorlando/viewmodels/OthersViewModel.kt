package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.IProductRepository
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers

class OthersViewModel(override var repository: IProductRepository) : BaseViewModel() {

    private val otherList = MutableLiveData<List<Product>>()
    val others: LiveData<List<Product>>
        get() = otherList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    otherList.value = it
                },
                {
                    it.printStackTrace()
                }
            )
            .addTo(disposable)
    }

    fun updateQuantity(setUpdateQuantity: Long, id: String){
        repository.updateStock(setUpdateQuantity, id)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}