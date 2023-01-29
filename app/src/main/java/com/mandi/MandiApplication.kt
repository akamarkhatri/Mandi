package com.mandi

import android.app.Application
import com.mandi.data.AppContainer
import com.mandi.data.FakeAppContainer

class MandiApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = FakeAppContainer()
    }
}