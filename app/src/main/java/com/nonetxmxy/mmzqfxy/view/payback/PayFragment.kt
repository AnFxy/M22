package com.nonetxmxy.mmzqfxy.view.payback

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.MainActivityViewModel
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.PayWayAdapter
import com.nonetxmxy.mmzqfxy.adapters.RepayListAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.FragmentPayBinding
import com.nonetxmxy.mmzqfxy.model.RepayMessage
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.PayFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PayFragment : BaseFragment<FragmentPayBinding, PayFragViewModel>() {

    private val viewModel: PayFragViewModel by viewModels()

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    private val repayAdapter: RepayListAdapter by lazy {
        RepayListAdapter().apply {
            onItemCheckChanged = {
                updateAllSelectViewStatus()
            }
            goRequestAgain = {
                navController.navigate(PayFragmentDirections.actionPayFragmentToConfirmRequestFragment())
            }
            toRepay = { sonId ->
                viewModel.sonId = sonId
                viewModel.payType = 1
                payWayDialog?.show()
                viewModel.selectCode()
            }
            toExpand = { order ->
                viewModel.sonId = order.eJwh
                viewModel.payType = 2
                navController.navigate(PayFragmentDirections.actionPayFragmentToExpandFragment(order))
            }
        }
    }

    private val payWayAdapter: PayWayAdapter by lazy {
        PayWayAdapter().apply {
            onItemSelected = { code, name, logo ->
                navController.navigate(
                    PayFragmentDirections.actionPayFragmentToPayCodeFragment(
                        payWayCode = code,
                        payType = viewModel.payType,
                        orderMainId = viewModel.pagerData.value?.oqiuffK!![0].kcUBu ?: 0,
                        orderSonId = viewModel.sonId ?: 0L,
                        payWayName = name,
                        proName = viewModel.pagerData.value?.oqiuffK!![0].IAtgc,
                        payWayLogo = logo
                    )
                )
                payWayDialog?.dismiss()
            }
        }
    }

    private val payWayDialog: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_pay_way)
            dialog.setViewState<ImageView>(R.id.iv_close) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }.setViewState<RecyclerView>(R.id.rv_pay_way) {
                adapter = payWayAdapter
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

        binding.mRefresh.setOnRefreshListener {
            viewModel.getPageData()
        }

        updateAllSelectViewStatus()

        binding.cbSelectAll.setOnClickListener {
            repayAdapter.datas = repayAdapter.datas.map {
                it.apply {
                    isChecked = binding.cbSelectAll.isChecked
                }
            }
            updateAllSelectViewStatus()
        }

        binding.tvPay.setLimitClickListener {
            if (binding.cbSelectAll.isChecked) {
                viewModel.payType = 5
            } else {
                viewModel.payType = 1
                viewModel.sonId =
                    repayAdapter.datas.first { it.isChecked && it.CZusa in listOf(0, 2) }.eJwh
            }
            payWayDialog?.show()
            viewModel.selectCode()
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagerData.collect {
                it?.let {
                    // 进行数据过滤，订单异常关闭的
                    val orders = it.oqiuffK.filterNot { item -> item.tTeY == 7 && item.CZusa == 3 }
                    repayAdapter.isCanRequestAgain = it.Djb == 1
                    updatePage(
                        it.copy(
                            oqiuffK = orders
                        )
                    )
                    if (it.OEdZXUBY <= 0) {
                        navController.popBackStack()
                    }
                    updateAllSelectViewStatus()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.payChannel.collect {
                payWayAdapter.payWays = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mainViewModel.refreshPage.collect {
                if (it !in viewModel.eventsList) {
                    viewModel.getPageData()
                    viewModel.eventsList.add(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.needUpdate.collect {
                payWayAdapter.payWays = viewModel.payChannel.value
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

        val totalPayAmount = needPayCheckedItems.sumOf { it.QhNNScsTT ?: 0.0 }
        val totalReduceAmount = needPayCheckedItems.sumOf { it.tcwHIg ?: 0.0 }

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
