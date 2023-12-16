package com.emyr78.theproj.ui.details.state

import com.emyr78.theproj.models.ContributorsItem

sealed class RepoContributorsViewState
data object RepoContributorsViewStateLoading : RepoContributorsViewState()
data class RepoContributorsViewStateLoaded(
    val contributors: List<ContributorsItem>
) : RepoContributorsViewState()
data class RepoContributorsViewStateError(
    val message: String
) : RepoContributorsViewState()