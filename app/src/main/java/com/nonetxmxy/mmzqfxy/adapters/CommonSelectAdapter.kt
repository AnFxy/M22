package com.nonetxmxy.mmzqfxy.adapters

import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.model.OptionShowItem

class CommonSelectAdapter :
    BaseQuickAdapter<OptionShowItem, BaseViewHolder>(R.layout.item_dia_common_select) {

    var curIndex = -1

    override fun convert(holder: BaseViewHolder, item: OptionShowItem) {
        holder.setText(R.id.textview, item.showContent)
        holder.setBackgroundColor(
            R.id.textview,
            if (holder.layoutPosition == curIndex)
                ColorUtils.getColor(R.color.color_f4f4f4)
            else
                ColorUtils.getColor(R.color.white)
        )
    }
}