package com.mandi

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ApplicationObserver private constructor(): LifecycleEventObserver {
    companion object {
        private var instance_ : ApplicationObserver? = null
        fun getInstance():ApplicationObserver {
            instance_ = instance_ ?: ApplicationObserver()
            return instance_!!
        }
    }
    private var curentState:AppState? = null
    private val _state by lazy { MutableStateFlow(curentState) }
    val state = _state.asStateFlow()
    fun getState() = curentState
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE-> {}
            Lifecycle.Event.ON_START -> {}
            Lifecycle.Event.ON_RESUME -> {
                updateState(AppState.RESUMED)

            }
            Lifecycle.Event.ON_STOP -> updateState(AppState.STOP)
            Lifecycle.Event.ON_DESTROY -> updateState(AppState.DESTROYED)
            Lifecycle.Event.ON_PAUSE -> {}
            Lifecycle.Event.ON_ANY -> {}
        }
    }

    private fun updateState(appState: AppState?) {
        curentState = appState
        Log.d("CurrentAppState","$curentState")
        _state.update { appState }
    }

}
enum class AppState {
    STOP,
    RESUMED,
    DESTROYED
}