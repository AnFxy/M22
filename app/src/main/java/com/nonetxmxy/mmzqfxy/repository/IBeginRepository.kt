package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.ConfigBean
import com.nonetxmxy.mmzqfxy.model.LocationType
import com.nonetxmxy.mmzqfxy.model.LoginBean
import com.nonetxmxy.mmzqfxy.model.Regions
import com.nonetxmxy.mmzqfxy.model.response.AllTags
import com.nonetxmxy.mmzqfxy.model.response.FaceResBean
import com.nonetxmxy.mmzqfxy.model.response.UpdateResBean

interface IBeginRepository {

    suspend fun getAppConfig(): ConfigBean

    suspend fun checkUpdateInformation(): UpdateResBean

    suspend fun getLoginType(number: String)

    suspend fun sendSmsCode()

    suspend fun sendVoiceCode()

    suspend fun doLogin(code: String): LoginBean

    suspend fun doLogout()

    suspend fun getOptionalDirections(): AllTags

    suspend fun getLatestRegions(): Regions

    suspend fun getLatestBanks(): List<String>

    suspend fun submitLocationData(type: LocationType)

    suspend fun submitPhoneMessage()

    suspend fun submitOtherMessage()

    suspend fun getFaceConfig(): FaceResBean
}
