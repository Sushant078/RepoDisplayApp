package com.emyr78.theproj.di.modules

import com.emyr78.theproj.api.GitHubApi
import com.emyr78.theproj.api.MockGithubApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GitHubApiModule {
    @Binds
    fun provideGithubApi(mockGithubApi: MockGithubApi): GitHubApi
}