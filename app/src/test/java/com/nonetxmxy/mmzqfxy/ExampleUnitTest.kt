package com.nonetxmxy.mmzqfxy

import com.nonetxmxy.mmzqfxy.tools.SecretUtil
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun jiaMiIT() {
        // nNCHTOT60OhC3pU9ulK3xwoCQ94EplxsKSnGeKwd1RVV3QQ0Pi5kStYPvJHX kCuyQ+VVWhXJyX54/ZUXrxUYWGm/juyBrq4V0CxnVeAplZ0fcSo3h8Kc+Qsm kjquB3AAoWMFtJwmoF7IZFFPYr7rlOkKg/WPojE0EQqnd1AEBCTPrn7VI7j4 wyf0Yr2fFwqdyDCkTefPK6O8PopxWqnh93tcGv7H2NVX+daNJ2aOa6IF/c86 F/VSh5CQLBVzqjvrmd4Yf+FJow3f1wVpygKrgNKraMooMRsf9+XFeEEc0lX3 qvXzBwtjP5CX9Ib4bDHgeVnjcV3GcTX2Os0war9RM8AIGSAV311XhB73lPCd HnsT/4nvzhN8u3+6SkZfadod98341
        // nNCHTOT60OhC3pU9ulK3xwoCQ94EplxsKSnGeKwd1RVV3QQ0Pi5kStYPvJHX kCuyQ+VVWhXJyX54/ZUXrxUYWGm/juyBrq4V0CxnVeAplZ0fcSo3h8Kc+Qsm kjquB3AAoWMFtJwmoF7IZFFPYr7rlOkKg/WPojE0EQqnd1AEBCTPrn7VI7j4 wyf0Yr2fFwqdyDCkTefPK6O8PopxWqnh93tcGv7H2NVX+daNJ2aOa6IF/c86 F/VSh5CQLBVzqjvrmd4Yf+FJow3f1wVpygKrgNKraMooMRsf9+XFeEEc0lX3 qvXzBwtjP5CX9Ib4bDHgeVnjcV3GcTX2Os0war9RM8AIGSAV311XhB73lPCd HnsT/4nvzhN8u3+6SkZfadod98341
        val resesult = SecretUtil.jieMi("nNCHTOT60OhC3pU9ulK3xwoCQ94EplxsKSnGeKwd1RVV3QQ0Pi5kStYPvJHX kCuyQ+VVWhXJyX54/ZUXrxUYWGm/juyBrq4V0CxnVeAplZ0fcSo3h8Kc+Qsm kjquB3AAoWMFtJwmoF7IZFFPYr7rlOkKg/WPojE0EQqnd1AEBCTPrn7VI7j4 wyf0Yr2fFwqdyDCkTefPK6O8PopxWqnh93tcGv7H2NVX+daNJ2aOa6IF/c86 F/VSh5CQLBVzqjvrmd4Yf+FJow3f1wVpygKrgNKraMooMRsf9+XFeEEc0lX3 qvXzBwtjP5CX9Ib4bDHgeVnjcV3GcTX2Os0war9RM8AIGSAV311XhB73lPCd HnsT/4nvzhN8u3+6SkZfadod",
            password = "418716189834187161898341"
        )
        println("结果为： $resesult")
    }
}