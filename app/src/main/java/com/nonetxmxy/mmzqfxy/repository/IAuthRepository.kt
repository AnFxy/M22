package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.BaseResponse
import com.nonetxmxy.mmzqfxy.model.auth.*
import com.nonetxmxy.mmzqfxy.model.response.FileResponse

interface IAuthRepository {

    suspend fun uploadFile(fileStr: String, suffixName: String): BaseResponse<FileResponse>

    suspend fun getSubmitWorkInfo(): WorkMessage

    suspend fun submitWorkInfo(workMessage: WorkMessage, startTime: Long)

    suspend fun getSubmitUserInfo(): UserMessage

    suspend fun submitUserInfo(userMessage: UserMessage, startTime: Long)

    suspend fun getSubmitContractInfo(): ContractMessage

    suspend fun submitContractInfo(contractMessage: ContractMessage, startTime: Long)

    suspend fun doOCRFlow(photoUrl: String): OCRResponse

    suspend fun getSubmitIDCardInfo(): IDMessage

    suspend fun submitIDCardInfo(idMessage: IDMessage, startTime: Long)

    suspend fun submitFaceInfo(startTime: Long)

    suspend fun getSubmitBanks(): List<BankMessage>

    suspend fun submitBanks(bankMessage: BankMessage, startTime: Long)
}
