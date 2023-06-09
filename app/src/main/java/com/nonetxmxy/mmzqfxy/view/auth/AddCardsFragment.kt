package com.nonetxmxy.mmzqfxy.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.base.callBackDataWhenDestroyed
import com.nonetxmxy.mmzqfxy.databinding.FragmentAddCardsBinding
import com.nonetxmxy.mmzqfxy.model.PageType
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.AddCardsFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCardsFragment : BaseFragment<FragmentAddCardsBinding, AddCardsFragViewModel>() {

    private val viewModel: AddCardsFragViewModel by viewModels()

    private val arguments: AddCardsFragmentArgs by navArgs()

    override fun getViewMode(): AddCardsFragViewModel = viewModel

    companion object {
        val GO_BACK = "go_back"
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentAddCardsBinding =
        FragmentAddCardsBinding.inflate(inflater, parent, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isHiddenStatus = true
    }

    override fun setLayout() {
        binding.mToolbar.setNavigationIcon(R.mipmap.fanhui)
        binding.image.setImageResource(R.mipmap.jinbi5)
        binding.tvSecurityTips.text = getString(R.string.add_bank_tips)

        binding.containerSecurity.setVisible(!arguments.isJustBack)
        binding.image.setVisible(!arguments.isJustBack)

        binding.rgBankType.setOnCheckedChangeListener { _, p1 ->
            viewModel.optionalDirection.value?.let {
                if (it.ihdXBrF.size >= 2) {
                    viewModel.pagerData = viewModel.pagerData.copy(
                        RhgBNBzglD = if (p1 == R.id.tv_card1) it.ihdXBrF[0].TuJpAVA else it.ihdXBrF[1].TuJpAVA,
                        YfpxWBMDrp = if (p1 == R.id.tv_card1) it.ihdXBrF[0].cnTVzVSsBYV else it.ihdXBrF[1].cnTVzVSsBYV
                    )
                }
            }
        }

        binding.commonSelect1.bankSelectOkBlock = {
            viewModel.pagerData = viewModel.pagerData.copy(
                TtoUz = it
            )
        }

        binding.includeAuthBottom.enviarBtn.setLimitClickListener {
            if (checkData()) {
                context?.let {
                    val dialogSet = RxDialogSet.provideDialog(it, R.layout.dia_bank_confirm)
                    dialogSet.setViewState<ImageView>(R.id.iv_close) {
                        setLimitClickListener {
                            dialogSet.dismiss()
                        }
                    }.setViewState<TextView>(R.id.tv_bank_name) {
                        text = " ${viewModel.pagerData.TtoUz}"
                    }.setViewState<TextView>(R.id.tv_bank_number) {
                        text = CommonUtil.bankFormat(viewModel.pagerData.zUbbNgrgLl)
                    }.setViewState<TextView>(R.id.tv_confirm) {
                        setLimitClickListener {
                            viewModel.submitBank()
                            dialogSet.dismiss()
                        }
                    }.show()
                }
            }
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.optionalDirection.collect {
                it?.let { allTags ->
                    val tags = allTags.ihdXBrF
                    if (tags.isNotEmpty()) {
                        binding.tvCard1.text = tags[0].cnTVzVSsBYV
                        binding.tvCard2.text = tags[1].cnTVzVSsBYV
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.banks.collect {
                binding.commonSelect1.setBanks(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.goNextPage.collect {
                if (arguments.isJustBack) {
                    callBackDataWhenDestroyed(GO_BACK, data = true)
                } else {
                    viewModel.checkGoWhichVerificationPage()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel._baseGoPage.collect {
                when (it) {
                    PageType.USER -> navController.navigate(AddCardsFragmentDirections.actionAddCardsFragmentToAuthUserInfoFragment())
                    PageType.WORK -> navController.navigate(AddCardsFragmentDirections.actionAddCardsFragmentToAuthUserWorkFragment())
                    PageType.CONTRACT -> navController.navigate(AddCardsFragmentDirections.actionAddCardsFragmentToAuthContactPersonFragment())
                    PageType.ID -> navController.navigate(AddCardsFragmentDirections.actionAddCardsFragmentToAuthIdentityFragment())
                    PageType.BANK -> {}
                    PageType.FACE -> navController.navigate(AddCardsFragmentDirections.actionAddCardsFragmentToAuthIdentityFragment())
                    PageType.CONFIRM -> navController.navigate(AddCardsFragmentDirections.actionAddCardsFragmentToConfirmRequestFragment())
                }
            }
        }
    }

    private fun checkData(): Boolean {
        viewModel.pagerData = viewModel.pagerData.copy(
            zUbbNgrgLl = binding.input1.editValue
        )

        if (viewModel.pagerData.RhgBNBzglD.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.tvBankTypeTitle.text.toString()
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.tvBankTypeTitle.top)
            return false
        }

        if (viewModel.pagerData.TtoUz.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect1.selectTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.commonSelect1.top)
            return false
        }

        if (viewModel.pagerData.zUbbNgrgLl.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint),
                    binding.input1.inputTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.input1.top)
            return false
        }

        if (viewModel.pagerData.RhgBNBzglD == "40" && viewModel.pagerData.zUbbNgrgLl.length != 18) {
            ToastUtils.showShort("Introduzca una tarjeta bancaria de 18 digitos de longitud.")
            return false
        }

        if (viewModel.pagerData.RhgBNBzglD == "3" && viewModel.pagerData.zUbbNgrgLl.length != 16) {
            ToastUtils.showShort("Introduzca una tarjeta bancaria de 16 digitos de longitud.")
            return false
        }

        return true
    }
}
