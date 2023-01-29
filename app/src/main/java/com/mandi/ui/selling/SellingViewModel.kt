package com.mandi.ui.selling

import com.mandi.data.AppContainer
import com.mandi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellingViewModel @Inject internal constructor(appContainer: AppContainer): BaseViewModel(appContainer){
}