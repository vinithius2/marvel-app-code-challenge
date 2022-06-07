package com.vinithius.di


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

private val listModules by lazy {
    listOf(
        repositoryModule,
        repositoryDataModule,
        networkModule
    )
}

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listModules)
        }
    }
}