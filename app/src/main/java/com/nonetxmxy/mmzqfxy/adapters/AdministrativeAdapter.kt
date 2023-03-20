package com.nonetxmxy.mmzqfxy.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.model.ProvinceOrCity

class AdministrativeAdapter :
    BaseQuickAdapter<ProvinceOrCity, BaseViewHolder>(R.layout.item_address) {
    override fun convert(holder: BaseViewHolder, item: ProvinceOrCity) {
        holder.setText(R.id.tvName, item.DmFXvr)
            .setText(R.id.tvIndex, item.letter)
        holder.setVisible(R.id.tvIndex, item.isShowLetter)
    }
}
