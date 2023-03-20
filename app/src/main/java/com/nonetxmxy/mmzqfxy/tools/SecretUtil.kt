package com.nonetxmxy.mmzqfxy.tools

import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.base.SpecialCode
import timber.log.Timber
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

class SecretUtil {
    companion object {

        // 加密方法
        fun jiaMi(
            originalContent: String,
            password: String,
            extraKey: String = "",
            path: String = "",
            moveArrow: String = BuildConfig.SECRET_ARROW
        ): String {
            Timber.d(" ")
            Timber.d("======================================================Start===========================================================================================")
            Timber.d("请求路径： $path")
            Timber.d("加密前body：$originalContent")
            Timber.d("加密的password：$password")
            val iv = IvParameterSpec(moveArrow.toByteArray())
            val key = SecretKeySpec(password.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key, iv)
            val jiaMiData = cipher.doFinal(originalContent.toByteArray())
            val jiaMiStr = SpecialCode.encode(jiaMiData) + extraKey
            Timber.e("加密后的数据：$jiaMiStr")
            Timber.d("======================================================END===========================================================================================")
            Timber.d(" ")
            return jiaMiStr
        }

        // 解密方法
        fun jieMi(
            originalContent: String,
            password: String,
            path: String = "",
            moveArrow: String = BuildConfig.SECRET_ARROW
        ): String {
            Timber.d(" ")
            Timber.d("======================================================Start===========================================================================================")
            Timber.d("请求路径： $path")
            Timber.e("解密之前的数据：$originalContent")
            Timber.e("解密的password：$password")
            val byteMi = SpecialCode.decode(originalContent)
            val iv = IvParameterSpec(moveArrow.toByteArray())
            val key = SecretKeySpec(password.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, key, iv)
            val jieMiData = cipher.doFinal(byteMi)
            Timber.e("解密后的数据 ${String(jieMiData, Charsets.UTF_8)}")
            Timber.d("======================================================END===========================================================================================")
            Timber.d(" ")
            return String(jieMiData, Charsets.UTF_8)
        }

        fun randomKey(
            isNeedJiaMi: Boolean = true,
            url: String = BuildConfig.MAIN_URL,
            count: Int = 24
        ) =
            if (isNeedJiaMi && url.contains(BuildConfig.MAIN_URL)) {
                val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
                List(count) {
                    Random.nextInt(0, chars.size)
                }.map {
                    chars[it]
                }.joinToString("")
            } else {
                ""
            }
    }
}