package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.databinding.FragmentAllAuthBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.AllAuthFragViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllAuthFragment : BaseFragment<FragmentAllAuthBinding, AllAuthFragViewModel>() {

    private val viewModel: AllAuthFragViewModel by viewModels()

    override fun getViewMode(): AllAuthFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentAllAuthBinding =
        FragmentAllAuthBinding.inflate(inflater, parent, false)

    override fun setLayout() {

        binding.containerWork.setLimitClickListener {
            navController.navigate(
                AllAuthFragmentDirections.actionAllAuthFragmentToAuthUserWorkFragment(
                    true
                )
            )
        }
        binding.containerUser.setLimitClickListener {
            if (LocalCache.workCredit == 0)
                return@setLimitClickListener
            navController.navigate(
                AllAuthFragmentDirections.actionAllAuthFragmentToAuthUserInfoFragment(
                    true
                )
            )
        }
        binding.containerContract.setLimitClickListener {
            if (LocalCache.infoCredit == 0 || LocalCache.workCredit == 0)
                return@setLimitClickListener
            navController.navigate(
                AllAuthFragmentDirections.actionAllAuthFragmentToAuthContactPersonFragment(
                    true
                )
            )
        }
        binding.containerId.setLimitClickListener {
            if (LocalCache.infoCredit == 0 || LocalCache.workCredit == 0 || LocalCache.contactPersonCredit == 0)
                return@setLimitClickListener
            navController.navigate(
                AllAuthFragmentDirections.actionAllAuthFragmentToAuthIdentityFragment(
                    true
                )
            )
        }
        binding.containerCard.setLimitClickListener {
            navController.navigate(
                AllAuthFragmentDirections.actionAllAuthFragmentToAddCardsFragment(
                    true
                )
            )
        }

        binding.tvApply.setLimitClickListener {
            if (LocalCache.workCredit == 0) {
                navController.navigate(
                    AllAuthFragmentDirections.actionAllAuthFragmentToAuthUserWorkFragment(
                        true
                    )

                )
            } else if (LocalCache.infoCredit == 0) {
                navController.navigate(
                    AllAuthFragmentDirections.actionAllAuthFragmentToAuthUserInfoFragment(
                        true
                    )
                )
            } else if (LocalCache.contactPersonCredit == 0) {
                navController.navigate(
                    AllAuthFragmentDirections.actionAllAuthFragmentToAuthContactPersonFragment(
                        true
                    )
                )
            } else if (LocalCache.idCredit == 0) {
                navController.navigate(
                    AllAuthFragmentDirections.actionAllAuthFragmentToAuthIdentityFragment(
                        true
                    )
                )
            } else if (LocalCache.bankCredit == 0) {
                navController.navigate(
                    AllAuthFragmentDirections.actionAllAuthFragmentToAddCardsFragment(
                        true
                    )
                )
            } else {
                navController.popBackStack()

                navController.navigate(
                    R.id.product_list_navigation,
                    null,
                    NavOptions.Builder()
                        .setRestoreState(true)
                        .setPopUpTo(
                            R.id.productListFragment,
                            false,
                            true
                        )
                        .build()
                )
            }
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.toUpdatePage.collect {
                updateAuthList()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateAuthList()
    }

    private fun updateAuthList() {
        binding.ivCheckStateUser.setImageResource(if (LocalCache.infoCredit == 1) R.mipmap.gouxuan else R.mipmap.weigouxuan)
        binding.ivCheckStateWork.setImageResource(if (LocalCache.workCredit == 1) R.mipmap.gouxuan else R.mipmap.weigouxuan)
        binding.ivCheckStateContract.setImageResource(if (LocalCache.contactPersonCredit == 1) R.mipmap.gouxuan else R.mipmap.weigouxuan)
        binding.ivCheckStateId.setImageResource(if (LocalCache.idCredit == 1 && LocalCache.faceCredit == 1) R.mipmap.gouxuan else R.mipmap.weigouxuan)
        binding.ivCheckStateCard.setImageResource(if (LocalCache.bankCredit == 1) R.mipmap.gouxuan else R.mipmap.weigouxuan)
    }
}
