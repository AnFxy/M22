package com.nonetxmxy.mmzqfxy.customer_view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo.IME_ACTION_NEXT
import android.widget.FrameLayout
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ViewInputInfoBinding

class InputInfoView constructor(
    context: Context, attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private var binding = ViewInputInfoBinding.inflate(LayoutInflater.from(context), this, true)

     var inputTitle = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) binding.title.text = value
        }

    private var editHint = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) binding.editText.hint = value
        }

    val editValue
        get() = binding.editText.text.toString()


    var inputContent = ""
        set(value) {
            field = value
            if (value.isNotEmpty()) {
                binding.editText.typeface = Typeface.DEFAULT_BOLD
                binding.editText.setText(value)
            }
        }

    init {
        initAttribute(attrs)
        initListener()
    }

    private fun initListener() {

    }


    private fun initAttribute(attrs: AttributeSet?) {
        val t = context.obtainStyledAttributes(attrs, R.styleable.InputInfoView)
        inputTitle = t.getString(R.styleable.InputInfoView_enter_title) ?: ""
        editHint = t.getString(R.styleable.InputInfoView_enter_hint) ?: ""
        binding.editText.imeOptions =
            t.getInt(R.styleable.InputInfoView_android_imeOptions, IME_ACTION_NEXT)
        t.recycle()
    }
}