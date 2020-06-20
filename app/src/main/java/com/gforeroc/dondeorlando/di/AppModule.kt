package com.gforeroc.dondeorlando.di

import com.gforeroc.dondeorlando.data.repositories.*
import com.gforeroc.dondeorlando.data.repositories.base.IProductRepository
import com.gforeroc.dondeorlando.ui.stock.viewmodel.StockAdditionalViewModel
import com.gforeroc.dondeorlando.ui.stock.viewmodel.StockBeveragesViewModel
import com.gforeroc.dondeorlando.ui.stock.viewmodel.StockMeatViewModel
import com.gforeroc.dondeorlando.ui.stock.viewmodel.StockOthersViewModel
import com.gforeroc.dondeorlando.viewmodels.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    fun firebaseDatabase() = FirebaseFirestore.getInstance().apply {
        firestoreSettings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
    }
    single<IProductRepository>(named("sideRepository")) {
        SideRepository(
            firebaseDatabase()
        )
    }
    single<IProductRepository>(named("beveragesRepository")) {
        BeveragesRepository(
            firebaseDatabase()
        )
    }
    single<IProductRepository>(named("othersRepository")) {
        OthersRepository(
            firebaseDatabase()
        )
    }
    single<IProductRepository>(named("meatsRepository")) {
        MeatsRepository(
            firebaseDatabase()
        )
    }
    single<IOrderRepository>() {
        OrderRepository(
            firebaseDatabase()
        )
    }

    viewModel { BeveragesViewModel(get(named("beveragesRepository"))) }
    viewModel { OrdersViewModel(get()) }
    viewModel { SidesViewModel(get(named("sideRepository"))) }
    viewModel { MeatViewModel(get(named("meatsRepository"))) }
    viewModel { OthersViewModel(get(named("othersRepository"))) }
    viewModel { StockMeatViewModel(get(named("meatsRepository"))) }
    viewModel { StockBeveragesViewModel(get(named("beveragesRepository"))) }
    viewModel { StockAdditionalViewModel(get(named("sideRepository"))) }
    viewModel { StockOthersViewModel(get(named("othersRepository"))) }

}