package com.nonetxmxy.mmzqfxy.repository.create

import com.nonetxmxy.mmzqfxy.model.AdministrativeData
import com.nonetxmxy.mmzqfxy.model.OptionShowItem
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import com.nonetxmxy.mmzqfxy.model.SelfData
import com.nonetxmxy.mmzqfxy.repository.IUserInfoAuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class UserInfoAuthRepository @Inject constructor(/*val remoteService: IUserAuthService*/) :
    IUserInfoAuthRepository {

    override suspend fun getOptionShowList(vararg connetColumn: String): OptionShowList {
        //remoteService.getOptionShowList()
        delay(1500)
        return OptionShowList(
            marryStatus =
            listOf(
                OptionShowItem("大学awqd", "大学"),
                OptionShowItem("大学", "大学dqdq")
            )
        )
    }

    override suspend fun submitInfo(data: SelfData): Boolean {
        delay(2000)
        return true
    }

    override suspend fun getSubmitInfo(): SelfData {
        return SelfData(
            educationLevelShow = "666",
            educationLevel = "6",
            marryStatusShow = "大学",
            marryStatus = "大学",
            childrenTotalShow = "99",
            childrenTotal = "9",
            familyProvince = "999",
            familyCity = "66666",
            familyAddress = "66666666",
        )
    }

    override suspend fun getAdministrativeList(): AdministrativeData {
        delay(1500)
        return AdministrativeData(
            province = listOf(
                AdministrativeData.AdministrativeItem("abc", 1, 0),
                AdministrativeData.AdministrativeItem("bcd", 1, 0),
                AdministrativeData.AdministrativeItem("aaw", 2, 0),
                AdministrativeData.AdministrativeItem("eds", 2, 0),
                AdministrativeData.AdministrativeItem("wsq", 2, 0),
                AdministrativeData.AdministrativeItem("gtd", 3, 0)
            ), city = listOf(
                AdministrativeData.AdministrativeItem("市1", 1, 1),
                AdministrativeData.AdministrativeItem("市2", 2, 1),
                AdministrativeData.AdministrativeItem("市3", 3, 2),
                AdministrativeData.AdministrativeItem("省2市1", 4, 2),
                AdministrativeData.AdministrativeItem("省2市2", 5, 2),
                AdministrativeData.AdministrativeItem("省2市2", 5, 3)
            )
        )
    }
}