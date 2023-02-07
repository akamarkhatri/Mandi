package com.mandi.ui.base


import androidx.lifecycle.ViewModel
import com.mandi.data.AppContainer
import com.mandi.data.DispatcherProvider
import javax.inject.Inject

abstract class BaseViewModel(val appContainer: AppContainer): ViewModel() {
    @Inject
    lateinit var dispatcherProvider: DispatcherProvider
}