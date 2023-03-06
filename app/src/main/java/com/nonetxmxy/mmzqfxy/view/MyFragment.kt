package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentMyBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.MyFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFragment : BaseFragment<FragmentMyBinding, MyFragViewModel>() {

    private val viewModel: MyFragViewModel by viewModels()

    override fun getViewMode(): MyFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentMyBinding =
        FragmentMyBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        binding.containerHis.setLimitClickListener {
            navController.navigate(MyFragmentDirections.actionMyFragmentToOrderListFragment(false))
        }
    }
}
