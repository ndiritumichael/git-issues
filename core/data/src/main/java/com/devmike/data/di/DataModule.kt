package com.devmike.data.di

import com.devmike.data.sources.issues.IssuesRepositoryImpl
import com.devmike.data.sources.repositories.RepoSearchRepository
import com.devmike.domain.repository.IRepoSearchRepository
import com.devmike.domain.repository.IssuesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindIssuesRepository(issuesRepositoryImpl: IssuesRepositoryImpl): IssuesRepository

    @Binds
    abstract fun bindRepoSearchRepository(repository: RepoSearchRepository): IRepoSearchRepository
}
