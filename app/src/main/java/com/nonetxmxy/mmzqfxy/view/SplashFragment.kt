package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentSplashBinding
import com.nonetxmxy.mmzqfxy.viewmodel.SplashFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashFragViewModel>() {

    private val viewModel: SplashFragViewModel by viewModels()

    override fun getViewMode(): SplashFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentSplashBinding =
        FragmentSplashBinding.inflate(inflater, parent, false)

    override fun setObserver() {
        lifecycleScope.launch {
            delay(1000)
            navController.navigate(SplashFragmentDirections.actionSplashFragmentToAuthNavigation())
        }
    }
}