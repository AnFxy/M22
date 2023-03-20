package com.nonetxmxy.mmzqfxy.tools

import android.view.View
import androidx.appcompat.widget.Toolbar
import java.text.NumberFormat
import java.util.*

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

fun Number.jinE(): String =
    "S/".plus(" ").plus(NumberFormat.getNumberInstance(Locale("es", "PE")).format(this))

fun Int.days() = "$this dias"