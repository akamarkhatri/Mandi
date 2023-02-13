package com.mandi.base

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mandi.ApplicationObserver
import com.mandi.LaunchApp
import com.mandi.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val hiltRule = HiltAndroidRule(this)
    companion object {
        var CURRENT_ACTIVITY_STATE: Lifecycle.State? = null
        private set
//        var cuurentAppState = ApplicationObserver./
        val applicationObserver: ApplicationObserver by lazy {
            ApplicationObserver.getInstance()
        }
        fun getAppState() = applicationObserver.getState()
    }

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule)
        .around(composeTestRule)


    @Before
    fun setUpHelper() {
       TestHelper.composeTestRule = composeTestRule
        composeTestRule.activity.lifecycleScope.launch(Dispatchers.Main) {
            composeTestRule.activity.lifecycle.addObserver(object : DefaultLifecycleObserver{
                override fun onCreate(owner: LifecycleOwner) {
                    super.onCreate(owner)
                    CURRENT_ACTIVITY_STATE = Lifecycle.State.CREATED
                }

                override fun onStart(owner: LifecycleOwner) {
                    super.onStart(owner)
                    CURRENT_ACTIVITY_STATE = Lifecycle.State.STARTED
                }

                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)
                    CURRENT_ACTIVITY_STATE = Lifecycle.State.RESUMED
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    CURRENT_ACTIVITY_STATE = Lifecycle.State.DESTROYED
                }
            })
        }
    }
}