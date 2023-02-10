package com.mandi

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collectLatest

@HiltAndroidApp
class MandiApplication: Application() {
//    lateinit var appContainer: AppContainer

    companion object {
        private lateinit var instance: MandiApplication
        fun  getInstance(): MandiApplication {
            return instance
        }
    }

    val applicationObserver by lazy {
        ApplicationObserver.getInstance()
    }

    fun getAppState(): AppState? {
       return applicationObserver.state.value
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        ProcessLifecycleOwner
            .get()
            .lifecycle
            .addObserver(applicationObserver)
    }
}