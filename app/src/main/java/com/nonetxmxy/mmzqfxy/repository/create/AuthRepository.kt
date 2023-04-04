package com.nonetxmxy.mmzqfxy.repository.create

import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.model.BaseResponse
import com.nonetxmxy.mmzqfxy.model.auth.*
import com.nonetxmxy.mmzqfxy.model.response.FileResponse
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import com.nonetxmxy.mmzqfxy.service.IAuthService
import com.nonetxmxy.mmzqfxy.service.IFileUploadService
import com.nonetxmxy.mmzqfxy.service.IMainService
import com.nonetxmxy.mmzqfxy.tools.CommonUtil.Companion.formatPhone
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: IAuthService,
    private val fileUploadService: IFileUploadService
) : IAuthRepository {

    override suspend fun uploadFile(
        fileStr: String,
        suffixName: String
    ): BaseResponse<FileResponse> {
        val maps = HashMap<String, String>()
        maps["IuuvhApJnKIN"] = fileStr
        maps["JgnEIZWTqS"] = "fileupload"
        maps["woXueYtLs"] = BuildConfig.CODE
        maps["IhgNRQnJVPAk"] = suffixName
        return fileUploadService.uploadFile(maps)
    }

    override suspend fun getSubmitWorkInfo(): WorkMessage {
        val maps = HashMap<String, String>()
        maps["KeL"] = BuildConfig.LANGUAGE
        maps["CpP"] = LocalCache.currentProCode
        maps["GCRv"] = BuildConfig.CODE
        maps["ODtRxgcSBn"] = LocalCache.token
        maps["sBKNfLNMx"] = "1"
        return authService.workMessageGot(maps).checkDataEmpty()
    }

    override suspend fun submitWorkInfo(workMessage: WorkMessage, startTime: Long) {
        val maps = HashMap<String, String>()
        maps["SuaCySYzmEn"] = BuildConfig.LANGUAGE
        maps["YCdqI"] = BuildConfig.CODE
        maps["lSpaLNb"] = LocalCache.currentProCode
        maps["kbsHvq"] = LocalCache.token
        maps["SxVgVauNC"] = "1"

        maps["NgkE"] = workMessage.WLQ
        maps["SgY"] = workMessage.TqqZwacSZ
        maps["IVqVOgnjia"] = workMessage.PrKpqCuxQ
        maps["cpeKwj"] = workMessage.tLxEVr
        maps["jQOvw"] = workMessage.Yrfwo
        maps["dFWU"] = workMessage.rIIWYi
        maps["ndaHL"] = workMessage.BRDyVcJsB ?: ""
        maps["uEJ"] = (System.currentTimeMillis() - startTime).toString()
        maps["coFMHP"] = workMessage.rFAso
        maps["GsJIwGClOu"] = workMessage.CVZLaIndZG
        maps["bVysySFk"] = workMessage.XHIcmEoky
        maps["GJlj"] = workMessage.ODZzYj
        maps["LkDg"] = workMessage.UpoeXeBjwVB
        authService.workMessageSend(maps).checkCodeError()
    }

    override suspend fun getSubmitUserInfo(): UserMessage {
        val maps = HashMap<String, String>()
        maps["KeL"] = BuildConfig.LANGUAGE
        maps["CpP"] = LocalCache.currentProCode
        maps["GCRv"] = BuildConfig.CODE
        maps["ODtRxgcSBn"] = LocalCache.token
        maps["sBKNfLNMx"] = "2"
        return authService.userMessageGot(maps).checkDataEmpty()
    }

    override suspend fun submitUserInfo(userMessage: UserMessage, startTime: Long) {
        val maps = HashMap<String, String>()
        maps["SuaCySYzmEn"] = BuildConfig.LANGUAGE
        maps["YCdqI"] = BuildConfig.CODE
        maps["lSpaLNb"] = LocalCache.currentProCode
        maps["kbsHvq"] = LocalCache.token
        maps["SxVgVauNC"] = "2"

        maps["kmNlaD"] = userMessage.kaAT
        maps["pYbfyaXf"] = userMessage.AnmkImJZjp
        maps["NBzaoHqhZ"] = userMessage.hFHaKOITD ?: ""
        maps["SfZH"] = userMessage.vnQBj
        maps["FelINBmQMY"] = userMessage.qLh
        maps["oBHFXVd"] = userMessage.URCcx
        maps["zNy"] = userMessage.dFZqoeahk
        maps["AwiuWtMMtWk"] = (System.currentTimeMillis() - startTime).toString()
        maps["tUAjCeFxZsB"] = userMessage.URXdUgWGz ?: ""
        maps["SfxZ"] = userMessage.keMI ?: ""
        maps["MVDYu"] = userMessage.OnDO ?: ""

        authService.userMessageSent(maps).checkCodeError()
    }

    override suspend fun getSubmitContractInfo(): ContractMessage {
        val maps = HashMap<String, String>()
        maps["KeL"] = BuildConfig.LANGUAGE
        maps["CpP"] = LocalCache.currentProCode
        maps["GCRv"] = BuildConfig.CODE
        maps["ODtRxgcSBn"] = LocalCache.token
        maps["sBKNfLNMx"] = "3"
        return authService.contractMessageGot(maps).checkDataEmpty()
    }

    override suspend fun submitContractInfo(contractMessage: ContractMessage, startTime: Long) {
        val maps = HashMap<String, String>()
        maps["SuaCySYzmEn"] = BuildConfig.LANGUAGE
        maps["YCdqI"] = BuildConfig.CODE
        maps["lSpaLNb"] = LocalCache.currentProCode
        maps["kbsHvq"] = LocalCache.token
        maps["SxVgVauNC"] = "3"

        maps["ePxloBmkkQ"] = contractMessage.Cqr
        maps["jCTfmdrscW"] = formatPhone(contractMessage.RHaDS)
        maps["rnMV"] = contractMessage.KiVk
        maps["hGcQRWvNU"] = contractMessage.pRgj
        maps["kxLQ"] = formatPhone(contractMessage.faVW)
        maps["VnqYhn"] = contractMessage.vwuan
        maps["XgpY"] = (System.currentTimeMillis() - startTime).toString()
        authService.userMessageSent(maps).checkCodeError()
    }

    override suspend fun doOCRFlow(photoUrl: String): OCRResponse {
        val maps = HashMap<String, String>()
        maps["SuaCySYzmEn"] = BuildConfig.LANGUAGE
        maps["YCdqI"] = BuildConfig.CODE
        maps["lSpaLNb"] = LocalCache.currentProCode
        maps["kbsHvq"] = LocalCache.token
        maps["SxVgVauNC"] = "7"

        maps["YtLuQhNj"] = photoUrl
        return authService.ocrSent(maps).checkDataEmpty()
    }

    override suspend fun getSubmitIDCardInfo(): IDMessage {
        val maps = HashMap<String, String>()
        maps["KeL"] = BuildConfig.LANGUAGE
        maps["CpP"] = LocalCache.currentProCode
        maps["GCRv"] = BuildConfig.CODE
        maps["ODtRxgcSBn"] = LocalCache.token
        maps["sBKNfLNMx"] = "4"

        return authService.idMessageGot(maps).checkDataEmpty()
    }

    override suspend fun submitIDCardInfo(idMessage: IDMessage, startTime: Long) {
        val maps = HashMap<String, String>()
        maps["SuaCySYzmEn"] = BuildConfig.LANGUAGE
        maps["YCdqI"] = BuildConfig.CODE
        maps["lSpaLNb"] = LocalCache.currentProCode
        maps["kbsHvq"] = LocalCache.token
        maps["SxVgVauNC"] = "4"

        maps["YtLuQhNj"] = idMessage.AgzmxkTVhrb
        maps["qYDHsyFec"] = idMessage.MpOHLbXEBT
        maps["NhGm"] = idMessage.fOEEzcNxpv
        maps["eCqBul"] = idMessage.ZdKyAmeCGxL ?: ""
        maps["BVH"] = idMessage.ExHTUA
        maps["dGvdqaNow"] = (System.currentTimeMillis() - startTime).toString()
        maps["yHJskdf"] = idMessage.JkfImZtlQ
        maps["OuiodsJSD"] = idMessage.Tjq
        authService.idMessageSent(maps).checkCodeError()
    }

    override suspend fun submitFaceInfo(startTime: Long) {
        val maps = HashMap<String, String>()
        maps["SuaCySYzmEn"] = BuildConfig.LANGUAGE
        maps["YCdqI"] = BuildConfig.CODE
        maps["lSpaLNb"] = LocalCache.currentProCode
        maps["kbsHvq"] = LocalCache.token
        maps["SxVgVauNC"] = "6"

        maps["BVWVRt"] = LocalCache.facePhoto
        // 活体认证ID
        maps["UbcFWGd"] = LocalCache.faceLivenessID
        maps["khTM"] = (System.currentTimeMillis() - startTime).toString()
        // 活体认证的结果
        maps["DLqhRMDy"] = "1"
        authService.faceSent(maps).checkCodeError()
    }

    override suspend fun getSubmitBanks(): List<BankMessage> {
        val maps = HashMap<String, String>()
        maps["KeL"] = BuildConfig.LANGUAGE
        maps["CpP"] = LocalCache.currentProCode
        maps["GCRv"] = BuildConfig.CODE
        maps["ODtRxgcSBn"] = LocalCache.token
        maps["sBKNfLNMx"] = "5"

        return authService.cardMessageGot(maps).checkDataEmpty().ztJJHn
    }

    override suspend fun submitBanks(bankMessage: BankMessage, startTime: Long) {
        val maps = HashMap<String, String>()
        maps["SuaCySYzmEn"] = BuildConfig.LANGUAGE
        maps["YCdqI"] = BuildConfig.CODE
        maps["lSpaLNb"] = LocalCache.currentProCode
        maps["kbsHvq"] = LocalCache.token
        maps["SxVgVauNC"] = "5"

        if (bankMessage.ZlE != 0L) {
            maps["MyEMaLZNe"] = bankMessage.ZlE.toString()
        }
        maps["qnmLwz"] = bankMessage.TtoUz
        maps["XlLuaXPY"] = bankMessage.zUbbNgrgLl
        maps["JxxUD"] = bankMessage.XpuVfIvsAt
        maps["BQyybcwPiz"] = bankMessage.RhgBNBzglD
        maps["ZKUZ"] = (System.currentTimeMillis() - startTime).toString()

        authService.cardMessageSent(maps).checkCodeError()
    }
}