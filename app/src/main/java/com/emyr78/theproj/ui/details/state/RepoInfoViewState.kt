package com.emyr78.theproj.ui.details.state

sealed class RepoInfoViewState
data object RepoInfoViewStateLoading : RepoInfoViewState()
data class RepoInfoViewStateLoaded(
    val repoName: String,
    val repoDescription: String,
    val createdDate: String,
    val updatedDate: String
) : RepoInfoViewState()
data class RepoInfoViewStateError(
    val message: String,
) : RepoInfoViewState()