package com.emyr78.theproj

import com.emyr78.theproj.api.GitHubApi
import com.emyr78.theproj.models.RepoApiModel
import com.emyr78.theproj.models.UserApiModel
import com.emyr78.theproj.repo.AppRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


private val fakeRepoApiModel = RepoApiModel(
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

class AppRepositoryTest {
    private lateinit var appRepository: AppRepository
    private val fakeGithubApi = FakeGithubApi()

    @Before
    fun setup(){
        appRepository = AppRepository(fakeGithubApi)
    }

    @Test
    fun successfulQuery(){
        val topRepos = appRepository.getTopRepos()

        assertThat(topRepos.size).isEqualTo(1)
        assertThat(topRepos[0]).isEqualTo(fakeRepoApiModel)
    }
}


private class FakeGithubApi : GitHubApi {
    override fun getTopRepositories(): List<RepoApiModel> {
        return listOf(
            fakeRepoApiModel
        )
    }
}


