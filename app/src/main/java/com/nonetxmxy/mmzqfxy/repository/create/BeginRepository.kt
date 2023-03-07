package com.nonetxmxy.mmzqfxy.repository.create

import com.nonetxmxy.mmzqfxy.model.LoginType
import com.nonetxmxy.mmzqfxy.model.UpdateType
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class BeginRepository @Inject constructor() : IBeginRepository {

    override suspend fun checkUpdateInformation(): UpdateType {
        delay(1000)
        return UpdateType.NO_NEED_UPDATE
    }

    override suspend fun getLoginType(): LoginType {
        delay(1000)
        return LoginType.SMS
    }
}
