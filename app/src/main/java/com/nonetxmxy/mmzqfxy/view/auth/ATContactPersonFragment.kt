package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.MainActivity
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthContactPersonBinding
import com.nonetxmxy.mmzqfxy.model.AuthPagerEvent
import com.nonetxmxy.mmzqfxy.model.PageType
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
import com.nonetxmxy.mmzqfxy.tools.ContactPersonUtil
import com.nonetxmxy.mmzqfxy.tools.PreventMultiClickListener
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.AuthContactPersonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ATContactPersonFragment :
    BaseFragment<FragmentAuthContactPersonBinding, AuthContactPersonViewModel>() {

    private val contactSelectResult =
        registerForActivityResult(ActivityResultContracts.PickContact()) {
            it?.let {
                val contactInfo = ContactPersonUtil.getOneContactData(Utils.getApp(), it)
                val phone = contactInfo[ContactPersonUtil.KEY_PHONE]
                val name = contactInfo[ContactPersonUtil.KEY_CONTACT]
                if (!phone.isNullOrEmpty() && !name.isNullOrEmpty() &&
                    CommonUtil.checkPhone(phone) &&
                    phone != LocalCache.phoneNumber &&
                    phone != "+${LocalCache.phoneNumber}" &&
                    phone != LocalCache.phoneNumber.substring(2)
                ) {
                    viewModel.updateContactData(selectContact1, phone, name)
                } else {
                    ToastUtils.showShort("Por favor, rellene el número de teléfono correcto.")
                }
            }
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

    private val viewModel: AuthContactPersonViewModel by viewModels()

    private val args: ATContactPersonFragmentArgs by navArgs()

    private var selectContact1 = true

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater, parent: ViewGroup?
    ) = FragmentAuthContactPersonBinding.inflate(
        inflater, parent, false
    )

    override fun FragmentAuthContactPersonBinding.setLayout() {
        mToolbar.setNavigationOnClickListener(object : PreventMultiClickListener() {
            override fun onSafeClick() {
                activity?.onBackPressed()
            }
        })
        mToolbar.setNavigationIcon(R.mipmap.fanhui)
        includeAuthTitle.image.setImageResource(R.mipmap.jinbi3)

        activity?.let {
            (it as MainActivity).specialOnBackPressed = {
                if (LocalCache.fourAuth()) {
                    navController.popBackStack()
                } else {
                    authDialogSet?.show()
                }
            }
        }

        contact1Phone.contactPersonOKBlock = {
            selectContact1 = true
            openAddressBook()
        }
        contact2Phone.contactPersonOKBlock = {
            selectContact1 = false
            openAddressBook()
        }

        contact1Relacion.clickOptionItemBlock = {
            viewModel.updateContactRelationShip(
                isFirst = true,
                relationShipId = it.TuJpAVA,
                relationShip = it.cnTVzVSsBYV
            )
        }

        contact2Relacion.clickOptionItemBlock = {
            viewModel.updateContactRelationShip(
                isFirst = false,
                relationShipId = it.TuJpAVA,
                relationShip = it.cnTVzVSsBYV
            )
        }

        binding.includeAuthBottom.enviarBtn.setLimitClickListener {
            if (checkData()) {
                viewModel.submitContractPerson()
            }
        }

        binding.mRefresh.setOnRefreshListener {
            viewModel.getPageData()
        }
    }

    private fun openAddressBook() {
        PermissionUtils.permission(PermissionConstants.CONTACTS)
            .callback(object : PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    contactSelectResult.launch(null)
                }

                override fun onDenied(
                    deniedForever: MutableList<String>,
                    denied: MutableList<String>
                ) {
                    ToastUtils.showShort(StringUtils.getString(R.string.permission_hint))
                    if (deniedForever.isNotEmpty()) {
                        PermissionUtils.launchAppDetailsSettings()
                    }
                }
            }).request()
    }

    override fun FragmentAuthContactPersonBinding.setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.optionShowListFlow.collect {
                if (it == null) return@collect
                contact1Relacion.setOptionShowList(it.zHezuLsDK)
                contact2Relacion.setOptionShowList(it.zHezuLsDK)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerDataFlow.collect {
                contact1Phone.selectContent = it.RHaDS
                contact1Name.selectContent = it.KiVk
                contact1Relacion.selectContent = it.tvNbOHA
                contact2Phone.selectContent = it.faVW
                contact2Name.selectContent = it.vwuan
                contact2Relacion.selectContent = it.gSNfDy
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.pagerEventFlow.collect {
                when (it) {
                    AuthPagerEvent.Finish -> navController.popBackStack()
                    AuthPagerEvent.GoNextPage ->  {
                        if (args.isJustBack) navController.popBackStack() else viewModel.checkGoWhichVerificationPage()
                    }
                    else -> {
                        // Do nothing
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel._baseGoPage.collect {
                when (it) {
                    PageType.USER -> navController.navigate(ATContactPersonFragmentDirections.actionAuthContactPersonFragmentToAuthUserInfoFragment())
                    PageType.WORK -> navController.navigate(ATContactPersonFragmentDirections.actionAuthContactPersonFragmentToAuthUserWorkFragment())
                    PageType.CONTRACT -> {}
                    PageType.ID -> navController.navigate(ATContactPersonFragmentDirections.actionAuthContactPersonFragmentToAuthIdentityFragment())
                    PageType.BANK -> navController.navigate(ATContactPersonFragmentDirections.actionAuthContactPersonFragmentToAddCardsFragment())
                    PageType.FACE -> navController.navigate(ATContactPersonFragmentDirections.actionAuthContactPersonFragmentToAuthIdentityFragment())
                    PageType.CONFIRM -> navController.navigate(ATContactPersonFragmentDirections.actionAuthContactPersonFragmentToConfirmRequestFragment())
                }
            }
        }
    }

    private fun checkData(): Boolean {
        val currentData = viewModel.pagerDataFlow.value
        if (currentData.Cqr.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.contact1Relacion.selectTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.contact1Relacion.top)
            return false
        }

        if (currentData.RHaDS.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.contact1Phone.selectTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.contact1Phone.top)
            return false
        }

        if (currentData.KiVk.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.contact1Name.selectTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.contact1Name.top)
            return false
        }

        // ------------------------------------------------------------------------

        if (currentData.pRgj.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.contact2Relacion.selectTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.contact2Relacion.top)
            return false
        }

        if (currentData.faVW.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.contact2Phone.selectTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.contact2Phone.top)
            return false
        }

        if (currentData.vwuan.isEmpty()) {
            ToastUtils.showShort(
                StringUtils.format(
                    StringUtils.getString(R.string.selector_error_hint),
                    binding.contact2Name.selectTitle
                )
            )
            binding.scrollView.smoothScrollTo(0, binding.contact2Name.top)
            return false
        }

        return true
    }
}