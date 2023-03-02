package com.nonetxmxy.mmzqfxy.inject

import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.create.BeginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun createBeginRepository(
        beginRepository: BeginRepository
    ) : IBeginRepository
}