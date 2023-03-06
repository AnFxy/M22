package com.nonetxmxy.mmzqfxy.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.StringUtils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.AdministrativeAdapter
import com.nonetxmxy.mmzqfxy.databinding.DiaAddressSelectBinding
import com.nonetxmxy.mmzqfxy.model.AddressItem
import com.nonetxmxy.mmzqfxy.model.AdministrativeData

class AddressSelectDialog constructor(context: Context, themeResId: Int) :
    Dialog(context, themeResId) {

    private val binding = DiaAddressSelectBinding.inflate(layoutInflater)

    var addressSelectOKBlock: ((String, String) -> Unit)? = null

    private val addressAdapter by lazy(LazyThreadSafetyMode.NONE) {
        val adapter = AdministrativeAdapter()
        adapter.setOnItemClickListener { _, _, position ->
            val data = adapter.data[position]
            if (p == null) {
                p = data
                adapter.setList(handlerAddress(getCity(data.id)))
                provinceName = data.name
            } else {
                c = data
                cityName = data.name
                addressSelectOKBlock?.invoke(provinceName, cityName)
                dismiss()
            }
        }
        adapter
    }

    private var addressData: AdministrativeData? = null

    private var provinceName = ""
        set(value) {
            field = value
            binding.tvProvince.text = value
            if (p != null) {
                binding.tvProvince.setTextColor(ColorUtils.getColor(R.color.yellow_ffd247))
            } else {
                binding.tvProvince.setTextColor(ColorUtils.getColor(R.color.gray_999999))
            }
        }

    private var cityName = ""
        set(value) {
            field = value
            binding.tvCity.text = value
            if (c != null) {
                binding.tvCity.setTextColor(ColorUtils.getColor(R.color.yellow_ffd247))
            } else {
                binding.tvCity.setTextColor(ColorUtils.getColor(R.color.gray_999999))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window?.apply {
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
            )
            attributes.gravity = Gravity.BOTTOM
            setWindowAnimations(R.style.AnimationBottomDialog)
        }
        initView()
    }

    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = addressAdapter

        addressData?.province?.let {
            addressAdapter.setList(handlerAddress(it))
        }

        binding.cancelar.setOnClickListener {
            dismiss()
        }
        binding.tvProvince.setOnClickListener {
            p = null
            c = null
            provinceName = StringUtils.getString(R.string.select_province)
            cityName = StringUtils.getString(R.string.select_city)
            addressData?.province?.let {
                addressAdapter.setList(handlerAddress(it))
            }
        }
        binding.tvCity.setOnClickListener {
            p?.id?.let { i ->
                c = null
                cityName = StringUtils.getString(R.string.select_city)
                addressAdapter.setList(handlerAddress(getCity(i)))
            }
        }
    }

    private fun getCity(id: Int) = addressData?.city?.filter {
        it.parentid == id
    } ?: emptyList()

    private var p: AddressItem? = null

    private var c: AddressItem? = null

    private fun handlerAddress(administrativeItems: List<AdministrativeData.AdministrativeItem>): List<AddressItem> {
        //A-Z排序
        val data = administrativeItems.sortedBy { item ->
            item.name
        }
        //查找首字母索引
        val dataList = mutableListOf<AddressItem>()
        data.groupBy {
            it.name.first().uppercaseChar()
        }.forEach { entry ->
            var showParentLetter = true
            entry.value.forEachIndexed { listIndex, item ->
                if (listIndex != 0) {
                    showParentLetter = false
                }
                dataList.add(
                    AddressItem(
                        showParentLetter, entry.key.toString(), item.name, item.parentid, item.id
                    )
                )
            }
        }
        return dataList
    }

    fun setAdministrativeList(title: String, data: AdministrativeData) {
        binding.diaTitle.text = title
        addressData = data
    }
}