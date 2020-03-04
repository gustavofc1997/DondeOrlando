package com.gforeroc.dondeorlando.viewmodels

import androidx.lifecycle.ViewModel
import com.gforeroc.dondeorlando.data.IProductRepository
import com.gforeroc.dondeorlando.data.MeatsRepository
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel :ViewModel(){

    open lateinit var repository: IProductRepository
    protected val disposable = CompositeDisposable()

}