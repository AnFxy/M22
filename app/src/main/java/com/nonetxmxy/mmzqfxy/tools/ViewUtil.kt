package com.nonetxmxy.mmzqfxy.tools

import android.view.View
import androidx.appcompat.widget.Toolbar

fun View.setVisible(isCouldSeen: Boolean) {
    visibility = if (isCouldSeen) View.VISIBLE else View.GONE
}

fun View.setCantBeSeen() {
    visibility = View.GONE
}

fun View.setCanBeSeen() {
    visibility = View.VISIBLE
}

fun View.setLimitClickListener(block: () -> Unit) {
    setOnClickListener(object : PreventMultiClickListener() {
        override fun onSafeClick() {
            block.invoke()
        }
    })
}


fun Toolbar.setMenuAndNavLimitClickListener(navClickBlock: () -> Unit, menuClickBlock: () -> Unit) {
    setNavigationOnClickListener(object : PreventMultiClickListener() {
        override fun onSafeClick() {
            navClickBlock.invoke()
        }
    })

    setOnMenuItemClickListener(object : PreventMultiClickListener() {
        override fun onSafeClick() {
            menuClickBlock.invoke()
        }
    })
}