package com.nonetxmxy.mmzqfxy.tools

import com.blankj.utilcode.util.ToastUtils
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLHandshakeException

class ErrorHandleUtil {

    companion object {
        // 处理异常类型
        fun handleError(e: Exception) {
            val error = e.let {
                when (it) {
                    is SocketTimeoutException,
                    is TimeoutException,
                    is TimeoutCancellationException,
                    is ConnectException,
                    is UnknownHostException,
                    is SSLHandshakeException -> {
                        "La red actual no está disponible, compruebe la red o vuelva a intentarlo."
                    }
                    is JsonSyntaxException -> {  //请求成功，但Json语法异常,导致解析失败
                        "¡Error en el análisis de datos!"
                    }
                    is ParseException -> {       // ParseException异常表明请求成功，但是数据不正确
                        it.message ?: "¡Error en el análisis de datos!"   //msg为空，显示code
                    }
                    is HttpException -> {
                        "El acceso a la red es lento en el lado del servicio, por favor vuelva a intentarlo."
                    }
                    is IOException -> {
                        ""
                    }
                    is CancellationException -> {
                        ""
                    }
                    else -> {
                        it.message ?: "¡Error desconocido!"
                    }
                }
            }
            if (error.isNotEmpty()) {
                ToastUtils.showLong(error)
            }
            e.printStackTrace()
        }
    }
}
