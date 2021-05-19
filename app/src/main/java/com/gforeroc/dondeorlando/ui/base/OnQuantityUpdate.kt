package com.gforeroc.dondeorlando.ui.base

interface OnQuantityUpdate {
    fun updateQuantity(setUpdateQuantity : Long, id:String)
    fun updateQuantityCheck(id: String)
}