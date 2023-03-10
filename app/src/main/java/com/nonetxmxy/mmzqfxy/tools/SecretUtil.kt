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
            moveArrow: String = BuildConfig.SECRET_ARROW
        ): String {
            Timber.e("加密前body：$originalContent")
            Timber.e("加密的password：$password")
            val iv = IvParameterSpec(moveArrow.toByteArray())
            val key = SecretKeySpec(password.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key, iv)
            val jiaMiData = cipher.doFinal(originalContent.toByteArray())
            val jiaMiStr = SpecialCode.encode(jiaMiData)
            Timber.e("加密后的数据：$jiaMiStr")
            return jiaMiStr
        }

        // 解密方法
        fun jieMi(
            originalContent: String,
            password: String,
            moveArrow: String = BuildConfig.SECRET_ARROW
        ): String {
            Timber.e("解密之前的数据：$originalContent")
            Timber.e("解密的password：$password")
            val byteMi = SpecialCode.decode(originalContent)
            val iv = IvParameterSpec(moveArrow.toByteArray())
            val key = SecretKeySpec(password.toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, key, iv)
            val jieMiData = cipher.doFinal(byteMi)
            Timber.e("解密后的数据 ${String(jieMiData, Charsets.UTF_8)}")
            return String(jieMiData, Charsets.UTF_8)
        }

        fun randomKey(isNeedJiaMi: Boolean = true) =
            if (isNeedJiaMi) {
                val chars = ('a' .. 'z') + ('A' .. 'Z') + ('0' .. '9')
                List(24) {
                    Random.nextInt(0, chars.size)
                }.map {
                    chars[it]
                }.joinToString("")
            } else {
                ""
            }
    }
}