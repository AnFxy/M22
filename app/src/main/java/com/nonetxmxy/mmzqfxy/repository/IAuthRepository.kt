package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.SampleBank

interface IAuthRepository {

    suspend fun getCards(): List<SampleBank>
}
