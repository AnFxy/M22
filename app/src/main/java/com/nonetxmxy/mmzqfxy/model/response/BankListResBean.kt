package com.nonetxmxy.mmzqfxy.model.response

import com.nonetxmxy.mmzqfxy.model.auth.BankMessage

data class BankListResBean(
    val bankCards: List<BankMessage>
)
