package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gforeroc.dondeorlando.data.Product
import com.gforeroc.dondeorlando.data.SideRepository
import com.gforeroc.dondeorlando.utils.addTo
import io.reactivex.android.schedulers.AndroidSchedulers

class SidesViewModel : BaseViewModel() {

    private val sidesList = MutableLiveData<List<Product>>()
    val sides: LiveData<List<Product>>
        get() = sidesList


    init {
        repository = SideRepository()
        repository.getChangeObservable()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    sidesList.value = it
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