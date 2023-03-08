package com.nonetxmxy.mmzqfxy.view.payback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentPayBinding
import com.nonetxmxy.mmzqfxy.viewmodel.PayFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayFragment : BaseFragment<FragmentPayBinding, PayFragViewModel>() {

    private val viewModel: PayFragViewModel by viewModels()

    override fun getViewMode(): PayFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentPayBinding =
        FragmentPayBinding.inflate(inflater, parent, false)

    override fun setLayout() {

    }
}
