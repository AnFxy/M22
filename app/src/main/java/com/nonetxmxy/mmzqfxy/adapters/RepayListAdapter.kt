package com.nonetxmxy.mmzqfxy.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.StringUtils.getString
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.ItemPayAgainBinding
import com.nonetxmxy.mmzqfxy.databinding.ItemPayOrderBinding
import com.nonetxmxy.mmzqfxy.databinding.ItemPayStatusBinding
import com.nonetxmxy.mmzqfxy.model.OrderPeriodMessage
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import kotlin.math.abs

class RepayListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var datas: List<OrderPeriodMessage> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var goRequestAgain: () -> Unit = {}

    var toRepay: ((Long) -> Unit)? = null

    var toExpand: ((OrderPeriodMessage) -> Unit)? = null

    var isCanRequestAgain = false

    var onItemCheckChanged: () -> Unit = {}

    companion object {
        val STATUS = 1
        val REQUEST_AGAIN = 2
        val ORDER = 3
    }

    class StatusViewHolder(private val binding: ItemPayStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(order: OrderPeriodMessage) {
            binding.tvOrderNumber.text = "orden ${order.kcUBu}"
            when (order.tTeY) {
                0, 1, 2, 3 -> {
                    binding.ivStatus.setImageResource(R.mipmap.shenhezhong)
                    binding.tvStatusText.text = getString(R.string.on_reviewing)
                    binding.tvStatusText.setTextColor(Color.parseColor("#FFA700"))
                }
                4 -> {
                    binding.ivStatus.setImageResource(R.mipmap.shenqing)
                    binding.tvStatusText.text = getString(R.string.reviewing_pass)
                    binding.tvStatusText.setTextColor(Color.parseColor("#00B938"))
                }
                6 -> {
                    binding.ivStatus.setImageResource(R.mipmap.shenhebutongguo)
                    binding.tvStatusText.text = getString(R.string.refused)
                    binding.tvStatusText.setTextColor(Color.parseColor("#F06047"))
                }
            }

            binding.tvLoanAmount.text = (order.cyJ ?: 0.0).jinE()
            binding.tvLoanDate.text = CommonUtil.timeLongToDate(order.eQEBh?.toLong() ?: 0)
        }
    }

    class AgainViewHolder(private val binding: ItemPayAgainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(
            order: OrderPeriodMessage,
            goRequestAgain: () -> Unit,
            isCanRequestAgain: Boolean
        ) {
            LocalCache.currentProCode = order.BANcfnGeXv ?: ""
            binding.tvRequestAgainText.setLimitClickListener {
                goRequestAgain.invoke()
            }
            binding.tvRequestAgainText.isEnabled = isCanRequestAgain
        }
    }

    class OrderViewHolder(private val binding: ItemPayOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(
            order: OrderPeriodMessage,
            toRepay: ((Long) -> Unit)? = null,
            toExpand: ((OrderPeriodMessage) -> Unit)? = null,
            onItemCheckedChanged: () -> Unit
        ) {
            binding.tvPeriod.text = "Periodo ${order.PXsQKJs}"
            binding.tvOrder.text = "Orden ${order.eJwh ?: 0}"

            if ((order.fLk ?: 0) >= 0) {
                binding.tvStatusDays.apply {
                    text = if ((order.fLk ?: 0) > 0)
                        String.format(getString(R.string.remain_days), order.fLk)
                    else
                        "Vence hoy"
                    setTextColor(Color.parseColor("#FFA700"))
                }
                binding.containerOrderItem.setBackgroundResource(R.drawable.white_16)
            } else {
                binding.tvStatusDays.apply {
                    text = String.format(
                        getString(R.string.remain_days_over),
                        abs(order.fLk ?: 0)
                    )
                    setTextColor(Color.parseColor("#F06047"))
                }
                binding.containerOrderItem.setBackgroundResource(R.drawable.red_border_16)
            }

            binding.tvBorrowedAmount.text = (order.jgCtnsRuLhN ?: 0.0).jinE()
            binding.tvHandleAmount.text = (order.WJgjPYFKSKZ ?: 0.0).jinE()
            binding.tvLoanDate.text = CommonUtil.timeLongToDate(order.eQEBh?.toLong() ?: 0)
            binding.tvOverFee.text = (order.VHXe ?: 0.0).jinE()
            binding.tvReduce.text = String.format(
                getString(R.string.reduce_money),
                (order.tcwHIg ?: 0.0).jinE()
            )

            // 控件的可见性
            binding.tvReduce.setVisible((order.tcwHIg ?: 0.0) > 0)
            binding.containerOverFee.setVisible((order.fLk ?: 0) < 0)
            binding.tvPay.setVisible(order.HAkD == 1)
            binding.tvExpand.setVisible(order.BHWg == 1)

            binding.tvPay.setLimitClickListener {
                toRepay?.invoke(order.eJwh ?: 0)
            }
            binding.tvExpand.setLimitClickListener {
                toExpand?.invoke(order)
            }

            binding.cbSelect.isChecked = order.isChecked

            // 1. 选中一个 判断是否所有的已经全部选中，如果全部选中，那么更新全选按钮
            // 2. 底部是否可点击取决于是否全选了或者只有一个且可以单独还款
            binding.cbSelect.setOnClickListener {
                order.isChecked = binding.cbSelect.isChecked
                onItemCheckedChanged.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            STATUS -> StatusViewHolder(
                ItemPayStatusBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            REQUEST_AGAIN -> AgainViewHolder(
                ItemPayAgainBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> OrderViewHolder(
                ItemPayOrderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemCount(): Int = datas.size

    override fun getItemViewType(position: Int): Int =
        when (datas[position].CZusa) {
            1 -> REQUEST_AGAIN
            0, 2 -> ORDER
            else -> STATUS
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = datas[position]
        if (holder is StatusViewHolder) {
            holder.bindView(currentItem)
        } else if (holder is AgainViewHolder) {
            holder.bindView(currentItem, goRequestAgain, isCanRequestAgain)
        } else if (holder is OrderViewHolder) {
            holder.bindView(currentItem, toRepay, toExpand, onItemCheckChanged)
        }
    }
}