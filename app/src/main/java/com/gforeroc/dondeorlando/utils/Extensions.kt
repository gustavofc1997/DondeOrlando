package com.gforeroc.dondeorlando.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.DecimalFormat
import java.util.*

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun Int.convertToMoney(): String {
    val formatter = DecimalFormat("$#,###")
    formatter.currency = Currency.getInstance(Locale.US)
    return formatter.format(this).toString()
}