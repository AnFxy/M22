package com.nonetxmxy.mmzqfxy.view.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.nonetxmxy.mmzqfxy.MainActivity
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.adapters.AuthPageDataSelectAdapter
import com.nonetxmxy.mmzqfxy.adapters.GridLayoutManagerItemDecoration
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthUserWorkBinding
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.PageType
import com.nonetxmxy.mmzqfxy.model.auth.WorkMessage
import com.nonetxmxy.mmzqfxy.tools.PreventMultiClickListener
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserWorkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ATUserWorkFragment : BaseFragment<FragmentAuthUserWorkBinding, AuthUserWorkViewModel>() {

    private val viewModel: AuthUserWorkViewModel by viewModels()

    private val args: ATUserWorkFragmentArgs by navArgs()

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

            viewModel.pagerData = viewModel.pagerData.copy(
                CVZLaIndZG = data.TuJpAVA, VOpseRY = data.cnTVzVSsBYV
            )
        }
        adapter
    }

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isHiddenStatus = true
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentAuthUserWorkBinding.inflate(
        inflater, parent, false
    )

    override fun FragmentAuthUserWorkBinding.setLayout() {
        mToolbar.setNavigationOnClickListener(object : PreventMultiClickListener() {
            override fun onSafeClick() {
                activity?.onBackPressed()
            }
        })
        mToolbar.setNavigationIcon(R.mipmap.fanhui)

        includeAuthTitle.image.setImageResource(R.mipmap.jinbi1)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.addItemDecoration(GridLayoutManagerItemDecoration(38f))
        recyclerView.adapter = sourceIncomeAdapter

        activity?.let {
            (it as MainActivity).specialOnBackPressed = {
                if (LocalCache.fourAuth()) {
                    navController.popBackStack()
                } else {
                    authDialogSet?.show()
                }
            }
        }

        initListener()
    }

    private fun initListener() {
        binding.mRefresh.setOnRefreshListener {
            viewModel.getPageData()
        }

        binding.includeAuthBottom.enviarBtn.setOnClickListener {
            if (checkData()) {
                viewModel.submitWorkInfo()
            }
        }

        binding.commonSelect1.clickOptionItemBlock = {
            viewModel.pagerData = viewModel.pagerData.copy(
                rFAso = it.TuJpAVA, zuIMxSgT = it.cnTVzVSsBYV
            )
        }
        binding.commonSelect2.clickOptionItemBlock = {
            viewModel.pagerData = viewModel.pagerData.copy(
                ODZzYj = it.TuJpAVA, FVGLFJc = it.cnTVzVSsBYV
            )
        }

        binding.commonSelect4.specailActivity = activity

        binding.commonSelect3.clickOptionItemBlock = {
            viewModel.pagerData = viewModel.pagerData.copy(
                UpoeXeBjwVB = it.TuJpAVA, iueGnrcsZ = it.cnTVzVSsBYV
            )
        }
        binding.commonSelect4.dateSelectOkBlock = {
            viewModel.pagerData = viewModel.pagerData.copy(
                XHIcmEoky = it
            )
        }

        binding.commonSelect5.addressSelectOKBlock = { province: String, city: String ->
            viewModel.pagerData = viewModel.pagerData.copy(
                tLxEVr = province, Yrfwo = city
            )
        }
    }

    private fun checkData(): Boolean {

        viewModel.pagerData = viewModel.pagerData.copy(
            TqqZwacSZ = binding.input1.editValue,
            WLQ = binding.input2.editValue,
            rIIWYi = binding.input3.editValue,
            PrKpqCuxQ = binding.inpu4.editValue,
        )

        if (viewModel.pagerData.rFAso.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.commonSelect1.selectTitle
                )
            )
            return false
        }
        if (viewModel.pagerData.CVZLaIndZG.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    StringUtils.getString(R.string.fuente_de_ingresos)
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.recyclerView.top)
            return false
        }

        if (viewModel.pagerData.TqqZwacSZ.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint),
                    "Salario"
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.input1.top)
            return false
        }

        if (viewModel.pagerData.ODZzYj.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    "Frecuencia de pago de salarios"
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.commonSelect2.top)
            return false
        }

        if (viewModel.pagerData.UpoeXeBjwVB.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    "Fecha de pago del salario"
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.commonSelect3.top)
            return false
        }

        if (viewModel.pagerData.XHIcmEoky.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    "Hora de entrada al trabajo"
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.commonSelect4.top)
            return false
        }

        if (viewModel.pagerData.WLQ.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint),
                    "Nombre de la empresa"
                )
            )
            return false
        }

        if (viewModel.pagerData.tLxEVr.isEmpty() || viewModel.pagerData.Yrfwo.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    "Dirección de la empresa"
                )
            )
            return false
        }

        if (viewModel.pagerData.rIIWYi.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.input_error_hint),
                    "Calle detallada (** Comunidad ** Calle ** Número)"
                )
            )
            return false
        }
        return true
    }

    override fun FragmentAuthUserWorkBinding.setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.optionShowListFlow.collect {
                if (it == null) return@collect
                commonSelect1.setOptionShowList(it.sCdzUti)
                commonSelect2.setOptionShowList(it.pVevX)
                commonSelect3.setOptionShowList(it.BlrvIUr)
                sourceIncomeAdapter.setList(it.GYUgF)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.administrativeListFlow.collect {
                if (it == null) return@collect
                commonSelect5.setAdministrativeList(it)
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
                    PageType.USER -> navController.navigate(ATUserWorkFragmentDirections.actionAuthUserWorkFragmentToAuthUserInfoFragment())
                    PageType.WORK -> {}
                    PageType.CONTRACT -> navController.navigate(ATUserWorkFragmentDirections.actionAuthUserWorkFragmentToAuthContactPersonFragment())
                    PageType.ID -> navController.navigate(ATUserWorkFragmentDirections.actionAuthUserWorkFragmentToAuthIdentityFragment())
                    PageType.BANK -> navController.navigate(ATUserWorkFragmentDirections.actionAuthUserWorkFragmentToAddCardsFragment())
                    PageType.FACE -> navController.navigate(ATUserWorkFragmentDirections.actionAuthUserWorkFragmentToAuthIdentityFragment())
                    PageType.CONFIRM -> navController.navigate(ATUserWorkFragmentDirections.actionAuthUserWorkFragmentToConfirmRequestFragment())
                }
            }
        }
    }

    private fun updatePage(data: WorkMessage) {

        binding.commonSelect1.selectContent = viewModel.pagerData.zuIMxSgT

        val index = viewModel.optionShowListFlow.value?.GYUgF?.map { map ->
            map.cnTVzVSsBYV
        }?.indexOf(data.VOpseRY) ?: return
        val oldIndex = sourceIncomeAdapter.currentIndex
        sourceIncomeAdapter.currentIndex = index
        sourceIncomeAdapter.notifyItemChanged(oldIndex)
        sourceIncomeAdapter.notifyItemChanged(index)

        binding.input1.inputContent = viewModel.pagerData.TqqZwacSZ
        binding.commonSelect2.selectContent = viewModel.pagerData.FVGLFJc
        binding.commonSelect3.selectContent = viewModel.pagerData.iueGnrcsZ
        binding.commonSelect4.selectContent = viewModel.pagerData.XHIcmEoky
        binding.input2.inputContent = viewModel.pagerData.WLQ
        if (data.tLxEVr.isNotEmpty() || data.Yrfwo.isNotEmpty()) {
            binding.commonSelect5.selectContent =
                "${viewModel.pagerData.tLxEVr}/${viewModel.pagerData.Yrfwo}"
        }
        binding.input3.inputContent = viewModel.pagerData.rIIWYi
    }
}