package com.example.projekat3.application

import android.app.Application
//import coreModule
//import courseModule
//import noteModule
//import org.koin.android.ext.koin.androidContext
//import org.koin.android.ext.koin.androidFileProperties
//import org.koin.android.ext.koin.androidLogger
//import org.koin.androidx.fragment.koin.fragmentFactory
//import org.koin.core.context.startKoin
//import org.koin.core.logger.Level
//
//import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initTimber()
        initKoin()
    }

    private fun initTimber() {
//        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
//        val modules = listOf(
//            courseModule,
//            coreModule,
//            noteModule
//        )
//        startKoin {
//            androidLogger(Level.ERROR)
//            // Use application context
//            androidContext(this@Application)
//            // Use properties from assets/koin.properties
//            androidFileProperties()
//            // Use koin fragment factory for fragment instantiation
//            fragmentFactory()
//            modules(modules)
//        }
    }

}