package com.nonetxmxy.mmzqfxy.view.payback

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.RepayListAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentPayBinding
import com.nonetxmxy.mmzqfxy.model.RepayMessage
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.PayFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PayFragment : BaseFragment<FragmentPayBinding, PayFragViewModel>() {

    private val viewModel: PayFragViewModel by viewModels()

    private val repayAdapter: RepayListAdapter by lazy {
        RepayListAdapter().apply {
            onItemCheckChanged = {
                updateAllSelectViewStatus()
            }
        }
    }

    override fun getViewMode(): PayFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentPayBinding =
        FragmentPayBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        binding.rvPay.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repayAdapter
        }

        binding.cbSelectAll.setOnClickListener {
            repayAdapter.datas = repayAdapter.datas.map {
                it.apply {
                    isChecked = binding.cbSelectAll.isChecked
                }
            }
            updateAllSelectViewStatus()
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagerData.collect {
                it?.let {
                    // 进行数据过滤，订单异常关闭的
                    val orders = it.oqiuffK.filterNot { item -> item.tTeY == 7 && item.CZusa == 3 }
                    updatePage(
                        it.copy(
                            oqiuffK = orders
                        )
                    )
                }
            }
        }
    }

    private fun updatePage(repayMessage: RepayMessage) {
        binding.tvNeedPayTotalAmount.text = repayMessage.OEdZXUBY.jinE()
        binding.tvNeedPayTotalPeroids.text = repayMessage.XYz.toString()
        if (repayMessage.oqiuffK.isEmpty()) {
            return
        }
        Glide.with(this).load(repayMessage.oqiuffK[0].eSu).into(binding.ivPro)
        binding.tvProName.text = repayMessage.oqiuffK[0].IAtgc

        repayAdapter.datas = repayMessage.oqiuffK
    }

    // 更新全选按钮以及借款按钮的状态和统计全部金额和立减金额
    @SuppressLint("SetTextI18n")
    private fun updateAllSelectViewStatus() {
        val needPayItems = repayAdapter.datas.filter { it.CZusa in listOf(0, 2) }
        val needPayCheckedItems = needPayItems.filter { it.isChecked }
        val isAllSelected = needPayCheckedItems.size == needPayItems.size
        binding.cbSelectAll.isChecked = isAllSelected
        binding.tvPay.isEnabled =
            (needPayCheckedItems.size == 1 && needPayCheckedItems[0].HAkD == 1) || isAllSelected

        val totalPayAmount = needPayCheckedItems.sumOf { it.QhNNScsTT }
        val totalReduceAmount = 12
            //needPayCheckedItems.sumOf { it.tcwHIg }

        val reduceText =
            if (totalReduceAmount > 0)
                String.format(getString(R.string.repay_reduce), totalReduceAmount.jinE())
            else
                ""

        binding.tvTotal.text = totalPayAmount.jinE()
        binding.tvTotalReduce.text = reduceText
        binding.containerTotal.setVisible(totalPayAmount > 0)
    }
}
