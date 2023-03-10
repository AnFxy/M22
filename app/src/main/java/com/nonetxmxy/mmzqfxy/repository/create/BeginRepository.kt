package com.nonetxmxy.mmzqfxy.repository.create

import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.model.LoginType
import com.nonetxmxy.mmzqfxy.model.UpdateType
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.service.IBeginService
import kotlinx.coroutines.delay
import javax.inject.Inject

class BeginRepository @Inject constructor(
    private val beginService: IBeginService
) : IBeginRepository {

    override suspend fun checkUpdateInformation(): UpdateType {
        val maps = HashMap<String, String>()
        maps["xvf"] = BuildConfig.CODE
        maps["TlQ"] = BuildConfig.LANGUAGE
        maps["JVGzexCF"] = BuildConfig.VERSION_CODE.toString()
        maps["iiRkrfUYek"] = "1"
        val data = beginService.update(maps).checkDataEmpty()
        val isNeedUpdate = data.oMGtrc == 0
        val isForceUpdate = data.ITwm == 1
        return if (isForceUpdate) {
            UpdateType.FORCE
        } else if (isNeedUpdate) {
            UpdateType.OPTIONAL
        } else {
            UpdateType.NO_NEED_UPDATE
        }
    }

    override suspend fun getLoginType(): LoginType {
        delay(1000)
        return LoginType.SMS
    }
}
