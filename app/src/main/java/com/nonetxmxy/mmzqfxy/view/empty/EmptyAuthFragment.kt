package com.nonetxmxy.mmzqfxy.view.empty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.databinding.FragmentEmptyBinding
import com.nonetxmxy.mmzqfxy.model.PageType
import com.nonetxmxy.mmzqfxy.viewmodel.EmptyAuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmptyAuthFragment : BaseFragment<FragmentEmptyBinding, EmptyAuthViewModel>() {

    private val viewModel: EmptyAuthViewModel by viewModels()

    override fun getViewMode(): EmptyAuthViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentEmptyBinding =
        FragmentEmptyBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel._baseGoPage.collect {
                when (it) {
                    PageType.USER -> navController.navigate(EmptyAuthFragmentDirections.actionEmptyAuthFragmentToAuthUserInfoFragment())
                    PageType.WORK -> navController.navigate(EmptyAuthFragmentDirections.actionEmptyAuthFragmentToAuthUserWorkFragment())
                    PageType.CONTRACT -> navController.navigate(EmptyAuthFragmentDirections.actionEmptyAuthFragmentToAuthContactPersonFragment())
                    PageType.ID -> navController.navigate(EmptyAuthFragmentDirections.actionEmptyAuthFragmentToAuthIdentityFragment())
                    PageType.BANK -> navController.navigate(EmptyAuthFragmentDirections.actionEmptyAuthFragmentToAddCardsFragment())
                    PageType.FACE -> navController.navigate(EmptyAuthFragmentDirections.actionEmptyAuthFragmentToAuthIdentityFragment())
                    PageType.CONFIRM -> navController.navigate(EmptyAuthFragmentDirections.actionEmptyAuthFragmentToConfirmRequestFragment())
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.finishPage.collect {
                navController.popBackStack()
            }
        }
    }
}
