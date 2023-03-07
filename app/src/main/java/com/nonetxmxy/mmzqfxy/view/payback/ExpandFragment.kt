package com.nonetxmxy.mmzqfxy.view.payback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentExpandBinding
import com.nonetxmxy.mmzqfxy.viewmodel.ExpandFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExpandFragment : BaseFragment<FragmentExpandBinding, ExpandFragViewModel>() {

    private val viewModel : ExpandFragViewModel by viewModels()

    override fun getViewMode(): ExpandFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentExpandBinding =
        FragmentExpandBinding.inflate(inflater, parent, false)

    override fun setLayout() {

    }
}
