package com.nonetxmxy.mmzqfxy.repository.create

import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.ConfigBean
import com.nonetxmxy.mmzqfxy.model.LoginBean
import com.nonetxmxy.mmzqfxy.model.Regions
import com.nonetxmxy.mmzqfxy.model.response.AllTags
import com.nonetxmxy.mmzqfxy.model.response.UpdateResBean
import com.nonetxmxy.mmzqfxy.repository.IBeginRepository
import com.nonetxmxy.mmzqfxy.service.IBeginService
import com.nonetxmxy.mmzqfxy.service.IMainService
import javax.inject.Inject

class BeginRepository @Inject constructor(
    private val beginService: IBeginService,
    private val mainService: IMainService
) : IBeginRepository {
    override suspend fun getAppConfig(): ConfigBean {
        val maps = HashMap<String, String>()
        maps["pTXZgXQxM"] = BuildConfig.CODE
        maps["mFPBO"] = BuildConfig.LANGUAGE
        return beginService.configGot(maps).checkDataEmpty()
    }

    override suspend fun checkUpdateInformation(): UpdateResBean {
        val maps = HashMap<String, String>()
        maps["xvf"] = BuildConfig.CODE
        maps["TlQ"] = BuildConfig.LANGUAGE
        maps["JVGzexCF"] = BuildConfig.VERSION_CODE.toString()
        maps["iiRkrfUYek"] = "1"
        return beginService.update(maps).checkDataEmpty()
    }

    override suspend fun getLoginType(number: String) {
        val maps = HashMap<String, String>()
        maps["IFeu"] = BuildConfig.CODE
        maps["tTdFgtIwJa"] = BuildConfig.LANGUAGE
        maps["ZSWPu"] = number

        LocalCache.isOldMan = beginService.userType(maps).checkDataEmpty().tbHvUWTY == 1
    }

    override suspend fun sendSmsCode() {
        val maps = HashMap<String, String>()
        maps["LKJJz"] = BuildConfig.CODE
        maps["QyzBANJgn"] = BuildConfig.LANGUAGE
        maps["MdNZKtHwJ"] = LocalCache.phoneNumber
        maps["gbsNrji"] = "2"
        maps["wYoIn"] = LocalCache.adjustId
        maps["cPdNRHnS"] = "adjust"
        beginService.smsCode(maps).checkCodeError()
    }

    override suspend fun sendVoiceCode() {
        val maps = HashMap<String, String>()
        maps["evlvg"] = BuildConfig.CODE
        maps["VwUAQ"] = BuildConfig.LANGUAGE
        maps["yBLrKwSzzvy"] = LocalCache.phoneNumber
        maps["tfruXC"] = "adjust"
        maps["tzO"] = "2"
        maps["CVsb"] = LocalCache.adjustId
        beginService.voiceCode(maps).checkCodeError()
    }

    override suspend fun doLogin(code: String): LoginBean {
        val maps = HashMap<String, String>()
        maps["eZDqwXlH"] = BuildConfig.CODE
        maps["yJMo"] = BuildConfig.LANGUAGE
        maps["jtbHq"] = LocalCache.phoneNumber
        maps["AZC"] = code
        maps["ZBdaqR"] = BuildConfig.CODE
        maps["koxVr"] = BuildConfig.VERSION_CODE.toString()
        maps["mtGhYVoZjaj"] = Utils.getApp().packageName
        maps["NIX"] = "" // TODO
        maps["cwij"] = "" // TODO
        maps["rTfIZvie"] = "" // TODO
        maps["LQnvB"] = ""
        maps["rdRaQ"] = "firebase"
        maps["QmjDmCGoH"] = "" // TODO
        maps["apbJ"] = LocalCache.adjustId
        return beginService.login(maps).checkDataEmpty()
    }

    override suspend fun getOptionalDirections(): AllTags {
        val maps = HashMap<String, String>()
        maps["InHF"] = BuildConfig.CODE
        maps["PNJnHX"] = BuildConfig.LANGUAGE
        return mainService.getTags(maps).checkDataEmpty()
    }

    override suspend fun getLatestRegions(): Regions {
        val maps = HashMap<String, String>()
        maps["aRp"] = BuildConfig.CODE
        maps["bbXMFe"] = BuildConfig.LANGUAGE
        maps["NnL"] = LocalCache.token

        return mainService.regionArea(maps).checkDataEmpty()
    }

    override suspend fun getLatestBanks(): List<String> {
        val maps = HashMap<String, String>()
        maps["qYkAq"] = BuildConfig.CODE
        maps["rxNB"] = BuildConfig.LANGUAGE
        maps["CqMgCZzhY"] = LocalCache.currentProCode
        maps["fPZjqHyRR"] = LocalCache.token

        return beginService.banksGot(maps).checkDataEmpty().WPGaNatDXD
    }
}
