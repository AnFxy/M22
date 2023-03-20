package com.nonetxmxy.mmzqfxy.adapters

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.model.ShowBankBean

class BankNamesAdapter(data: ArrayList<ShowBankBean>) :
    BaseSectionQuickAdapter<ShowBankBean, BaseViewHolder>(
        R.layout.dia_bank_item_title,
        R.layout.dia_bank_item_content,
        data
    ) {
    override fun convert(holder: BaseViewHolder, item: ShowBankBean) {
        holder.setText(R.id.plpogzdtpp_mpzkxwb_fojkofw, item.bankName)
    }

    override fun convertHeader(helper: BaseViewHolder, item: ShowBankBean) {
        helper.setText(R.id.bddwhe, item.bankTitle)
    }
}
