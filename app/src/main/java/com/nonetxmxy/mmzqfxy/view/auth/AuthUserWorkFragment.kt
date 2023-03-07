package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.AuthPageDataSelectAdapter
import com.nonetxmxy.mmzqfxy.adapters.GridLayoutManagerItemDecoration
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthUserWorkBinding
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserWorkViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthUserWorkFragment : BaseFragment<FragmentAuthUserWorkBinding, AuthUserWorkViewModel>() {

    private val viewModel: AuthUserWorkViewModel by viewModels()

    override fun getViewMode() = viewModel

    private val sourceIncomeAdapter by lazy {
        val adapter = AuthPageDataSelectAdapter()
        adapter.setOnItemClickListener { _, _, position ->
            if (position == adapter.currentIndex || position >= adapter.data.size) return@setOnItemClickListener
            val data = adapter.data[position]

            val oldIndex = adapter.currentIndex
            adapter.currentIndex = position
            adapter.notifyItemChanged(oldIndex)
            adapter.notifyItemChanged(position)

            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
                incomeSourceType = data.dataValue, incomeSourceTypeShow = data.showContent
            )
        }
        adapter
    }


    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentAuthUserWorkBinding.inflate(
        inflater, parent, false
    )

    override fun FragmentAuthUserWorkBinding.setLayout() {
        mToolbar.setupWithNavController(navController)
        mToolbar.setNavigationIcon(R.mipmap.fanhui)

        includeAuthTitle.image.setImageResource(R.mipmap.jinbi2)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.addItemDecoration(GridLayoutManagerItemDecoration(38f))
        recyclerView.adapter = sourceIncomeAdapter

        initListener()
    }

    private fun initListener() {
        binding.includeAuthBottom.enviarBtn.setOnClickListener {
            if (checkData()) {
                viewModel.submitWorkInfo()
            }
        }

        binding.commonSelect1.clickOptionItemBlock = {
            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
                workNature = it.dataValue, workNatureShow = it.showContent
            )
            checkData()
        }
        binding.commonSelect2.clickOptionItemBlock = {
            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
//                payCycle = it.dataValue, payCycleShow = it.showContent
            )
            checkData()
        }

        binding.commonSelect3.clickOptionItemBlock = {
            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
//                payday = it.dataValue, paydayShow = it.showContent
            )
            checkData()
        }
        binding.commonSelect4.clickOptionItemBlock = {
            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
                //beginWorkYear = it.showContent
            )
            checkData()
        }

        binding.commonSelect5.addressSelectOKBlock = { province: String, city: String ->
            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
//                companyProvince = province, companyCity = city
            )
            checkData()
        }
    }


    private fun checkData(): Boolean {
        viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
            companyMonthIncomeShow = binding.input1.editValue
        )

        if (viewModel.pagerDataFlow.workNature.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect1.selectTitle
                )
            )
            binding.commonSelect1.showOptionDialog()
            return false
        }
        if (viewModel.pagerDataFlow.incomeSourceType.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    StringUtils.getString(R.string.fuente_de_ingresos)
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.recyclerView.top)
            return false
        }
//        if (viewModel.pagerDataFlow.payCycle.isEmpty()) {
//            ToastUtils.showShort(
//                StringUtils.format(
//                    StringUtils.getString(R.string.selector_error_hint),
//                    binding.commonSelect2.selectTitle
//                )
//            )
//            binding.commonSelect2.showOptionDialog()
//            return false
//        }
//        if (viewModel.pagerDataFlow.familyProvince.isEmpty() && viewModel.pagerDataFlow.familyCity.isEmpty()) {
//            ToastUtils.showShort(
//                StringUtils.format(
//                    StringUtils.getString(R.string.selector_error_hint),
//                    binding.commonSelect3.selectTitle
//                )
//            )
//            binding.commonSelect3.showAddressSelectDialog()
//            return false
//        }
//
//        if (viewModel.pagerDataFlow.familyAddress.isEmpty()) {
//            ToastUtils.showShort(
//                StringUtils.format(
//                    StringUtils.getString(R.string.input_error_hint), binding.input1.inputTitle
//                )
//            )
//            binding.input1.requestFocus()
//            KeyboardUtils.showSoftInput(binding.input1)
//            return false
//        }
        return true
    }


    override fun FragmentAuthUserWorkBinding.setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.optionShowListFlow.collect {
                    if (it == null) return@collect
                    commonSelect1.setOptionShowList(it.marryStatus)
                    commonSelect2.setOptionShowList(it.marryStatus)
                    commonSelect3.setOptionShowList(it.marryStatus)
                    commonSelect4.setOptionShowList(it.marryStatus)
                    commonSelect5.setOptionShowList(it.marryStatus)
                    sourceIncomeAdapter.setList(it.marryStatus)
                }
            }
        }
    }
}