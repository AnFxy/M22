package com.nonetxmxy.mmzqfxy.view.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentSmsBinding
import com.nonetxmxy.mmzqfxy.viewmodel.SMSFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SMSFragment : BaseFragment<FragmentSmsBinding, SMSFragViewModel>() {

    private val viewModel: SMSFragViewModel by viewModels()

    override fun getViewMode(): SMSFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentSmsBinding =
        FragmentSmsBinding.inflate(inflater, parent, false)

}