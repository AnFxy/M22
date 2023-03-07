package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.ui.setupWithNavController
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthIdentityBinding
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthIdentityFragment : BaseFragment<FragmentAuthIdentityBinding, AuthUserInfoViewModel>() {

    private val viewModel: AuthUserInfoViewModel by viewModels()

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater, parent: ViewGroup?
    ) = FragmentAuthIdentityBinding.inflate(
        inflater, parent, false
    )

    override fun FragmentAuthIdentityBinding.setLayout() {
        mToolbar.setupWithNavController(navController)
        mToolbar.setNavigationIcon(R.mipmap.fanhui)
        includeAuthTitle.image.setImageResource(R.mipmap.jinbi4)

    }
}