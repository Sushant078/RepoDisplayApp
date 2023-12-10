package com.emyr78.theproj.ui.screen

import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ActivityDrivenScreenNavigator @Inject constructor(): ScreenNavigator {
    var handleGoToScreen: ((Screen) -> Unit)? = null
    override fun goToScreen(screen: Screen) {
        handleGoToScreen?.invoke(screen)
    }
}