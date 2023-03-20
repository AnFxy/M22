package com.nonetxmxy.mmzqfxy.base.net

import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.tools.SecretUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitSet private constructor() {
    companion object {
        val onlyOne by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitSet()
        }
    }

    fun provideRetrofit(url: String = BuildConfig.MAIN_URL): Retrofit {
        val requestJiaMiInterceptor = RequestJiaMiInterceptor()
        val responseJieMiInterceptor = ResponseJieMiInterceptor()
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        val okhttpBuilder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(responseJieMiInterceptor)

        // 添加请求头
        if (url == BuildConfig.MAIN_URL) {
            okhttpBuilder.addInterceptor(
                Interceptor { chain ->
                    val request = chain.request().newBuilder()
                    for ((key, value) in provideHeaders()) {
                        request.addHeader(key, value)
                    }
                    chain.proceed(request.build())
                }
            )
        }

        okhttpBuilder.addInterceptor(requestJiaMiInterceptor)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okhttpBuilder.addInterceptor(httpLoggingInterceptor)
        okhttpBuilder.connectTimeout(6L, TimeUnit.SECONDS)
            .readTimeout(6L, TimeUnit.SECONDS)
            .writeTimeout(6L, TimeUnit.SECONDS)

        return Retrofit
            .Builder()
            .baseUrl(url)
            .client(okhttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun provideHeaders(): Map<String, String> {
        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"
        headers["code"] = "MXC"
        headers["isSecert"] = if (BuildConfig.NEED_JIA_MI) "1" else "0"
        if (BuildConfig.NEED_JIA_MI) {
            headers["secretKey"] = SecretUtil.randomKey()
        }
        return headers
    }
}