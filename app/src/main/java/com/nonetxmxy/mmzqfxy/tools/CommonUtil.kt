package com.nonetxmxy.mmzqfxy.tools

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity

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

    }
}
