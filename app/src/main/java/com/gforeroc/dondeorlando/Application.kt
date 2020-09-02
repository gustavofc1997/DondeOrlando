package com.gforeroc.dondeorlando

import android.app.Application
import android.content.ContextWrapper
import com.gforeroc.dondeorlando.di.appModule
import com.pixplicity.easyprefs.library.Prefs
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModule)

            Prefs.Builder().setContext(this@Application)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setUseDefaultSharedPreference(true)
                .build()
        }
        val builder = Picasso.Builder(this)
        builder.downloader(OkHttp3Downloader(this, Long.MAX_VALUE))
        val built = builder.build()
        built.isLoggingEnabled = true
        Picasso.setSingletonInstance(built)
    }
}