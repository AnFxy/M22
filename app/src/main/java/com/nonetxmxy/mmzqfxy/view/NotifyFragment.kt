package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentNotifyBinding
import com.nonetxmxy.mmzqfxy.viewmodel.NotifyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotifyFragment : BaseFragment<FragmentNotifyBinding, NotifyViewModel>() {

    private val viewModel: NotifyViewModel by viewModels()

    override fun getViewMode(): NotifyViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentNotifyBinding =
        FragmentNotifyBinding.inflate(inflater, parent, false)
}