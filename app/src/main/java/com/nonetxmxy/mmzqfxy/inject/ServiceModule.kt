package com.nonetxmxy.mmzqfxy.inject

import com.nonetxmxy.mmzqfxy.service.IBeginService
import com.nonetxmxy.mmzqfxy.service.IAuthService
import com.nonetxmxy.mmzqfxy.service.IMainService
import com.nonetxmxy.mmzqfxy.service.ServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideIBeginService(): IBeginService =
        ServiceImpl.giveIBeginService()

    @Provides
    fun provideIAuthService(): IAuthService =
        ServiceImpl.giveIAuthService()

    @Provides
    fun provideIMainService(): IMainService =
        ServiceImpl.giveIMainService()
}