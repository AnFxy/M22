package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthUserWorkBinding
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserWorkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthUserWorkFragment : BaseFragment<FragmentAuthUserWorkBinding, AuthUserWorkViewModel>() {

    private val viewModel: AuthUserWorkViewModel by viewModels()

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentAuthUserWorkBinding.inflate(
        inflater, parent, false
    )
}