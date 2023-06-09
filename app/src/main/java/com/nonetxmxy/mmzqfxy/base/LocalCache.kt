package com.nonetxmxy.mmzqfxy.base

class LocalCache {
    companion object {
        // 是否已经登录了
        var isLogged: Boolean by SPSet(FinalKeys.IS_LOGGED, false)

        // 是否已经展示过披露了
        var isShowedTips: Boolean by SPSet(FinalKeys.IS_TIPS, false)

        // 手机号
        var phoneNumber: String by SPSet(FinalKeys.PHONE_NUMBER, "")

        // 客服手机号
        var serviceNumber: String by SPSet(FinalKeys.SERVICE_NUMBER, "")

        // 邮箱号
        var email: String by SPSet(FinalKeys.EMAIL, "")

        // token
        var token: String by SPSet(FinalKeys.TOKEN, "")

        // 当前产品编码
        var currentProCode: String by SPSet(FinalKeys.CURRENT_PRO_CODE, "")

        // 是否是特殊账号
        var isSAccount: Boolean by SPSet(FinalKeys.IS_S_ACCOUNT, false)

        // 是否是老用户
        var isOldMan: Boolean by SPSet(FinalKeys.IS_OLD_MAN, false)

        // adjustid
        var adjustId: String by SPSet(FinalKeys.ADJUST_ID, "")

        // 上一次发送验证码的时候
        var lastTimeSendCode: Long by SPSet(FinalKeys.LAST_SEND_CODE_TIME, 0L)

        // 上一次发送语音验证码的时候
        var lastTimeSendVoiceCode: Long by SPSet(FinalKeys.LAST_SEND_VCODE_TIME, 0L)

        // 图片信息
        var photoInfo: String by SPSet(FinalKeys.PHOTO_INFO, "")

        // 身份证正面图片
        var idCardTop: String by SPSet(FinalKeys.ID_TOP, "")

        // 身份证反面图片
        var idCardBehind: String by SPSet(FinalKeys.ID_BeHIND, "")

        // 人脸识别照片
        var facePhoto: String by SPSet(FinalKeys.FACE_PHOTO, "")

        // Location
        var lonLocal: String by SPSet(FinalKeys.LONG_LOCATION, "")
        var latiLocal: String by SPSet(FinalKeys.LATI_LOCATION, "")

        // 人脸识别key
        var faceAccessKey: String by SPSet(FinalKeys.ACCESS_KEY, "")
        var faceSecretKey: String by SPSet(FinalKeys.SECRET_KEY, "")

        // 人脸识别id
        var faceLivenessID: String by SPSet(FinalKeys.FACE_LIVENESS_ID, "")


        //认证
        var infoCredit = 0
        var workCredit = 0
        var contactPersonCredit = 0
        var idCredit = 0
        var bankCredit = 0
        var faceCredit = 0

        fun fourAuth() =
            infoCredit == 1 && workCredit == 1 && contactPersonCredit == 1 && idCredit == 1

        fun clearALLCache() {
            // 是否已经登录了
            isLogged = false

            // 手机号
            phoneNumber = ""

            // token
            token = ""

            // 当前产品编码
            currentProCode = ""

            // 是否是特殊账号
            isSAccount = false

            // 是否是老用户
            isOldMan = false

            // 上一次发送验证码的时候
            lastTimeSendCode = 0L

            // 上一次发送语音验证码的时候
            lastTimeSendVoiceCode = 0L

            // 图片信息
            photoInfo = ""

            // 身份证正面图片
            idCardTop = ""

            // 身份证反面图片
            idCardBehind = ""

            // 人脸识别照片
            facePhoto = ""

            //认证
            infoCredit = 0
            workCredit = 0
            contactPersonCredit = 0
            idCredit = 0
            bankCredit = 0
            faceCredit = 0

            // Location
            lonLocal = ""
            latiLocal = ""

            // 人脸识别key
            faceAccessKey = ""
            faceSecretKey = ""

            // 人脸识别id
            faceLivenessID = ""
        }
    }

}