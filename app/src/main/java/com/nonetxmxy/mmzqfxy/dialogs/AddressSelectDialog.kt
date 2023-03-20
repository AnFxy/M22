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
import com.nonetxmxy.mmzqfxy.model.ProvinceOrCity
import com.nonetxmxy.mmzqfxy.model.Regions

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
                adapter.setList(handlerAddress(getCity(data.beEymAI)))
                provinceName = data.DmFXvr
            } else {
                c = data
                cityName = data.DmFXvr
                addressSelectOKBlock?.invoke(provinceName, cityName)
                dismiss()
            }
        }
        adapter
    }

    private var addressData: Regions? = null

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

        addressData?.Rpp?.let {
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
            addressData?.Rpp?.let {
                addressAdapter.setList(handlerAddress(it))
            }
        }
        binding.tvCity.setOnClickListener {
            p?.beEymAI?.let { i ->
                c = null
                cityName = StringUtils.getString(R.string.select_city)
                addressAdapter.setList(handlerAddress(getCity(i)))
            }
        }
    }

    private fun getCity(id: Long) = addressData?.ewgSAY?.filter {
        it.kgmAjY == id
    } ?: emptyList()

    private var p: ProvinceOrCity? = null

    private var c: ProvinceOrCity? = null

    private fun handlerAddress(administrativeItems: List<ProvinceOrCity>): List<ProvinceOrCity> {
        //A-Z排序
        val data = administrativeItems.sortedBy { item ->
            item.DmFXvr
        }
        //查找首字母索引
        val dataList = mutableListOf<ProvinceOrCity>()
        data.groupBy {
            it.DmFXvr.first().uppercaseChar()
        }.forEach { entry ->
            var showParentLetter = true
            entry.value.forEachIndexed { listIndex, item ->
                if (listIndex != 0) {
                    showParentLetter = false
                }
                dataList.add(
                    ProvinceOrCity(
                        isShowLetter = showParentLetter,
                        letter = entry.key.toString(),
                        DmFXvr = item.DmFXvr,
                        kgmAjY = item.kgmAjY,
                        beEymAI = item.beEymAI
                    )
                )
            }
        }
        return dataList
    }

    fun setAdministrativeList(title: String, data: Regions) {
        binding.diaTitle.text = title
        addressData = data
    }
}