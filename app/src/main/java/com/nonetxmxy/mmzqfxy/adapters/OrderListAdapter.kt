package com.nonetxmxy.mmzqfxy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.ItemOrderListBinding
import com.nonetxmxy.mmzqfxy.databinding.ItemOrderListEmptyBinding
import com.nonetxmxy.mmzqfxy.model.OrderMessage
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import kotlin.math.abs

class OrderListAdapter(private val goPro: () -> Unit, val goRepay: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: List<OrderMessage> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        val EMPTY_VIEW_HOLDER = 1
        val ORDER_ITEM = 2
    }

    class OrderViewHolder(private val binding: ItemOrderListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(sampleOrder: OrderMessage, goRepay: () -> Unit) {
            binding.tvName.text = sampleOrder.qkpPjr
            binding.tvOrderNumber.text = "Orden ${sampleOrder.LjNAwGsqXs}"
            binding.tvAmount.text = sampleOrder.fHTnifvEC.jinE()
            binding.tvPeriod.text = "${sampleOrder.llYomqGFjAS}"
            binding.tvCycle.text = "${sampleOrder.nhgL}"

            binding.tvRepay.setLimitClickListener {
                goRepay.invoke()
            }

            when (sampleOrder.snbrREWru) {
                0, 1, 2, 3 -> {
                    binding.tvOverOrNot.text = Utils.getApp().getString(R.string.on_reviewing)
                    binding.tvOrderNumber.visibility = View.GONE
                    binding.containerOrderListItem.setBackgroundResource(R.drawable.white_16)
                    binding.tvOverOrNot.setTextColor(
                        Utils.getApp().getColor(R.color.color_00b938)
                    )
                    binding.containerCycle.setVisible(true)
                    binding.tvRepay.setVisible(false)

                    binding.tvDate.text = CommonUtil.timeLongToDate(sampleOrder.JuRZheDh.toLong())
                    binding.tvDateConstant.text = Utils.getApp().getString(R.string.request_date)
                }
                4 -> {
                    binding.tvOverOrNot.text = Utils.getApp().getString(R.string.reviewing_pass)
                    binding.tvOrderNumber.visibility = View.GONE
                    binding.containerOrderListItem.setBackgroundResource(R.drawable.white_16)
                    binding.tvOverOrNot.setTextColor(
                        Utils.getApp().getColor(R.color.color_00b938)
                    )
                    binding.containerCycle.setVisible(true)
                    binding.tvRepay.setVisible(false)
                    binding.tvDate.text = CommonUtil.timeLongToDate(sampleOrder.JuRZheDh.toLong())
                    binding.tvDateConstant.text = Utils.getApp().getString(R.string.request_date)
                }
                6 -> {
                    binding.tvOverOrNot.text = Utils.getApp().getString(R.string.refused)
                    binding.tvOrderNumber.visibility = View.GONE
                    binding.containerOrderListItem.setBackgroundResource(R.drawable.white_16)
                    binding.tvOverOrNot.setTextColor(
                        Utils.getApp().getColor(R.color.red_f06047)
                    )
                    binding.containerCycle.setVisible(true)
                    binding.tvRepay.setVisible(false)
                    binding.tvDate.text = CommonUtil.timeLongToDate(sampleOrder.JuRZheDh.toLong())
                    binding.tvDateConstant.text = Utils.getApp().getString(R.string.request_date)
                }
                else -> {
                    binding.tvOrderNumber.setVisible(true)
                    binding.containerCycle.setVisible(false)
                    binding.tvRepay.setVisible(true)

                    binding.tvDate.text =
                        CommonUtil.timeLongToDate(sampleOrder.sLEsJFIXyqv.toLong())
                    binding.tvDateConstant.text =
                        Utils.getApp().getString(R.string.confirm_repay_date)

                    if (sampleOrder.JMgRrdrv >= 0) {
                        binding.tvOverOrNot.visibility = View.GONE
                        binding.containerOrderListItem.setBackgroundResource(R.drawable.white_16)
                    } else {
                        binding.tvOverOrNot.text = String.format(
                            Utils.getApp().getString(R.string.overdue_days),
                            abs(sampleOrder.JMgRrdrv)
                        )
                        binding.tvOverOrNot.visibility = View.VISIBLE
                        binding.tvOverOrNot.setTextColor(
                            Utils.getApp().getColor(R.color.red_f06047)
                        )
                        binding.containerOrderListItem.setBackgroundResource(R.drawable.red_border_16)
                    }
                }
            }
        }
    }

    class EmptyViewHolder(private val binding: ItemOrderListEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView() {
            // TODO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == EMPTY_VIEW_HOLDER) {
            val emptyBinding = ItemOrderListEmptyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            emptyBinding.tvRequest.setLimitClickListener {
                goPro.invoke()
            }
            EmptyViewHolder(emptyBinding)
        } else {
            OrderViewHolder(
                ItemOrderListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemCount(): Int = if (orders.isEmpty()) 1 else orders.size

    override fun getItemViewType(position: Int): Int =
        if (orders.isEmpty()) EMPTY_VIEW_HOLDER else ORDER_ITEM

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmptyViewHolder) {
            holder.bindView()
        } else if (holder is OrderViewHolder) {
            val currentItem = orders[position]
            holder.bindView(currentItem) {
                LocalCache.currentProCode = currentItem.piKSfRNing
                goRepay.invoke()
            }
        }

    }
}