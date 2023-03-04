package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.OptionShowList

interface IUserAuthRepository {

    suspend fun getOptionShowList(): OptionShowList


}
