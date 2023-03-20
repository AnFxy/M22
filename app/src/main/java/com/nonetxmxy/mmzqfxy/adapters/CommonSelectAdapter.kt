package com.nonetxmxy.mmzqfxy.adapters

import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.model.response.Tags

class CommonSelectAdapter :
    BaseQuickAdapter<Tags, BaseViewHolder>(R.layout.item_dia_common_select) {

    var curIndex = -1

    override fun convert(holder: BaseViewHolder, item: Tags) {
        holder.setText(R.id.textview, item.cnTVzVSsBYV)
        holder.setBackgroundColor(
            R.id.textview,
            if (holder.layoutPosition == curIndex)
                ColorUtils.getColor(R.color.color_f4f4f4)
            else
                ColorUtils.getColor(R.color.white)
        )
    }
}
