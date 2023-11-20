package com.emyr78.theproj.repo

import com.emyr78.theproj.api.GitHubApi
import com.emyr78.theproj.models.RepoApiModel
import javax.inject.Inject
import javax.inject.Singleton

//Abstraction might seem unnecessary but this extra layer ensures that features that use this
//are separated from the changes we make in GitHubApi

@Singleton
class AppRepository @Inject constructor (private val gitHubApi: GitHubApi) {
    suspend fun getTopRepos(): List<RepoApiModel>{
        return gitHubApi.getTopRepositories().items
    }
}