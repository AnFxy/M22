package com.nonetxmxy.mmzqfxy.view.auth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.ui.setupWithNavController
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentAuthUserInfoBinding
import com.nonetxmxy.mmzqfxy.viewmodel.AuthUserIndoViewModel

class AuthUserIndoFragment : BaseFragment<FragmentAuthUserInfoBinding, AuthUserIndoViewModel>() {

    private val viewModel: AuthUserIndoViewModel by viewModels()

    override fun getViewMode() = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ) = FragmentAuthUserInfoBinding.inflate(inflater, parent, false)

    override fun FragmentAuthUserInfoBinding.setLayout() {
        toolBar.setupWithNavController(navController)
        toolBar.setNavigationIcon(R.mipmap.fanhui)

//        SpanUtils.with(includeAuthTitle.tvHint)
//            .append("*")
//            .setForegroundColor(ColorUtils.getColor(R.color.color_06047))
//            .append("*888")
//            .setForegroundColor(ColorUtils.getColor(R.color.gray_999999))
//            .create()

    }
}