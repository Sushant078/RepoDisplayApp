package com.emyr78.theproj.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@DisableInstallInCheck //Module is included in a different module, no need for @InstallIn
abstract class OkHttpConfigModule {
    companion object{
        @Provides
        @Singleton
        fun provideOkHttpConfigurator() = object: OkHttpConfigurator{
            override fun configure(clientBuilder: OkHttpClient.Builder) {
                clientBuilder.callTimeout(10,TimeUnit.SECONDS)
            }
        }
    }
}

interface OkHttpConfigurator{
    fun configure(clientBuilder: OkHttpClient.Builder)
}