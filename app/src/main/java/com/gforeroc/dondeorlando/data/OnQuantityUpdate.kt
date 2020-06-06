package com.gforeroc.dondeorlando.data

interface OnQuantityUpdate {
    suspend fun updateQuantity(setUpdateQuantity : Long, id:String)
}