package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.BeveragesRepository
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers

class BeveragesViewModel : BaseViewModel() {

    private val beveragesList = MutableLiveData<List<Product>>()
    val beverages: LiveData<List<Product>>
        get() = beveragesList


    init {
        repository = BeveragesRepository()
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    beveragesList.value = it
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