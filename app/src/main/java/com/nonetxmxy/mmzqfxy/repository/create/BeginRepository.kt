package com.nonetxmxy.mmzqfxy.repository.create

import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.ConfigBean
import com.nonetxmxy.mmzqfxy.model.LocationType
import com.nonetxmxy.mmzqfxy.model.LoginBean
import com.nonetxmxy.mmzqfxy.model.Regions
import com.nonetxmxy.mmzqfxy.model.response.AllTags
import com.nonetxmxy.mmzqfxy.model.response.FaceResBean
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

    override suspend fun doLogout() {
        val maps = HashMap<String, String>()
        maps["tyrL"] = LocalCache.token
        maps["FDBHi"] = BuildConfig.CODE
        beginService.logout(maps).checkCodeError()
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

    override suspend fun submitLocationData(type: LocationType) {
        val maps = HashMap<String, String>()
        maps["bCy"] = BuildConfig.LANGUAGE
        maps["QJc"] = BuildConfig.CODE
        maps["pbsX"] = LocalCache.token
        maps["rbyA"] = "1"

        maps["BuU"] = LocalCache.lonLocal
        maps["HsbJ"] = LocalCache.latiLocal
        maps["nyUbq"] = type.weight

        mainService.submitHiddenData(maps).checkCodeError()
    }

    override suspend fun submitPhoneMessage() {
        val maps = HashMap<String, String>()
        maps["bCy"] = BuildConfig.LANGUAGE
        maps["QJc"] = BuildConfig.CODE
        maps["pbsX"] = LocalCache.token
        maps["rbyA"] = "6"

        maps["UDeNg"] = ""
        maps["EVPAwr"] = ""
        maps["tET"] = ""

        mainService.submitHiddenData(maps).checkCodeError()
    }

    override suspend fun submitOtherMessage() {
        val maps = HashMap<String, String>()
        maps["bCy"] = BuildConfig.LANGUAGE
        maps["QJc"] = BuildConfig.CODE
        maps["pbsX"] = LocalCache.token
        maps["rbyA"] = "7"

        maps["tVo"] = ""
        maps["gpi"] = ""
        maps["rSdIzOiMddQ"] = ""
        maps["KgV"] = ""
        maps["uNFkOcaWT"] = ""
        maps["yoss"] = ""
        maps["ToQSZR"] = ""
        maps["AjrirUUIg"] = ""
        maps["UMid"] = ""
        maps["Wml"] = ""
        maps["xQRo"] = ""
        maps["UyC"] = ""
        maps["gzGHNIPjdXV"] = ""
        maps["DfvFgptSJI"] = ""
        maps["CmjZdOx"] = ""
        maps["XBJLpmWbqsY"] = ""
        maps["aSa"] = ""
        maps["boJiyRLV"] = ""
        maps["gIFO"] = ""
        maps["sRcMf"] = ""
        maps["Gtylv"] = ""
        maps["eJqv"] = ""
        maps["nvD"] = ""
        maps["QSGVG"] = ""
        maps["KBOKQCB"] = ""
        maps["ihkgt"] = ""
        maps["RglUKH"] = ""
        maps["aFYWPoMlG"] = ""
        maps["dqpIwPWJT"] = ""
        maps["JJN"] = ""
        maps["Pok"] = ""
        maps["UKqnoyiXd"] = ""
        maps["hxG"] = ""
        maps["bke"] = ""
        maps["bcxkFWJjpN"] = ""
        maps["TCofkrjQ"] = ""
        maps["SAZKeeRai"] = ""
        maps["wDT"] = ""
        maps["HbujD"] = ""
        maps["juolTzR"] = ""
        maps["FGc"] = ""
        maps["nHzv"] = ""
        maps["vEGK"] = ""
        maps["slZHJ"] = ""
        maps["TrNw"] = ""
        maps["mbRyj"] = ""
        maps["rmRzw"] = ""
        maps["DorpuVh"] = ""
        maps["RWdxU"] = ""
        maps["RsPh"] = ""
        maps["mrwaHnptR"] = ""
        maps["CKMd"] = ""
        maps["EDkhT"] = ""
        maps["KrgVnEH"] = ""
        maps["YxzQNXNw"] = ""
        maps["PcHE"] = ""
        maps["FMvUIao"] = ""
        maps["RCC"] = ""
        maps["cLtDhFxT"] = ""
        maps["IPl"] = ""
        maps["yEoJUutSb"] = ""
        maps["SfZZaR"] = ""
        maps["LMaG"] = ""
        maps["qzAk"] = ""
        maps["yqNf"] = ""

        mainService.submitHiddenData(maps).checkCodeError()
    }

    override suspend fun submitSuggestion(content: String, picLink: List<String>) {
        val maps = HashMap<String, String>()
        maps["xGZLVE"] = BuildConfig.LANGUAGE
        maps["ruvMuv"] = BuildConfig.CODE
        maps["Fnc"] = LocalCache.token
        maps["Ysnd"] = LocalCache.phoneNumber
        maps["mLuKt"] = content
        maps["SzF"] = "5"
        maps["cCSpH"] = picLink.joinToString(",")

        mainService.submitFeedBack(maps).checkCodeError()
    }

    override suspend fun getFaceConfig(): FaceResBean {
        val maps = HashMap<String, String>()
        maps["efe"] = BuildConfig.LANGUAGE
        maps["ziBNh"] = BuildConfig.CODE
        maps["gkjSDJlprej"] = LocalCache.token

        return beginService.faceConfigGot(maps).checkDataEmpty()
    }
}
