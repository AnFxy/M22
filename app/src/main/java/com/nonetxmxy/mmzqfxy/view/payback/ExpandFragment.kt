package com.nonetxmxy.mmzqfxy.view.payback

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.PayWayAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.base.callBackDataWhenDestroyed
import com.nonetxmxy.mmzqfxy.databinding.FragmentExpandBinding
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
import com.nonetxmxy.mmzqfxy.tools.days
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.view.auth.UnderReviewFragment
import com.nonetxmxy.mmzqfxy.viewmodel.ExpandFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpandFragment : BaseFragment<FragmentExpandBinding, ExpandFragViewModel>() {

    private val viewModel: ExpandFragViewModel by viewModels()
    private val args: ExpandFragmentArgs by navArgs()

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

    private val payWayAdapter: PayWayAdapter by lazy {
        PayWayAdapter().apply {
            onItemSelected = { code, name, logo ->
                navController.navigate(
                    ExpandFragmentDirections.actionExpandFragmentToPayCodeFragment(
                        payWayCode = code,
                        payType = 2,
                        orderMainId = args.sonOrder.kcUBu,
                        orderSonId = args.sonOrder.eJwh,
                        payWayName = name,
                        proName = args.sonOrder.IAtgc,
                        payWayLogo = logo
                    )
                )
                args.sonOrder.hashCode()
                payWayDialog?.dismiss()
            }
        }
    }

    override fun getViewMode(): ExpandFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentExpandBinding =
        FragmentExpandBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        binding.rvPayWay.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = payWayAdapter
        }

        try {
            binding.tvExpandDays.text = args.sonOrder.nMq.days()
            binding.tvExpandFee.text = args.sonOrder.sZOWLJOMQ.jinE()
            binding.tvNeedPay.text = args.sonOrder.ccnN.jinE()
            binding.tvPayDate.text = CommonUtil.timeLongToDate(args.sonOrder.PHBPS.toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.tvExpandConfirm.apply {
            isEnabled = args.sonOrder.fxhOFanoPe == 1
            setLimitClickListener {
                viewModel.doConfirmExpanded(args.sonOrder.eJwh)
            }
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.payChannel.collect {
                payWayAdapter.payWays = it
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.closeAndBackPage.collect {
                callBackDataWhenDestroyed(UnderReviewFragment.BACK, true)
            }
        }
    }
}
