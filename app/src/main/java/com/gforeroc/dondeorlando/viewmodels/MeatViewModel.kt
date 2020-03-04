package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gforeroc.dondeorlando.data.IProductRepository
import com.gforeroc.dondeorlando.data.MeatsRepository
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MeatViewModel (override var repository: IProductRepository): BaseViewModel() {

    private val meatsList = MutableLiveData<List<Product>>()
    val meats: LiveData<List<Product>>
        get() = meatsList

    init {
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    meatsList.value = it
                },
                {
                    it.printStackTrace()
                }
            )
            .addTo(disposable)
    }

    fun deleteTask(taskId: String) {
        /*repository.deleteTask(taskId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {},
                {
                    it.printStackTrace()
                })
            .addTo(disposable)*/
    }

    fun addTask(taskTitle: String) {
        /* repository.addTask(Task("${System.currentTimeMillis()}", taskTitle))
             .subscribeOn(Schedulers.io())
             .subscribe(
                 {},
                 {
                     it.printStackTrace()
                 })
             .addTo(disposable)*/
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}