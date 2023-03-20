package com.nonetxmxy.mmzqfxy.customer_view

import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ToastUtils

abstract class SimplePermissionCallBack : PermissionUtils.FullCallback {
    override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {
        ToastUtils.showShort("Has rechazado la autorizacion")
    }
}
