package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.ui.setupWithNavController
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthContactPersonBinding
import com.nonetxmxy.mmzqfxy.tools.ContactPersonUtil
import com.nonetxmxy.mmzqfxy.viewmodel.AuthContactPersonViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthContactPersonFragment :
    BaseFragment<FragmentAuthContactPersonBinding, AuthContactPersonViewModel>() {

    private val contactSelectResult =
        registerForActivityResult(ActivityResultContracts.PickContact()) {
            it?.let {
                val contactInfo = ContactPersonUtil.getOneContactData(Utils.getApp(), it)
                val phone = contactInfo[ContactPersonUtil.KEY_PHONE]
                val name = contactInfo[ContactPersonUtil.KEY_CONTACT]
                if (!phone.isNullOrEmpty() && !name.isNullOrEmpty()) {
                    viewModel.updateContactData(selectContact1, phone, name)
                }
            }
        }

    private val viewModel: AuthContactPersonViewModel by viewModels()

    private var selectContact1 = true

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater, parent: ViewGroup?
    ) = FragmentAuthContactPersonBinding.inflate(
        inflater, parent, false
    )

    override fun FragmentAuthContactPersonBinding.setLayout() {
        mToolbar.setupWithNavController(navController)
        mToolbar.setNavigationIcon(R.mipmap.fanhui)

        contact1Phone.contactPersonOKBlock = {
            selectContact1 = true
            openAddressBook()
        }
        contact2Phone.contactPersonOKBlock = {
            selectContact1 = false
            openAddressBook()
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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.optionShowListFlow.collect {
                    if (it == null) return@collect
                    contact1Relacion.setOptionShowList(it.marryStatus)
                    contact2Relacion.setOptionShowList(it.marryStatus)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagerDataFlow.collect {
                    contact1Phone.selectContent = it.relationshipFirst
                    contact2Phone.selectContent = ""
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.pagerEventFlow.collect {
                when (it) {
                    AuthContactPersonViewModel.Event.SamePhone -> {
                        ToastUtils.showShort("相同手机号")
                    }
                }
            }
        }
    }
}