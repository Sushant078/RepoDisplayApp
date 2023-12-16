package com.emyr78.theproj.repo

import com.emyr78.theproj.api.GitHubApi
import com.emyr78.theproj.constants.toResultFlow
import com.emyr78.theproj.models.api.ContributorsApiModel
import com.emyr78.theproj.models.api.RepoApiModel
import javax.inject.Inject
import javax.inject.Singleton

//Abstraction might seem unnecessary but this extra layer ensures that features that use this
//are separated from the changes we make in GitHubApi

//TODO: need to return data from here rather than ApiResponse, structuring and layer to be added/implemented
@Singleton
class AppRepository @Inject constructor (private val gitHubApi: GitHubApi) {
    suspend fun getTopRepos() = toResultFlow {
        gitHubApi.getTopRepositories()
    }

    suspend fun getRepoInfo(repoOwner: String, repoName: String) = toResultFlow {
        gitHubApi.getRepo(repoOwner, repoName)
    }

    suspend fun getContributors(repoOwner: String, repoName: String) = toResultFlow {
        gitHubApi.getContributors(repoOwner,repoName)
    }
}