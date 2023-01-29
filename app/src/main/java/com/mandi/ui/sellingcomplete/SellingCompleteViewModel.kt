package com.mandi.ui.sellingcomplete

import com.mandi.data.AppContainer
import com.mandi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SellingCompleteViewModel @Inject internal constructor(appContainer: AppContainer): BaseViewModel(appContainer){
}