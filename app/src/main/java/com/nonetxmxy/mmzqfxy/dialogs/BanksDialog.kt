package com.nonetxmxy.mmzqfxy.dialogs

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.StringUtils
import com.nonetxmxy.mmzqfxy.adapters.BankNamesAdapter
import com.nonetxmxy.mmzqfxy.base.BaseDialog
import com.nonetxmxy.mmzqfxy.databinding.DiaBanksBinding
import com.nonetxmxy.mmzqfxy.model.ShowBankBean

class BanksDialog @JvmOverloads constructor(
    context: Context, themeResId: Int = 0
) : BaseDialog<DiaBanksBinding>(context, themeResId) {

    private var allBlank = ArrayList<String>()
    private var title: String = ""
    var selectorListener: IStrSelectListener? = null
    private val commPickerAdapter = BankNamesAdapter(ArrayList())

    fun setData(blanks: List<String>, title: String = "") {
        allBlank.clear()
        allBlank.addAll(blanks)
        commPickerAdapter.setList(handleBlankGroup(blanks))
    }

    private fun handleSearchBlank(
        blanks: List<String>,
        keyWord: String
    ): List<ShowBankBean> {
        val allBlankGroup = ArrayList<ShowBankBean>()
        allBlankGroup.addAll(blanks.map {
            ShowBankBean(false, it)
        }.toList().filter {
            it.bankName.contains(keyWord, true)
        })
        return allBlankGroup

    }

    private fun handleBlankGroup(blanks: List<String>): List<ShowBankBean> {
        if (blanks.isEmpty()) {
            return emptyList()
        }
        val allBlankGroup = ArrayList<ShowBankBean>()
        val group = blanks.groupBy {
            it.first().toUpperCase().toString()
        }

        val hotHeadBlank = ShowBankBean(true, bankTitle = "Commonly used bank")
        allBlankGroup.add(hotHeadBlank)

        /**
         * 过滤出常用的银行
         */
        allBlankGroup.addAll(blanks.filterIndexed { index, _ ->
            index < 5
        }.map {
            ShowBankBean(false, it)
        }.toList())

        group.keys.sorted().forEach { key ->
            val keyGroup = group[key]
            val headBlank = ShowBankBean(true, bankTitle = key)
            allBlankGroup.add(headBlank)

            keyGroup?.forEach { blank ->
                val blankBean = ShowBankBean(false, blank)
                allBlankGroup.add(blankBean)
            }

        }
        return allBlankGroup
    }

    fun isDataEmpty(): Boolean {
        return commPickerAdapter.data.isEmpty()
    }

    override fun getViewBinding(): DiaBanksBinding {
        return DiaBanksBinding.inflate(layoutInflater)
    }

    override fun initView() {
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (ScreenUtils.getScreenHeight() / 3) * 2
        )
        binding.tvPickDialogTitle.text = title
        binding.rvPickDialog.layoutManager = LinearLayoutManager(context)
        binding.rvPickDialog.adapter = commPickerAdapter
        commPickerAdapter.setOnItemClickListener { _, _, position ->
            val showBlankBean = commPickerAdapter.data[position]
            if (!showBlankBean.isHeader) {
                val blankName = showBlankBean.bankName
                selectorListener?.onStrSelect(blankName)
                dismiss()
            }
        }
        binding.ivPickDialogClose.setOnClickListener {
            dismiss()
        }
        binding.eehfpafhpyEzcnhl.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(p0: Editable?) {
                val lists: List<ShowBankBean>
                val value = p0?.toString() ?: ""
                lists = if (StringUtils.isEmpty(value)) {
                    handleBlankGroup(allBlank)
                } else {
                    handleSearchBlank(allBlank, value)
                }
                commPickerAdapter.setList(lists)
            }
        })

    }
}