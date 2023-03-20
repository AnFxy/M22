package com.nonetxmxy.mmzqfxy.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<V : ViewBinding> @JvmOverloads constructor(
    context: Context, themeResId: Int = 0
) : Dialog(context, themeResId) {
    private lateinit var _binding: V
    protected val binding get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(_binding.root)
        window?.let {
            it.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val lp = it.attributes
            it.attributes.gravity = Gravity.BOTTOM
            it.attributes = lp
        }
        initView()
    }

    protected abstract fun getViewBinding(): V
    abstract fun initView()
}

