package com.emyr78.theproj.api

import com.emyr78.theproj.models.RepoApiModel
import com.emyr78.theproj.models.UserApiModel
import javax.inject.Inject
import javax.inject.Singleton

interface GitHubApi {
    fun getTopRepositories(): List<RepoApiModel>
}

@Singleton
class MockGithubApi @Inject constructor() : GitHubApi {
    override fun getTopRepositories(): List<RepoApiModel> {
        return listOf(
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
    }
}

