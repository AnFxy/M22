package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentProductListBinding
import com.nonetxmxy.mmzqfxy.viewmodel.ProductListFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding, ProductListFragViewModel>() {

    private val viewModel: ProductListFragViewModel by viewModels()

    override fun getViewMode(): ProductListFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentProductListBinding =
        FragmentProductListBinding.inflate(inflater, parent, false)


}
