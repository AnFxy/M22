package com.nonetxmxy.mmzqfxy.tools

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

class CommonUtil {
    companion object {

        val MOXICO_PHONE_ADD_52 = "(\\+528)\\d{9}\$"
        val MOXICO_PHONE_52 = "(528)\\d{9}\$"
        val MOXICO_PHONE = "(8)\\d{9}\$"

        fun goGooglePlay(
            activity: FragmentActivity?,
            downloadUrl: String,
            _packageName: String = ""
        ) {

            val packageName = _packageName.ifEmpty { activity?.packageName }
            if (packageName?.isNotEmpty() == true) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("market://details?id=$packageName")
                if (activity != null) {
                    if (intent.resolveActivity(activity.packageManager) != null) {
                        activity.startActivity(intent)
                    } else {
                        val intentExtra = Intent(Intent.ACTION_VIEW)
                        intentExtra.data =
                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                        if (intentExtra.resolveActivity(activity.packageManager) != null) {
                            activity.startActivity(intentExtra)
                        } else {
                            // do nothing
                        }
                    }
                }
            }
        }

        fun timeLongToDate(timeLong: Long): String {
            val ts = Timestamp(timeLong)
            val date = Date(ts.time)

            return SimpleDateFormat("dd/MM/yyyy").format(date)
        }

        fun checkPhone(phoneNumber: String) =
            (phoneNumber.matches(Regex(MOXICO_PHONE)) ||
                    phoneNumber.matches(Regex(MOXICO_PHONE_52)) ||
                    phoneNumber.matches(Regex(MOXICO_PHONE_ADD_52)))

        fun formatPhone(phoneNumber: String) =
            if (checkPhone(phoneNumber)) {
                val noAdd = phoneNumber.replace("+", "")
                if (noAdd.startsWith("8")) {
                    "52$noAdd"
                } else {
                    noAdd
                }
            } else {
                phoneNumber
            }
    }
}
