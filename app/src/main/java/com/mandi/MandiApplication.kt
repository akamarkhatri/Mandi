package com.mandi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MandiApplication: Application() {
//    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
//        appContainer = FakeAppContainer()
    }
}