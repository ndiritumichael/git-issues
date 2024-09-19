package com.devmike.network.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.HttpInterceptor
import com.devmike.datastore.repo.DataStoreRepo
import com.devmike.network.networkSource.GitHubIssuesRepo
import com.devmike.network.networkSource.GitHubIssuesRepoImpl
import com.devmike.network.utils.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApolloClient(authInterceptor: HttpInterceptor): ApolloClient =
        ApolloClient
            .Builder()
            .serverUrl("https://api.github.com/graphql")
            .addHttpInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGitHubIssuesRepo(apolloClient: ApolloClient): GitHubIssuesRepo =
        GitHubIssuesRepoImpl(apolloClient)

    @Provides
    @Singleton
    fun providesAuthInterceptor(dataStoreRepo: DataStoreRepo): HttpInterceptor =
        AuthorizationInterceptor(dataStoreRepo)
}
