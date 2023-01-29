package com.mandi.ui.base


import androidx.lifecycle.ViewModel
import com.mandi.data.AppContainer

abstract class BaseViewModel(val appContainer: AppContainer): ViewModel() {
}