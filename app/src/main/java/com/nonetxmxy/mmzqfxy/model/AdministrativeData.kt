package com.nonetxmxy.mmzqfxy.model

import com.contrarywind.interfaces.IPickerViewData

data class AdministrativeData(
    val province: List<AdministrativeItem>,
    val city: List<AdministrativeItem>
) {
    data class AdministrativeItem(
        val name: String,
        val id: Int,
        val parentid: Int
    ) : IPickerViewData {
        override fun getPickerViewText() = name
    }
}


data class AddressItem(
    val showIndex: Boolean,
    val indexName: String,
    val name: String,
    val parentid: Int,
    val id: Int
)