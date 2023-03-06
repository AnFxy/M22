package com.nonetxmxy.mmzqfxy.service

import com.nonetxmxy.mmzqfxy.model.BaseResponse
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import retrofit2.http.POST

interface IUserAuthService {

    @POST(NetPaths.optionShowList)
    suspend fun getOptionShowList(): BaseResponse<OptionShowList>
}