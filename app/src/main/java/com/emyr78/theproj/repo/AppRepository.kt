package com.emyr78.theproj.repo

import com.emyr78.theproj.api.GitHubApi
import com.emyr78.theproj.models.RepoApiModel
import javax.inject.Inject
import javax.inject.Singleton

//Abstraction might seem unnecessary but this extra layer ensures that features that use this
//are separated from the changes we make in GitHubApi

@Singleton //TODO:Need to confirm it this is added to SingletonComponent.class or not
//Assuming it is added
class AppRepository @Inject constructor (private val gitHubApi: GitHubApi) {
    suspend fun getTopRepos(): List<RepoApiModel>{
        return gitHubApi.getTopRepositories().items
    }
}