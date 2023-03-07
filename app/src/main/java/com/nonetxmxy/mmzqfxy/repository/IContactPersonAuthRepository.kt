package com.nonetxmxy.mmzqfxy.repository

import com.nonetxmxy.mmzqfxy.model.ContactPersonData

interface IContactPersonAuthRepository : IBaseAuth {
    suspend fun submitContactPerson(data: ContactPersonData): Boolean

    suspend fun getSubmitContactPerson(): ContactPersonData
}
