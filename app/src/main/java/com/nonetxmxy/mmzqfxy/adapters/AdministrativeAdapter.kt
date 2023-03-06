package com.nonetxmxy.mmzqfxy.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.model.AddressItem

class AdministrativeAdapter : BaseQuickAdapter<AddressItem, BaseViewHolder>(R.layout.item_address) {
    override fun convert(holder: BaseViewHolder, item: AddressItem) {
        holder.setText(R.id.tvName, item.name)
            .setText(R.id.tvIndex, item.indexName)
        holder.setVisible(R.id.tvIndex, item.showIndex)
    }
}