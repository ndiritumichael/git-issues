package com.devmike.testing.di

import com.devmike.network.di.NetworkModule
import com.devmike.network.networkSource.GitHubIssuesRepo
import com.devmike.testing.networktestdoubles.FakeGithubRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(replaces = [NetworkModule::class], components = [SingletonComponent::class])
object NetworkTestingModule {
    @Provides
    @Singleton
    fun provideGitHubIssuesRepo(): GitHubIssuesRepo = FakeGithubRepo()
}
