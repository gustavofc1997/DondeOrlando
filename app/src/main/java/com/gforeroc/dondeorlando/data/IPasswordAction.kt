package com.gforeroc.dondeorlando.data

interface IPasswordAction {
    fun onPasswordSuccessful()
}

interface IDeleteOrders : IPasswordAction

interface IShowOrders : IPasswordAction