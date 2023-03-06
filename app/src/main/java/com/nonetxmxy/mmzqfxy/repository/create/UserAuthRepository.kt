package com.nonetxmxy.mmzqfxy.repository.create

import com.nonetxmxy.mmzqfxy.model.OptionShowItem
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import com.nonetxmxy.mmzqfxy.repository.IUserAuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserAuthRepository @Inject constructor(/*val remoteService: IUserAuthService*/) :
    IUserAuthRepository {

    override suspend fun getOptionShowList(): OptionShowList {
        //remoteService.getOptionShowList()
        delay(1500)
        return OptionShowList(
            listOf(
                OptionShowItem("6", "大学"),
                OptionShowItem("8", "大大学"),
                OptionShowItem("9", "大大大学"),
                OptionShowItem("88", "大大大大大大大大大大大大学"),
                OptionShowItem("88", "大大大大大大大大大大大大学"),
                OptionShowItem("88", "大大大大大大大大大大大大学"),
                OptionShowItem("88", "大大大大大大大大大大大大学")
            )
        )
    }
}