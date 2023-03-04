package com.nonetxmxy.mmzqfxy.customer_view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ViewCommonSelectBinding
import com.nonetxmxy.mmzqfxy.dialogs.AddressSelectDialog
import com.nonetxmxy.mmzqfxy.dialogs.CommonSelectDialog
import com.nonetxmxy.mmzqfxy.model.OptionShowItem

class CommonSelectView constructor(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {

    private val binding = ViewCommonSelectBinding.inflate(LayoutInflater.from(context), this, true)

    private val commonSelectDialog by lazy {
        CommonSelectDialog(context, R.style.SimpleDialog)
    }

    private val addressSelectDialog by lazy {
        AddressSelectDialog(context, R.style.SimpleDialog)
    }

    var clickOptionItemBlock: ((String) -> Unit)? = null

    private var selectType = 0

    private var selectTitle = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) binding.title.text = value
        }

     var selectContent = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                binding.content.typeface = Typeface.DEFAULT_BOLD
                binding.content.text = value
            }
        }


    init {
        initAttribute(attrs)
        initListener()
    }

    private fun initListener() {
        binding.rootLayout.setOnClickListener {
            when (selectType) {
                0 -> {
                    commonSelectDialog.show()
                }

                1 -> {

                }
            }
        }

        commonSelectDialog.clickItemBlock = {
            binding.content.text = it.showContent
            binding.content.typeface = Typeface.DEFAULT_BOLD
            clickOptionItemBlock?.invoke(it.showContent)
        }
    }

    fun showOptionDialog() {
        commonSelectDialog.show()
    }


    fun setOptionShowList(data: List<OptionShowItem>?) {
        if (data.isNullOrEmpty()) return
        commonSelectDialog.setOptionShowList(selectTitle, data)
    }

    private fun initAttribute(attrs: AttributeSet?) {
        val t = context.obtainStyledAttributes(attrs, R.styleable.CommonSelectView)
        selectType = t.getInt(R.styleable.CommonSelectView_select_type, 0)
        selectTitle = t.getString(R.styleable.CommonSelectView_select_title) ?: ""
        t.recycle()
    }
}