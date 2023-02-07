package com.mandi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MandiApplication: Application() {
//    lateinit var appContainer: AppContainer

    companion object {
        private lateinit var instance: Application
        fun  getInstance(): Application {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
//        appContainer = FakeAppContainer()
    }
}