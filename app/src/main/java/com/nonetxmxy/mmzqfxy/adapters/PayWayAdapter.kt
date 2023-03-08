package com.nonetxmxy.mmzqfxy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ItemPayWayBinding
import com.nonetxmxy.mmzqfxy.model.SamplePayWay
import com.nonetxmxy.mmzqfxy.tools.CopyUtil
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible

class PayWayAdapter(private val payWays: List<SamplePayWay>) :
    RecyclerView.Adapter<PayWayAdapter.PayWayViewHolder>() {

    class PayWayViewHolder(private val binding: ItemPayWayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(payWay: SamplePayWay) {
            binding.tvTag.apply {
                text = if (payWay.isRecommend)
                    Utils.getApp().getString(R.string.pay_tag_recommend)
                else
                    Utils.getApp().getString(R.string.pay_tag_common)
                setTextColor(
                    if (payWay.isRecommend)
                        Utils.getApp().getColor(R.color.color_ffa700)
                    else
                        Utils.getApp().getColor(R.color.gray_999999)
                )
            }
            binding.tvPayCode.text = payWay.repayCode
            binding.containerPayCode.setVisible(payWay.repayCode.isNotEmpty())

            binding.tvCopy.setLimitClickListener {
                CopyUtil.copyToClipboard(payWay.repayCode)
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
        holder.bindView(payWays[position])
}
