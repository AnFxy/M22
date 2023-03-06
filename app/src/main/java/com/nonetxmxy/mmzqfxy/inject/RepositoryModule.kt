package com.nonetxmxy.mmzqfxy.inject

import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.repository.IOrderRepository
import com.nonetxmxy.mmzqfxy.repository.create.AuthRepository
import com.nonetxmxy.mmzqfxy.repository.IUserAuthRepository
import com.nonetxmxy.mmzqfxy.repository.create.BeginRepository
import com.nonetxmxy.mmzqfxy.repository.create.OrderRepository
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
