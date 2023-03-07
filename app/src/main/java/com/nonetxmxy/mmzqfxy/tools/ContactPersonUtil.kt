package com.nonetxmxy.mmzqfxy.tools

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import com.blankj.utilcode.util.StringUtils
import com.nonetxmxy.mmzqfxy.R

object ContactPersonUtil {

    val KEY_CONTACT = "ContactName"
    val KEY_PHONE = "ContactPhone"

    @SuppressLint("Range")
    fun getOneContactData(context: Context, uri: Uri): Map<String, String> {
        val contactInfoMap = mutableMapOf<String, String>()
        val contentResolver = context.contentResolver ?: return contactInfoMap
        val cursor = contentResolver.query(uri, null, null, null, null) ?: return contactInfoMap
        cursor.use { cur ->
            if (cur.moveToNext()) {

                contactInfoMap[KEY_CONTACT] = cur.getString(cur.getColumnIndex("display_name"))
                val id = cur.getString(cur.getColumnIndex("_id"))

                val curPhone = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    "contact_id=$id",
                    null,
                    null
                ) ?: return@use

                curPhone.use {
                    contactInfoMap[KEY_PHONE] = if (it.moveToNext()) it.getString(it.getColumnIndex("data1")) else ""

                    val handlerPhone =
                        contactInfoMap[KEY_PHONE]!!
                            .replace("-".toRegex(), "")
                            .trim { c -> c <= ' ' }
                            .replace(" ".toRegex(), "")
                            .replace(
                                StringUtils.getString(R.string.internationall_number), ""
                            )

                    contactInfoMap[KEY_PHONE] =
                        if (handlerPhone.startsWith("51")) handlerPhone.replaceFirst(
                            "51",
                            ""
                        ) else if (handlerPhone.startsWith("0")) handlerPhone.replaceFirst("0", "")
                        else handlerPhone
                }
            }
        }
        return contactInfoMap
    }
}