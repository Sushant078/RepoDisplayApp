package com.emyr78.theproj.repo

import com.emyr78.theproj.api.GitHubApi
import com.emyr78.theproj.models.api.ContributorsApiModel
import com.emyr78.theproj.models.api.RepoApiModel
import javax.inject.Inject
import javax.inject.Singleton

//Abstraction might seem unnecessary but this extra layer ensures that features that use this
//are separated from the changes we make in GitHubApi

//TODO: add error handling by creating states and migrate to Flows
@Singleton
class AppRepository @Inject constructor (private val gitHubApi: GitHubApi) {
    suspend fun getTopRepos(): List<RepoApiModel>{
        return gitHubApi.getTopRepositories().items
    }

    suspend fun getRepoInfo(repoOwner: String, repoName: String): RepoApiModel{
        return gitHubApi.getRepo(repoOwner,repoName)
    }

    suspend fun getContributors(repoOwner: String, repoName: String): List<ContributorsApiModel> {
        return gitHubApi.getContributors(repoOwner,repoName)
    }
}