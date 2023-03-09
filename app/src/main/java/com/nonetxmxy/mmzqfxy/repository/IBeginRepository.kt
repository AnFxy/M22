package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.LoginType
import com.nonetxmxy.mmzqfxy.model.UpdateType

interface IBeginRepository {

    suspend fun checkUpdateInformation(): UpdateType
    
    suspend fun getLoginType(): LoginType
}
