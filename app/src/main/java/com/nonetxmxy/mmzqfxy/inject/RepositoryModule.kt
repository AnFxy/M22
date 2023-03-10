package com.nonetxmxy.mmzqfxy.inject

import com.nonetxmxy.mmzqfxy.repository.*
import com.nonetxmxy.mmzqfxy.repository.create.*
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
    abstract fun createOrderRepository(
        orderRepository: OrderRepository
    ): IOrderRepository

    @Binds
    abstract fun createAuthRepository(
        authRepository: AuthRepository
    ): IAuthRepository
}
