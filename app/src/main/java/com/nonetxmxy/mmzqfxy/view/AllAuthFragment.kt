package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAllAuthBinding
import com.nonetxmxy.mmzqfxy.viewmodel.AllAuthFragViewModel

class AllAuthFragment : BaseFragment<FragmentAllAuthBinding, AllAuthFragViewModel>() {

    private val viewModel : AllAuthFragViewModel by viewModels()

    override fun getViewMode(): AllAuthFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentAllAuthBinding =
        FragmentAllAuthBinding.inflate(inflater, parent, false)

    override fun setObserver() {

    }

    private fun updateAuthList() {
        // binding.ivCheckStateUser.setImageResource()
    }
}
