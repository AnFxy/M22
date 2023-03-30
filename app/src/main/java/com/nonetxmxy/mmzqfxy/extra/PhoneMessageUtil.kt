package com.nonetxmxy.mmzqfxy.extra

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.text.TextUtils
import com.google.gson.Gson
import timber.log.Timber

class PhoneMessageUtil {
    companion object {
        fun getSmsLogStr(context: Context): String {
            val smsLog = getSmsLog(context)
            smsLog.map {
                Timber.d(it?.YSmU)
            }
            Timber.d("1111-----${Gson().toJson(smsLog)}")
            return Gson().toJson(smsLog)
        }

        @SuppressLint("LongLogTag", "Range")
        fun getSmsLog(context: Context): ArrayList<SMSBean?> {

            val SMS_URI_ALL = "content://sms/" // 所有短信
            val uri = Uri.parse(SMS_URI_ALL)
            val projection = arrayOf(
                "_id", "address", "person",
                "body", "date", "type", "service_center", "read"
            )
            val cursor = context.contentResolver.query(
                uri, projection, null,
                null, "date desc"
            ) // 获取手机内部短信
            val smsInboxList: ArrayList<SMSBean?> = ArrayList()
            try {
                cursor!!.moveToFirst()
                do {
                    val number = cursor.getString(cursor.getColumnIndex("address")) //手机号
                    val read = cursor.getString(cursor.getColumnIndex("read")) //联系人姓名列表
                    val body = cursor.getString(cursor.getColumnIndex("body")) //内容
                    val date: Long = cursor.getLong(cursor.getColumnIndex("date"))//时间
                    val type = cursor.getString(cursor.getColumnIndex("type")) //1是接收到的，2是已发出

                    if (!TextUtils.isEmpty(number)) {
                        var changeNumber = number.replace("+", "").replace(" ", "")
                            .replace("-", "")
                        while (changeNumber.startsWith("0")) {
                            changeNumber = changeNumber.substring(1)
                        }
                        if (changeNumber.startsWith("51")) {
                            changeNumber = changeNumber.substring(2)
                        }
                        val smsInboxBean =
                            SMSBean(body, changeNumber, read ?: "-1", number, date.toString(), type)
                        smsInboxList.add(smsInboxBean)
                    }
                } while (cursor.moveToNext())
            } catch (ex: Exception) {
                Timber.e(ex.message ?: ex.toString())
            } finally {
                cursor?.close()
                return smsInboxList
            }
        }
    }
}


class SMSBean(
    val YSmU: String,
    val sNrmp: String,
    val mMph: String,
    val vRMspu: String,
    val RaQEWiWYGF: String,
    val toEinfc: String
)
