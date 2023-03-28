package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.nonetxmxy.mmzqfxy.MainActivity
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.AuthPageDataSelectAdapter
//import com.nonetxmxy.mmzqfxy.adapters.AuthPageDataSelectAdapter
import com.nonetxmxy.mmzqfxy.adapters.GridLayoutManagerItemDecoration
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthUserInfoBinding
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.PageType
import com.nonetxmxy.mmzqfxy.model.auth.UserMessage
import com.nonetxmxy.mmzqfxy.tools.PreventMultiClickListener
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
//import com.nonetxmxy.mmzqfxy.model.SelfData
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthUserInfoFragment : BaseFragment<FragmentAuthUserInfoBinding, AuthUserInfoViewModel>() {

    private val viewModel: AuthUserInfoViewModel by viewModels()

    private val args: AuthUserInfoFragmentArgs by navArgs()

    private val authDialogSet: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_auth)
            dialog.setViewState<ImageView>(R.id.iv_close) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }.setViewState<TextView>(R.id.tv_continue) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }.setViewState<TextView>(R.id.tv_abandonar) {
                setLimitClickListener {
                    navController.popBackStack()
                    dialog.dismiss()
                }
            }
            dialog
        }

    }

    private val estadoCivilAdapter by lazy {
        val adapter = AuthPageDataSelectAdapter()
        adapter.setOnItemClickListener { _, _, position ->
            if (position == adapter.currentIndex || position >= adapter.data.size) return@setOnItemClickListener
            val data = adapter.data[position]

            val oldIndex = adapter.currentIndex
            adapter.currentIndex = position
            adapter.notifyItemChanged(oldIndex)
            adapter.notifyItemChanged(position)

            viewModel.pagerData = viewModel.pagerData.copy(
                kaAT = data.TuJpAVA, XKbzBk = data.cnTVzVSsBYV
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

        mToolbar.setNavigationOnClickListener(object : PreventMultiClickListener() {
            override fun onSafeClick() {
                activity?.onBackPressed()
            }
        })
        mToolbar.setNavigationIcon(R.mipmap.fanhui)

        includeAuthTitle.image.setImageResource(R.mipmap.jinbi2)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.addItemDecoration(GridLayoutManagerItemDecoration(38f))
        recyclerView.adapter = estadoCivilAdapter

        initListener()
    }

    private fun initListener() {

        binding.mRefresh.setOnRefreshListener {
            viewModel.getPageData()
        }


        binding.includeAuthBottom.enviarBtn.setOnClickListener {
            if (checkData()) {
                viewModel.submitInfo()
            }
        }

        binding.commonSelect1.clickOptionItemBlock = {
            viewModel.pagerData = viewModel.pagerData.copy(
                vnQBj = it.TuJpAVA, xzwqCVcwQ = it.cnTVzVSsBYV
            )
        }
        binding.commonSelect2.clickOptionItemBlock = {
            viewModel.pagerData = viewModel.pagerData.copy(
                AnmkImJZjp = it.TuJpAVA, ijH = it.cnTVzVSsBYV
            )
        }
        binding.commonSelect3.addressSelectOKBlock = { province: String, city: String ->
            viewModel.pagerData = viewModel.pagerData.copy(
                qLh = province, URCcx = city
            )
        }

        activity?.let {
            (it as MainActivity).specialOnBackPressed = {
                if (LocalCache.fourAuth()) {
                    navController.popBackStack()
                } else {
                    authDialogSet?.show()
                }
            }
        }
    }

    private fun checkData(): Boolean {
        viewModel.pagerData = viewModel.pagerData.copy(
            dFZqoeahk = binding.input1.editValue
        )
        if (viewModel.pagerData.vnQBj.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect1.selectTitle
                )
            )
            return false
        }
        if (viewModel.pagerData.kaAT.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    StringUtils.getString(R.string.estado_civil)
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.recyclerView.top)
            return false
        }
        if (viewModel.pagerData.AnmkImJZjp.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect2.selectTitle
                )
            )
            return false
        }
        if (viewModel.pagerData.qLh.isEmpty() || viewModel.pagerData.URCcx.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect3.selectTitle
                )
            )
            return false
        }

        if (viewModel.pagerData.dFZqoeahk.isEmpty()) {
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.optionShowListFlow.collect {
                if (it == null) return@collect
                commonSelect1.setOptionShowList(it.PBjZodk)
                commonSelect2.setOptionShowList(it.pAf)
                estadoCivilAdapter.setList(it.JDnuGMCy)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.administrativeListFlow.collect {
                if (it == null) return@collect
                commonSelect3.setAdministrativeList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerEventFlow.collect {
                when (it) {
                    AuthPagerEvent.Finish -> navController.popBackStack()
                    AuthPagerEvent.GoNextPage -> {
                        if (args.isJustBack) navController.popBackStack() else viewModel.checkGoWhichVerificationPage()
                    }
                    AuthPagerEvent.UpdatePageView -> updatePage(viewModel.pagerData)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel._baseGoPage.collect {
                when (it) {
                    PageType.USER -> {}
                    PageType.WORK -> navController.navigate(AuthUserInfoFragmentDirections.actionAuthUserInfoFragmentToAuthUserWorkFragment())
                    PageType.CONTRACT -> navController.navigate(AuthUserInfoFragmentDirections.actionAuthUserInfoFragmentToAuthContactPersonFragment())
                    PageType.ID -> navController.navigate(AuthUserInfoFragmentDirections.actionAuthUserInfoFragmentToAuthIdentityFragment())
                    PageType.BANK -> navController.navigate(AuthUserInfoFragmentDirections.actionAuthUserInfoFragmentToAddCardsFragment())
                    PageType.FACE -> navController.navigate(AuthUserInfoFragmentDirections.actionAuthUserInfoFragmentToAuthIdentityFragment())
                    PageType.CONFIRM -> navController.navigate(AuthUserInfoFragmentDirections.actionAuthUserInfoFragmentToConfirmRequestFragment())
                }
            }
        }
    }

    //读取用户提交内容
    private fun updatePage(data: UserMessage) {
        binding.apply {
            commonSelect1.selectContent = data.xzwqCVcwQ
            commonSelect2.selectContent = data.ijH
            if (data.qLh.isNotEmpty() || data.URCcx.isNotEmpty()) {
                commonSelect3.selectContent = "${data.qLh}/${data.URCcx}"
            }
            input1.inputContent = data.dFZqoeahk

            val index = viewModel.optionShowListFlow.value?.JDnuGMCy?.map { map ->
                map.cnTVzVSsBYV
            }?.indexOf(data.XKbzBk) ?: return
            val oldIndex = estadoCivilAdapter.currentIndex
            estadoCivilAdapter.currentIndex = index
            estadoCivilAdapter.notifyItemChanged(oldIndex)
            estadoCivilAdapter.notifyItemChanged(index)
        }
    }
}