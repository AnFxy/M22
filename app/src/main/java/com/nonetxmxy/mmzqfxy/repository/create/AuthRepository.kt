package com.nonetxmxy.mmzqfxy.repository.create

import com.nonetxmxy.mmzqfxy.model.SampleBank
import com.nonetxmxy.mmzqfxy.repository.IAuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AuthRepository @Inject constructor() : IAuthRepository {

    override suspend fun getCards(): List<SampleBank> {
        delay(1000)
        return listOf(
            SampleBank(
                bankName = "银行1",
                bankType = "银行类型1",
                bankNumber = "8893 3424 4350 3453"
            ),
            SampleBank(
                bankName = "银行2",
                bankType = "银行类型2",
                bankNumber = "8893 3424 4350 3453"
            ),
            SampleBank(
                bankName = "银行3",
                bankType = "银行类型3",
                bankNumber = "8893 3424 4350 3453"
            ),
            SampleBank(
                bankName = "银行4",
                bankType = "银行类型4",
                bankNumber = "8893 3424 4350 3453"
            ),
            SampleBank(
                bankName = "银行5",
                bankType = "银行类型5",
                bankNumber = "8893 3424 4350 3453"
            )
        )
    }
}