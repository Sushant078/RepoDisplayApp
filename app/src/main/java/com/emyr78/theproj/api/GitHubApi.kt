package com.emyr78.theproj.api

import com.emyr78.theproj.constants.MockGithubApi
import com.emyr78.theproj.models.RepoApiModel
import com.emyr78.theproj.models.TopRepoSearchResults
import com.emyr78.theproj.models.UserApiModel
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

interface GitHubApi {
    @GET("search/repositories?q=language:kotlin")
    suspend fun getTopRepositories(): TopRepoSearchResults

    @GET("repo/{owner}/{name}")
    suspend fun getRepo(
        @Path("owner") repoOwner: String,
        @Path("name") repoName: String
    ): RepoApiModel
}

