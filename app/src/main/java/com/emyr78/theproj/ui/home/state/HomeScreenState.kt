package com.emyr78.theproj.ui.home.state

import com.emyr78.theproj.models.RepoItem

sealed class HomeScreenState {
    data object HomeScreenStateLoading: HomeScreenState()
    data class HomeScreenStateLoaded(val repos: List<RepoItem>) : HomeScreenState()
    data class HomeScreenStateError(val message: String): HomeScreenState()
}