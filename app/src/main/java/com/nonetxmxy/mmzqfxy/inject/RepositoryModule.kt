package com.nonetxmxy.mmzqfxy.inject

import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IUserAuthRepository
import com.nonetxmxy.mmzqfxy.repository.create.BeginRepository
import com.nonetxmxy.mmzqfxy.repository.create.UserAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun createBeginRepository(
        beginRepository: BeginRepository
    ): IBeginRepository

    @Binds
    abstract fun createUserAuthRepository(
        userAuthRepository: UserAuthRepository
    ): IUserAuthRepository
}