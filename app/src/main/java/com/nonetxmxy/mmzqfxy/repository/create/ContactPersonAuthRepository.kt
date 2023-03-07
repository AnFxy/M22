package com.nonetxmxy.mmzqfxy.repository.create

import com.nonetxmxy.mmzqfxy.model.AdministrativeData
import com.nonetxmxy.mmzqfxy.model.ContactPersonData
import com.nonetxmxy.mmzqfxy.model.OptionShowItem
import com.nonetxmxy.mmzqfxy.model.OptionShowList
import com.nonetxmxy.mmzqfxy.repository.IContactPersonAuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class ContactPersonAuthRepository @Inject constructor(/*val remoteService: IUserAuthService*/) :
    IContactPersonAuthRepository {
    override suspend fun submitContactPerson(data: ContactPersonData): Boolean {
        return true
    }

    override suspend fun getSubmitContactPerson(): ContactPersonData {
        return ContactPersonData("")
    }

    override suspend fun getOptionShowList(vararg connetColumn: String): OptionShowList {
        delay(1500)
        return OptionShowList(
            marryStatus =
            listOf(
                OptionShowItem("大学awqd", "大学"),
                OptionShowItem("大学", "大学dqdq")
            )
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