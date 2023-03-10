package com.nonetxmxy.mmzqfxy.base.net

import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.tools.SecretUtil
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.nio.charset.Charset

class ResponseJieMiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        val jiaMiKey19 = response.headers["responseKey"]
        if (BuildConfig.MAIN_URL.contains(request.url.host)) {
            if (response.isSuccessful) {
                val body = response.body
                if (body != null) {
                    try {
                        val source = body.source()
                        source.request(Long.MAX_VALUE)
                        val buffer = source.buffer()
                        var charset = Charset.forName("UTF-8")
                        val contentType = body.contentType()
                        if (contentType != null) {
                            charset = contentType.charset(charset)
                        }
                        val bodyStr = buffer.clone().readString(charset)
                        val endJiaMiKey = bodyStr.subSequence(bodyStr.length - 5, bodyStr.length)
                        val completeKey = jiaMiKey19 + endJiaMiKey
                        if (BuildConfig.NEED_JIA_MI) {
                            response = response.newBuilder().body(
                                ResponseBody.create(
                                    contentType,
                                    SecretUtil.jieMi(
                                        bodyStr.replace("\"", ""),
                                        password = completeKey
                                    )
                                )
                            ).build()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
        return response
    }
}
