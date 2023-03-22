package com.nonetxmxy.mmzqfxy.adapters

import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nonetxmxy.mmzqfxy.databinding.ItemMoneyDaysBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener

class MoneyDaysAdapter : RecyclerView.Adapter<MoneyDaysAdapter.SimpleViewHolder>() {

    var datas: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var currentSelectIndex: Int = 0
    var onSelected: (String) -> Unit = {}

    class SimpleViewHolder(private val binding: ItemMoneyDaysBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: String, isSelected: Boolean, onSelect: (String) -> Unit = {}) {
            binding.tvMoneyDays.apply {
                text = data
                typeface = if (isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                setTextColor(
                    if (isSelected)
                        Color.parseColor("#333333")
                    else
                        Color.parseColor("#666666")
                )
                setBackgroundColor(
                    if (isSelected) Color.parseColor("#F4F4F4") else Color.parseColor("#FFFFFF")
                )
                setLimitClickListener {
                    onSelect.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder =
        SimpleViewHolder(
            ItemMoneyDaysBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) =
        holder.bindView(datas[position], position == currentSelectIndex, onSelected)
}