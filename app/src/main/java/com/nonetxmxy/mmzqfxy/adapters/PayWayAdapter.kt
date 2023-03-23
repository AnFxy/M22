package com.nonetxmxy.mmzqfxy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ItemPayWayBinding
import com.nonetxmxy.mmzqfxy.model.PayWayMessage
import com.nonetxmxy.mmzqfxy.tools.CopyUtil
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible

class PayWayAdapter : RecyclerView.Adapter<PayWayAdapter.PayWayViewHolder>() {

    var payWays: List<PayWayMessage> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemSelected: ((String, String, String) -> Unit)? = null

    class PayWayViewHolder(private val binding: ItemPayWayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(payWay: PayWayMessage, onItemSelected: ((String, String, String) -> Unit)?) {
            Glide.with(binding.root).load(payWay.Nqlgew).into(binding.ivLogo)
            binding.tvTag.apply {
                text = if (payWay.KKEMXfGmlVt == 1)
                    Utils.getApp().getString(R.string.pay_tag_recommend)
                else
                    Utils.getApp().getString(R.string.pay_tag_common)
                setTextColor(
                    if (payWay.KKEMXfGmlVt == 1)
                        Utils.getApp().getColor(R.color.color_ffa700)
                    else
                        Utils.getApp().getColor(R.color.gray_999999)
                )
            }
            binding.tvPayCode.text = payWay.jBsB
            binding.containerPayCode.setVisible(payWay.jBsB.isNotEmpty())

            binding.tvCopy.setLimitClickListener {
                CopyUtil.copyToClipboard(payWay.jBsB)
            }

            binding.root.setLimitClickListener {
                onItemSelected?.invoke(payWay.jBsB, payWay.Ghtm, payWay.Nqlgew)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayWayViewHolder =
        PayWayViewHolder(
            ItemPayWayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = payWays.size

    override fun onBindViewHolder(holder: PayWayViewHolder, position: Int) =
        holder.bindView(payWays[position], onItemSelected)
}
