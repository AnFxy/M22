package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import com.nonetxmxy.mmzqfxy.BuildConfig
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.FragmentAboutCompanyBinding
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.viewmodel.AboutCompanyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AboutCompanyFragment : BaseFragment<FragmentAboutCompanyBinding, AboutCompanyViewModel>() {

    private val viewModel: AboutCompanyViewModel by viewModels()

    private val logoutDialog: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_logout)
            dialog.setViewState<TextView>(R.id.tv_cancel) {
                setLimitClickListener {
                    dialog.dismiss()
                }
            }.setViewState<TextView>(R.id.tv_logout) {
                setLimitClickListener {
                    viewModel.doLogout()
                }
            }
        }
    }

    override fun getViewMode(): AboutCompanyViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentAboutCompanyBinding =
        FragmentAboutCompanyBinding.inflate(inflater, parent, false)

    override fun setLayout() {
        binding.tvPackageVersion.text = "Version ${BuildConfig.VERSION_NAME}"

        binding.tvLogout.setLimitClickListener {
            logoutDialog?.show()
        }
    }

    override fun setObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.goLogin.collect {
                LocalCache.clearALLCache()
                logoutDialog?.dismiss()

                // 你可以按想要的路由退回去，然后再跳到指定目的地
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

                navController.navigate(ProductListFragmentDirections.actionProductListFragmentToLoginNavigation())
            }
        }
    }
}