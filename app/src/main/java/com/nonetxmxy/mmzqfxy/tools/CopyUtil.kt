package com.nonetxmxy.mmzqfxy.tools

import android.content.ClipboardManager
import android.content.Context
import com.blankj.utilcode.util.Utils

class CopyUtil {
    companion object {
        fun copyToClipboard(content: String) {
            val cm: ClipboardManager =
                Utils.getApp().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            cm.text = content
        }
    }
}