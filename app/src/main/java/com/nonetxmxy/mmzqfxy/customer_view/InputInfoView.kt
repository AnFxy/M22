package com.nonetxmxy.mmzqfxy.customer_view

import android.content.Context
import android.graphics.Typeface
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo.IME_ACTION_NEXT
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ViewInputInfoBinding
import java.util.regex.Pattern

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

        binding.editText.inputType =
            t.getInt(R.styleable.InputInfoView_android_inputType, InputType.TYPE_CLASS_TEXT)
        binding.editText.filters = arrayOf(object :
            InputFilter.LengthFilter(t.getInt(R.styleable.InputInfoView_android_maxLength, 40)) {})

        binding.editText.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun afterTextChanged(p0: Editable?) {
                val oldStr = p0?.toString() ?: ""
                val patternStr = "[`~!#_$%^&*()+=|{}':;'\\[\\]<>/?~！#￥%……&*（）—\\-—+=【】{}：；‘’“”。，、？]"
                val patterns = Pattern.compile(patternStr).matcher(oldStr).replaceAll("")

                if (patterns.isNotEmpty()) {
                    binding.editText.typeface = Typeface.DEFAULT_BOLD
                } else {
                    binding.editText.typeface = Typeface.DEFAULT
                }

                if (oldStr != patterns) {
                    binding.editText.setText(patterns)
                }
            }

        })

        t.recycle()
    }
}