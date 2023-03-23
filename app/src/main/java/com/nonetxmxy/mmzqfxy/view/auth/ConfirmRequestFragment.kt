package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.CardsAdapter
import com.nonetxmxy.mmzqfxy.adapters.MoneyDaysAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.base.receiveCallBackDataFromLastFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentConfirmRequestBinding
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.auth.ConfirmMessage
import com.nonetxmxy.mmzqfxy.tools.days
import com.nonetxmxy.mmzqfxy.tools.jinE
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.ConfirmRequestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmRequestFragment :
    BaseFragment<FragmentConfirmRequestBinding, ConfirmRequestViewModel>() {

    private val viewModel: ConfirmRequestViewModel by viewModels()

    private val popUpMoneyAdapter: MoneyDaysAdapter = MoneyDaysAdapter()
    private val popUpDaysAdapter: MoneyDaysAdapter = MoneyDaysAdapter()

    private val bankCardAdapter: CardsAdapter by lazy {
        CardsAdapter(
            isSelectIconVisible = true,
            goAddCards = {
                navController.navigate(
                    ConfirmRequestFragmentDirections.actionConfirmRequestFragmentToAddCardsFragment(
                        true
                    )
                )
            }, onItemSelected = {
                viewModel.updateBankSelected(it)
                selectCardDialog?.dismiss()
            })
    }

    private val popUpMoneyView: PopupWindow by lazy {
        PopupWindow(
            LayoutInflater.from(context).inflate(R.layout.pop_up_money_days, binding.root, false)
                .apply {
                    findViewById<RecyclerView>(R.id.rv_money_days).apply {
                        adapter = popUpMoneyAdapter
                    }
                }
        )
    }

    private val popUpDaysView: PopupWindow by lazy {
        PopupWindow(
            LayoutInflater.from(context).inflate(R.layout.pop_up_money_days, binding.root, false)
                .apply {
                    findViewById<RecyclerView>(R.id.rv_money_days).apply {
                        adapter = popUpDaysAdapter
                    }
                }
        )
    }

    private val selectCardDialog: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_select_card)
            dialog.setViewState<ImageView>(R.id.iv_close) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }.setViewState<RecyclerView>(R.id.rv_cards) {
                adapter = bankCardAdapter
            }.setViewState<TextView>(R.id.tv_add_cards) {
                setLimitClickListener {
                    navController.navigate(
                        ConfirmRequestFragmentDirections.actionConfirmRequestFragmentToAddCardsFragment(true)
                    )
                    dialog.dismiss()
                }
            }
        }
    }

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentConfirmRequestBinding.inflate(
        inflater, parent, false
    )

    override fun setLayout() {

        receiveCallBackDataFromLastFragment<Boolean>(AddCardsFragment.GO_BACK) { _ ->
            viewModel.getPageData()
        }

        binding.ivExpand.setLimitClickListener {
            viewModel.isClose = false
            binding.ivExpand.setVisible(false)
            updatePage(viewModel.pagerData.value)
        }

        binding.tvLoanAmount.setOnClickListener {
            if (popUpMoneyView.isShowing) {
                popUpMoneyView.dismiss()
                return@setOnClickListener
            }
            val tvLoanAmountWidth = binding.tvLoanAmount.width
            popUpMoneyView.apply {
                width = tvLoanAmountWidth
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                showAsDropDown(binding.tvLoanAmount, 0, 12)
            }
            if (popUpDaysView.isShowing) {
                popUpDaysView.dismiss()
                binding.tvLoanDays.isChecked = false
            }
        }

        binding.tvLoanDays.setOnClickListener {
            if (popUpDaysView.isShowing) {
                popUpDaysView.dismiss()
                return@setOnClickListener
            }
            val tvLoanDaysWidth = binding.tvLoanDays.width
            popUpDaysView.apply {
                width = tvLoanDaysWidth
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                showAsDropDown(binding.tvLoanDays, 0, 12)
            }
            if (popUpMoneyView.isShowing) {
                popUpMoneyView.dismiss()
                binding.tvLoanAmount.isChecked = false
            }
        }

        binding.tvChange.setLimitClickListener {
            selectCardDialog?.show()
        }

        binding.enviarBtn.setLimitClickListener {
            viewModel.submitRequestInfo()
        }

        // pop up window 回调状态
        popUpMoneyAdapter.onSelected = { selectedMoney ->
            viewModel.moneySelectedName = selectedMoney
            viewModel.setDaysSelectAdapterData(isInit = false)
            popUpMoneyView.dismiss()
            binding.tvLoanAmount.isChecked = false
        }

        popUpDaysAdapter.onSelected = { selectedDay ->
            viewModel.daySelectedName = selectedDay
            viewModel.setDaysSelectAdapterData(isInit = false, selectedDay)
            popUpDaysView.dismiss()
            binding.tvLoanDays.isChecked = false
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerData.collect {
                updatePage(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerEventFlow.collect {
                when (it) {
                    AuthPagerEvent.UpdatePageView -> {}
                    AuthPagerEvent.GoNextPage -> {}
                    AuthPagerEvent.Finish -> {}
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.goVerifiPage.collect {
                navController.navigate(ConfirmRequestFragmentDirections.actionConfirmRequestFragmentToUnderReviewFragment())
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.banks.collect {
                bankCardAdapter.orders = it
            }
        }
    }

    private fun updatePage(confirmMessage: ConfirmMessage) {
        binding.tvProName.text = confirmMessage.proName
        try {
            Glide.with(this).load(confirmMessage.proLogo).into(binding.ivLogo)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.tvLoanAmount.text = confirmMessage.requestAmount.jinE()
        try {
            binding.tvLoanDays.text = confirmMessage.requestDays.toInt().days()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (viewModel.isClose) {
            binding.tvFixed1.text = getString(R.string.confirm_need_repay_amount)
            binding.tvValue1.text = confirmMessage.needPayWhenDateOver.jinE()

            binding.tvFixed2.text = getString(R.string.confirm_repay_date)
            binding.tvValue2.text = confirmMessage.payDate

        } else {
            binding.tvFixed1.text = getString(R.string.confirm_loan_amount)
            binding.tvValue1.text = confirmMessage.requestAmount.jinE()

            binding.tvFixed2.text = getString(R.string.confirm_get_money_account)
            binding.tvValue2.text = confirmMessage.handAmount.jinE()

            binding.tvFixed3.text = getString(R.string.confirm_rate_amount)
            binding.tvValue3.text = confirmMessage.rate.jinE()

            binding.tvFixed4.text = getString(R.string.confirm_repay_date)
            binding.tvValue4.text = confirmMessage.payDate

            binding.tvFixed5.text = getString(R.string.confirm_need_repay_amount)
            binding.tvValue5.text = confirmMessage.needPayWhenDateOver.jinE()
        }

        binding.container3.setVisible(!viewModel.isClose)
        binding.container4.setVisible(!viewModel.isClose)
        binding.container5.setVisible(!viewModel.isClose)

        binding.tvBankName.text = confirmMessage.bankName
        binding.tvBankNumber.text = confirmMessage.bankNumber

        // 更新popUpWindow的数据
        popUpMoneyAdapter.apply {
            datas = viewModel.moneyDatas
            currentSelectIndex = viewModel.moneyDatas.indexOf(viewModel.moneySelectedName)
        }
        popUpDaysAdapter.apply {
            datas = viewModel.daysDatas
            currentSelectIndex = viewModel.daysDatas.indexOf(viewModel.daySelectedName)
        }

        // 更新银行卡adapter的数据
        bankCardAdapter.currentSelectedIndex =
            viewModel.banks.value.indexOfFirst { it.ZlE == confirmMessage.bankId }
    }

    override fun onPause() {
        super.onPause()
        popUpMoneyView.dismiss()
        popUpDaysView.dismiss()
    }
}