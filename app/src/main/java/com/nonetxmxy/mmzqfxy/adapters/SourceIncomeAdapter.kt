package com.nonetxmxy.mmzqfxy.adapters

import android.graphics.Typeface
import android.widget.TextView
import com.blankj.utilcode.util.ColorUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.model.OptionShowItem

class SourceIncomeAdapter :
    BaseQuickAdapter<OptionShowItem, BaseViewHolder>(R.layout.item_soure_income) {

    var currentIndex = -1

    override fun convert(holder: BaseViewHolder, item: OptionShowItem) {
        val textview = holder.getView<TextView>(R.id.textview)
        textview.text = item.showContent
        if (currentIndex == holder.layoutPosition) {
            textview.typeface = Typeface.DEFAULT_BOLD
            textview.setTextColor(ColorUtils.getColor(R.color.gray_532e00))
            textview.setBackgroundResource(R.drawable.shape_btn_enable_bg)
        } else {
            textview.typeface = Typeface.DEFAULT
            textview.setTextColor(ColorUtils.getColor(R.color.gray_999999))
            textview.setBackgroundResource(R.drawable.shape_1_stroke_e3e3_6_solid_white)
        }
    }
}