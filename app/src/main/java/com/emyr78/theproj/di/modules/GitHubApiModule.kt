package com.emyr78.theproj.di.modules

import com.emyr78.theproj.api.GitHubApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Call
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GitHubApiModule {
    companion object {
        @Provides
        @Singleton
        fun provideOkhttp(): Call.Factory {
            return OkHttpClient.Builder().build()
        }

        @Provides
        @Singleton
        fun provideMoshi(): Moshi {
            return Moshi.Builder().build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(moshi: Moshi, callFactory: Call.Factory): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .callFactory(callFactory)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Provides
        @Singleton
        fun provideGithubApi(retrofit: Retrofit): GitHubApi {
            return retrofit.create(GitHubApi::class.java);
        }
    }
}