package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.SelfData

interface IUserInfoAuthRepository:IBaseAuth {
    suspend fun submitInfo(data: SelfData): Boolean

    suspend fun getSubmitInfo(): SelfData
}
