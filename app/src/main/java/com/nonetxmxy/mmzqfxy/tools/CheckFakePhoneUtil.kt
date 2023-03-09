package com.nonetxmxy.mmzqfxy.tools

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class CheckFakePhoneUtil {

    companion object {
        suspend fun isFacePhone() : Boolean {
            val abi = getProperties("ro.product.cpu.abi")
            val usePPP = getProperties("ro.radio.use-app")
            val console = getProperties("init.svc.console")
            val emulator1 = "x86" == abi
            val emulator2 = "yes" == usePPP
            val device1 = usePPP.isEmpty()
            val device2 = console.isEmpty()
            return !(emulator1 || emulator2) &&(device1 || device2)
        }

        private suspend fun getProperties(key: String): String =
            try {
                val process = withContext(Dispatchers.IO) {
                    Runtime.getRuntime().exec("getprop $key")
                }
                val inputStream = process.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val buffer = StringBuffer()
                var line: String?
                while (withContext(Dispatchers.IO) { reader.readLine() }.also { line = it } != null) {
                    buffer.append(line)
                }
                buffer.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }
    }
}
