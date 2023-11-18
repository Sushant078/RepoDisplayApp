package com.emyr78.theproj.api

import com.emyr78.theproj.constants.MockGithubApi
import com.emyr78.theproj.models.RepoApiModel
import com.emyr78.theproj.models.TopRepoSearchResults
import com.emyr78.theproj.models.UserApiModel
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

interface GitHubApi {
    @GET("search/repositories?q=language:kotlin")
    suspend fun getTopRepositories(): TopRepoSearchResults
}

@Singleton
@MockGithubApi
class MockGithubApi @Inject constructor() : GitHubApi {
    override suspend fun getTopRepositories(): TopRepoSearchResults {
        return TopRepoSearchResults(
            listOf(
                RepoApiModel(
                    id = 1L,
                    name = "Mock Repo Test",
                    description = "MockRepoDesc",
                    owner = UserApiModel(1L, "Hilt"),
                    stargazersCount = 10,
                    forksCount = 1,
                    contributorsUrl = "https://www.google.com",
                    createdDate = "1/1/2023",
                    updatedDate = "1/1/2023"
                )
            )
        )
    }
}

