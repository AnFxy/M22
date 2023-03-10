package com.nonetxmxy.mmzqfxy.base.net

import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.tools.SecretUtil
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer
import org.json.JSONObject

class RequestJiaMiInterceptor : Interceptor {

    // 将json转换为json对象 这个方法主要就是 添加额外的通用共有参数
    private fun jsonToJSONObject(json: String): JSONObject {
        val emptyJSONObject = JSONObject()
        if (json.isNotEmpty()) {
            val originalObject = JSONObject(json)
            val keys = originalObject.keys()
            var value: Any?
            while (keys.hasNext()) {
                val currentKey = keys.next()
                value = originalObject.opt(currentKey)
                // TODO 如果value值再请求map时已经加密了，那么需要解密
                emptyJSONObject.put(currentKey, value)
            }
        }
        return emptyJSONObject
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val oldBody = request.body
        val headers = request.headers
        val mediaType = headers["Content-type"]?.toMediaTypeOrNull()
        val key: String? = headers["secretKey"]
        val jiaMikey = key?.substring(0, 19) ?: ""
        val addToBodyBehind = key?.substring(19)
        val buffer = Buffer()
        oldBody?.writeTo(buffer)
        val strOldBody: String = buffer.readUtf8()
        if (BuildConfig.MAIN_URL.contains(request.url.host)) {
            val jsonStr = jsonToJSONObject(strOldBody).toString()
            val newBody = if (BuildConfig.NEED_JIA_MI) {
                if (!key.isNullOrBlank()) {
                    (SecretUtil.jiaMi(jsonStr, key) + addToBodyBehind).toRequestBody(mediaType)
                } else {
                    jsonStr.toRequestBody(mediaType)
                }
            } else {
                jsonStr.toRequestBody(mediaType)
            }

            request = if (!key.isNullOrBlank()) {
                request.newBuilder()
                    .header("secretKey", jiaMikey)
                    .method(request.method, newBody).build()
            } else {
                request.newBuilder().method(request.method, newBody).build()
            }
        }
        return chain.proceed(request)
    }
}