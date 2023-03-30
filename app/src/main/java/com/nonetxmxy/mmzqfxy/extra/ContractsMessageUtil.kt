package com.nonetxmxy.mmzqfxy.extra

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.text.TextUtils
import com.google.gson.Gson
import timber.log.Timber

object ContractsMessageUtil {
    //获取所有联系人（已转为json字符串）
    fun getAllContractStr(context: Context): String {
        val userContacts: ArrayList<UserContractBean> = getAllContractList(context)
        val gson = Gson()
        val s = gson.toJson(userContacts)
        userContacts.clear()
        return s
    }

    //获取所有联系人列表
    private fun getAllContractList(context: Context): ArrayList<UserContractBean> {
        val arrayList: ArrayList<UserContractBean> = ArrayList()
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(
                uri, arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_LAST_UPDATED_TIMESTAMP,
                    ContactsContract.CommonDataKinds.StructuredName.SORT_KEY_ALTERNATIVE
                ), null, null, null
            )
            cursor?.moveToFirst()
            do {
                var userName: String = cursor?.getString(0) ?: ""
                val userPhone: String = cursor?.getString(1) ?: ""
                val lastUpdatedTimestamp: Long = cursor?.getLong(2) ?: 0L
                val sortKeyAlt: String = cursor?.getString(3) ?: ""
                val split = sortKeyAlt.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                var family = ""
                var given = ""
                if (split.size > 1) {
                    family = split[1].trim { it <= ' ' }
                    given = split[0].trim { it <= ' ' }
                }
                if (!TextUtils.isEmpty(userPhone)) {
                    if (TextUtils.isEmpty(userName)) userName = userPhone
                    val userContact = UserContractBean(
                        userName,
                        userPhone,
                        lastUpdatedTimestamp.toString(),
                        given,
                        family
                    )
                    arrayList.add(userContact)
                }
            } while (cursor?.moveToNext() == true)
        } catch (e: Exception) {
            if (TextUtils.isEmpty(e.message)) {
                Timber.e(e.toString())
            } else {
                Timber.e(e.message)
            }
        } finally {
            cursor?.close()
        }
        return arrayList
    }
}

class UserContractBean(
    val xHNx: String,
    val ravD: String,
    val rsag: String,
    val ydf: String,
    val akKW: String
)
