package com.nonetxmxy.mmzqfxy.base

class LocalCache {
    companion object {
        // 是否已经登录了
        var isLogged: Boolean by SPSet(FinalKeys.IS_LOGGED, false)

        // 是否已经展示过披露了
        var isShowedTips: Boolean by SPSet(FinalKeys.IS_TIPS, false)

        //认证
        var infoCredit = 0
        var workCredit = 0
        var contactPersonCredit = 0
    }
}