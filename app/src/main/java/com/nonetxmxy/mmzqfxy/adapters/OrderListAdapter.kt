package com.nonetxmxy.mmzqfxy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.databinding.ItemOrderListBinding
import com.nonetxmxy.mmzqfxy.databinding.ItemOrderListEmptyBinding
import com.nonetxmxy.mmzqfxy.model.SampleOrder
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import kotlin.math.abs

class OrderListAdapter(private val goPro: () -> Unit, val goRepay: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var orders: List<SampleOrder> = emptyList()
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
        fun bindView(sampleOrder: SampleOrder) {
            binding.tvName.text = sampleOrder.name
            binding.tvOrderNumber.text = "订单号${sampleOrder.orderNum}"
            binding.tvAmount.text = "$${sampleOrder.loanAmount}"
            binding.tvPeriod.text = "${sampleOrder.periods}"
            binding.tvCycle.text = "${sampleOrder.cycle}"
            binding.tvDate.text = sampleOrder.date

            when (sampleOrder.orderStatus) {
                0 -> {
                    binding.tvOverOrNot.text = Utils.getApp().getString(R.string.on_reviewing)
                    binding.tvOrderNumber.visibility = View.GONE
                    binding.containerOrderListItem.setBackgroundResource(R.drawable.white_16)
                    binding.tvOverOrNot.setTextColor(
                        Utils.getApp().getColor(R.color.color_00b938)
                    )
                    binding.containerCycle.setVisible(true)
                    binding.tvRepay.setVisible(false)
                }
                1 -> {
                    binding.tvOverOrNot.text = Utils.getApp().getString(R.string.reviewing_pass)
                    binding.tvOrderNumber.visibility = View.GONE
                    binding.containerOrderListItem.setBackgroundResource(R.drawable.white_16)
                    binding.tvOverOrNot.setTextColor(
                        Utils.getApp().getColor(R.color.color_00b938)
                    )
                    binding.containerCycle.setVisible(true)
                    binding.tvRepay.setVisible(false)
                }
                2 -> {
                    binding.tvOverOrNot.text = Utils.getApp().getString(R.string.refused)
                    binding.tvOrderNumber.visibility = View.GONE
                    binding.containerOrderListItem.setBackgroundResource(R.drawable.white_16)
                    binding.tvOverOrNot.setTextColor(
                        Utils.getApp().getColor(R.color.red_f06047)
                    )
                    binding.containerCycle.setVisible(true)
                    binding.tvRepay.setVisible(false)
                }
                else -> {
                    binding.tvOrderNumber.setVisible(true)
                    binding.containerCycle.setVisible(false)
                    binding.tvRepay.setVisible(true)

                    if (sampleOrder.days >= 0) {
                        binding.tvOverOrNot.visibility = View.GONE
                        binding.containerOrderListItem.setBackgroundResource(R.drawable.white_16)
                    } else {
                        binding.tvOverOrNot.text = String.format(
                            Utils.getApp().getString(R.string.overdue_days),
                            abs(sampleOrder.days)
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
            val itemBinding = ItemOrderListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            itemBinding.tvRepay.setLimitClickListener {
                goRepay.invoke()
            }
            OrderViewHolder(itemBinding)
        }

    override fun getItemCount(): Int = if (orders.isEmpty()) 1 else orders.size

    override fun getItemViewType(position: Int): Int =
        if (orders.isEmpty()) EMPTY_VIEW_HOLDER else ORDER_ITEM

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmptyViewHolder) {
            holder.bindView()
        } else if (holder is OrderViewHolder) {
            val currentItem = orders[position]
            holder.bindView(currentItem)
        }

    }
}