package com.mandi.ui.search

import com.mandi.base.BaseRobot

fun searchScreen(func: SearchRobot.() -> Unit) = SearchRobot().apply(func)

class SearchRobot: BaseRobot() {

}