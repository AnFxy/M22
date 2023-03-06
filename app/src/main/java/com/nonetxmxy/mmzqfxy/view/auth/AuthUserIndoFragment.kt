package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.GridLayoutManagerItemDecoration
import com.nonetxmxy.mmzqfxy.adapters.SourceIncomeAdapter
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthUserInfoBinding
import com.nonetxmxy.mmzqfxy.model.SelfData
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserIndoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthUserIndoFragment : BaseFragment<FragmentAuthUserInfoBinding, AuthUserIndoViewModel>() {

    private val viewModel: AuthUserIndoViewModel by viewModels()

    private val sourceIncomeAdapter by lazy {
        val adapter = SourceIncomeAdapter()
        adapter.setOnItemClickListener { _, _, position ->
            if (position == adapter.currentIndex || position >= adapter.data.size) return@setOnItemClickListener
            val data = adapter.data[position]

            val oldIndex = adapter.currentIndex
            adapter.currentIndex = position
            adapter.notifyItemChanged(oldIndex)
            adapter.notifyItemChanged(position)

            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
                marryStatus = data.dataValue, marryStatusShow = data.showContent
            )
        }
        adapter
    }

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater, parent: ViewGroup?
    ) = FragmentAuthUserInfoBinding.inflate(
        inflater, parent, false
    )

    override fun FragmentAuthUserInfoBinding.setLayout() {

        toolBar.setupWithNavController(navController)
        toolBar.setNavigationIcon(R.mipmap.fanhui)

        includeAuthTitle.image.setImageResource(R.mipmap.jinbi1)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.addItemDecoration(GridLayoutManagerItemDecoration(38f))
        recyclerView.adapter = sourceIncomeAdapter

        includeAuthBottom.enviarBtn.setOnClickListener {
            if (checkData()) {
                viewModel.submitInfo()
            }
        }
        initListener()
    }

    private fun initListener() {
        binding.commonSelect1.clickOptionItemBlock = {
            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
                educationLevel = it.dataValue, educationLevelShow = it.showContent
            )
            checkData()
        }
        binding.commonSelect2.clickOptionItemBlock = {
            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
                childrenTotal = it.dataValue, childrenTotalShow = it.showContent
            )
            checkData()
        }

        binding.commonSelect3.addressSelectOKBlock = { province: String, city: String ->
            viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
                familyProvince = province, familyCity = city
            )
            checkData()
        }
    }

    private fun checkData(): Boolean {
        viewModel.pagerDataFlow = viewModel.pagerDataFlow.copy(
            familyAddress = binding.input1.editValue
        )
        if (viewModel.pagerDataFlow.educationLevel.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect1.selectTitle
                )
            )
            binding.commonSelect1.showOptionDialog()
            return false
        }
        if (viewModel.pagerDataFlow.marryStatus.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect1.selectTitle
                )
            )
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    StringUtils.getString(R.string.estado_civil)
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.recyclerView.top)
            return false
        }
        if (viewModel.pagerDataFlow.childrenTotal.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect2.selectTitle
                )
            )
            binding.commonSelect2.showOptionDialog()
            return false
        }
        if (viewModel.pagerDataFlow.familyProvince.isEmpty() && viewModel.pagerDataFlow.familyCity.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect3.selectTitle
                )
            )
            binding.commonSelect3.showAddressSelectDialog()
            return false
        }

        if (viewModel.pagerDataFlow.familyAddress.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint), binding.input1.inputTitle
                )
            )
            binding.input1.requestFocus()
            KeyboardUtils.showSoftInput(binding.input1)
            return false
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        KeyboardUtils.hideSoftInput(binding.input1)
    }

    override fun FragmentAuthUserInfoBinding.setObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.optionShowListFlow.collect {
                    if (it == null) return@collect
                    commonSelect1.setOptionShowList(it.marryStatus)
                    commonSelect2.setOptionShowList(it.marryStatus)
                    sourceIncomeAdapter.setList(it.marryStatus)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.administrativeListFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .collect {
                    if (it == null) return@collect
                    commonSelect3.setAdministrativeList(it)
                }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.pagerEventFlow.collect {
                when (it) {
                    AuthUserIndoViewModel.PagerEvent.Finsh -> {
                        navController.navigateUp()
                    }
                    AuthUserIndoViewModel.PagerEvent.GoWorkPage -> {
                        navController.navigate(AuthUserIndoFragmentDirections.actionAuthUserIndoFragmentToAuthUserWorkFragment())
                    }
                    AuthUserIndoViewModel.PagerEvent.UpdatePageView -> updatePage(viewModel.pagerDataFlow)
                }
            }
        }
    }

    //读取用户提交内容
    private fun updatePage(data: SelfData) {
        binding.apply {
            commonSelect1.selectContent = data.educationLevelShow
            commonSelect2.selectContent = data.childrenTotalShow
            if (data.familyProvince.isNotEmpty() || data.familyCity.isNotEmpty()) {
                commonSelect3.selectContent = "${data.familyProvince}/${data.familyCity}"
            }
            input1.inputContent = data.familyAddress

            val index = viewModel.optionShowListFlow.value?.marryStatus?.map { map ->
                map.showContent
            }?.indexOf(data.marryStatusShow) ?: return
            val oldIndex = sourceIncomeAdapter.currentIndex
            sourceIncomeAdapter.currentIndex = index
            sourceIncomeAdapter.notifyItemChanged(oldIndex)
            sourceIncomeAdapter.notifyItemChanged(index)
        }
    }
}