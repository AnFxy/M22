package com.nonetxmxy.mmzqfxy.model

import com.chad.library.adapter.base.entity.SectionEntity

data class ShowBankBean(
    override val isHeader: Boolean,
    var bankName: String = "",
    var bankTitle: String = ""
) : SectionEntity {
    constructor() : this(false)
}
