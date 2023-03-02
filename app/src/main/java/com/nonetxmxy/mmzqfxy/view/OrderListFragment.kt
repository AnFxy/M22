package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentOrderListBinding
import com.nonetxmxy.mmzqfxy.viewmodel.OrderListFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderListFragment : BaseFragment<FragmentOrderListBinding, OrderListFragViewModel>() {

    private val viewModel: OrderListFragViewModel by viewModels()

    override fun getViewMode(): OrderListFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentOrderListBinding =
        FragmentOrderListBinding.inflate(inflater, parent, false)
}
