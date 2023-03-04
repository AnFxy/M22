package com.nonetxmxy.mmzqfxy.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.CommonSelectAdapter
import com.nonetxmxy.mmzqfxy.databinding.DiaCommonSelectBinding
import com.nonetxmxy.mmzqfxy.model.OptionShowItem

class CommonSelectDialog constructor(context: Context, themeResId: Int) :
    Dialog(context, themeResId) {

    private val binding = DiaCommonSelectBinding.inflate(layoutInflater)

    var clickItemBlock: ((OptionShowItem) -> Unit)? = null

    private val commonSelectAdapter by lazy {
        val adapter = CommonSelectAdapter()
        adapter.setOnItemClickListener { _, _, position ->
            if (position >= adapter.data.size) return@setOnItemClickListener
            val oldIndex = adapter.curIndex
            adapter.curIndex = position
            adapter.notifyItemChanged(oldIndex)
            adapter.notifyItemChanged(position)
            clickItemBlock?.invoke(adapter.data[position])
            dismiss()
        }
        adapter
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
        binding.recyclerView.adapter = commonSelectAdapter
        binding.cancelar.setOnClickListener {
            dismiss()
        }
    }

    fun setOptionShowList(title: String, data: List<OptionShowItem>) {
        binding.diaTitle.text = title
        commonSelectAdapter.setList(data)
    }
}