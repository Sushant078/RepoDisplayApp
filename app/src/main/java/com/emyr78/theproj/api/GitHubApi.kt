package com.emyr78.theproj.api

import com.emyr78.theproj.models.api.RepoApiModel
import com.emyr78.theproj.models.TopRepoSearchResults
import com.emyr78.theproj.models.api.ContributorsApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("search/repositories?q=language:kotlin")
    suspend fun getTopRepositories(): Response<TopRepoSearchResults>

    @GET("repos/{owner}/{name}")
    suspend fun getRepo(
        @Path("owner") repoOwner: String,
        @Path("name") repoName: String
    ): Response<RepoApiModel>

    @GET("repos/{owner}/{name}/contributors")
    suspend fun getContributors(
        @Path("owner") repoOwner: String,
        @Path("name") repoName: String,
    ): Response<List<ContributorsApiModel>>
}

