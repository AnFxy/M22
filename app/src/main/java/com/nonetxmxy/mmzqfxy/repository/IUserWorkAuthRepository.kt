package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.WorkData

interface IUserWorkAuthRepository:IBaseAuth {
    suspend fun submitWorkInfo(data: WorkData): Boolean

    suspend fun getSubmitWorkInfo(): WorkData
}
