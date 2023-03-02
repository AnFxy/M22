package com.nonetxmxy.mmzqfxy.base

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import com.nonetxmxy.mmzqfxy.R

class DialogSet(context: Context, theme: Int, private val itemLayoutId: Int) :
    Dialog(context, theme) {

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(viewId: Int): T {
        val view: View = findViewById(viewId)
        return view as T
    }

    init {
        setContentView(itemLayoutId)
    }

    fun setDialogWidthHeight(width: Int, height: Int) {
        window?.setLayout(width, height)
    }

    companion object {
        fun provideDialog(context: Context, view: Int): DialogSet =
            DialogSet(context, R.style.SimpleDialog, view)

        fun provideDialogBottom(context: Context, view: Int): DialogSet {
            val dialog = DialogSet(context, R.style.SimpleDialog, view)
            val dialogWindow = dialog.window
            val lp = dialogWindow?.attributes
            lp?.gravity = Gravity.BOTTOM
            dialogWindow?.attributes = lp
            return dialog
        }
    }
}
