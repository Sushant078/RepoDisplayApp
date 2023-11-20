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

@Module(includes = [OkHttpConfigModule::class])
@InstallIn(SingletonComponent::class)
abstract class GitHubApiModule {
    companion object {
        @Provides
        @Singleton
        fun provideOkhttp(configurator: OkHttpConfigurator): Call.Factory {
            return OkHttpClient.Builder().apply {
                configurator.configure(this)
            }.build()
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