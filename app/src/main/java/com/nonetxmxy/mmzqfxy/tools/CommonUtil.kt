package com.nonetxmxy.mmzqfxy.tools

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import java.sql.Date
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class CommonUtil {
    companion object {
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
    }
}
