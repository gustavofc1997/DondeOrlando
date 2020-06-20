package com.gforeroc.dondeorlando.ui.base

interface IPasswordAction {
    fun onPasswordSuccessful()
}

interface IDeleteOrders : IPasswordAction

interface IShowOrders : IPasswordAction