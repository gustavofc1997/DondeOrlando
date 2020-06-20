package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.ViewModel
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel :ViewModel(){

    open lateinit var repository: IProductRepository
    protected val disposable = CompositeDisposable()

}