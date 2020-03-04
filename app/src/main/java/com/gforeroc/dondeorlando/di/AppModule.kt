package com.gforeroc.dondeorlando.di

import com.gforeroc.dondeorlando.data.*
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

    single<IProductRepository>(named("beveragesRepository")) { BeveragesRepository(firebaseDatabase()) }
    single<IProductRepository>(named("othersRepository")) { OthersRepository(firebaseDatabase()) }
    single<IProductRepository>(named("meatsRepository")) { MeatsRepository(firebaseDatabase()) }
    single<IOrderRepository>() { OrderRepository(firebaseDatabase()) }
    single<IProductRepository>(named("sideRepository")) { SideRepository(firebaseDatabase()) }

    viewModel { BeveragesViewModel(get(named("beveragesRepository"))) }
    viewModel { OrdersViewModel(get()) }
    viewModel { SidesViewModel(get(named("sideRepository"))) }
    viewModel { MeatViewModel(get(named("meatsRepository"))) }
    viewModel { OthersViewModel(get(named("othersRepository"))) }
}