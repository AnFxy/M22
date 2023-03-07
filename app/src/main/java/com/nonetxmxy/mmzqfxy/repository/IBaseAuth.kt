package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.AdministrativeData
import com.nonetxmxy.mmzqfxy.model.OptionShowList

interface IBaseAuth {
    suspend fun getOptionShowList(vararg connetColumn: String): OptionShowList
    suspend fun getAdministrativeList(): AdministrativeData
}