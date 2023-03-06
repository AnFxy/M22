package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.AdministrativeData
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import com.nonetxmxy.mmzqfxy.model.SelfData

interface IUserAuthRepository {

    suspend fun getOptionShowList(): OptionShowList

    suspend fun submitInfo(data: SelfData): Boolean

    suspend fun getSubmitInfo(): SelfData

    suspend fun getAdministrativeList(): AdministrativeData

}
