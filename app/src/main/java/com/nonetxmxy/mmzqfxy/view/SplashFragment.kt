package com.nonetxmxy.mmzqfxy.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nonetxmxy.mmzqfxy.R
import com.nonetxmxy.mmzqfxy.base.BaseFragment
import com.nonetxmxy.mmzqfxy.base.LocalCache
import com.nonetxmxy.mmzqfxy.base.RxDialogSet
import com.nonetxmxy.mmzqfxy.databinding.FragmentSplashBinding
import com.nonetxmxy.mmzqfxy.model.UpdateType
import com.nonetxmxy.mmzqfxy.tools.setLimitClickListener
import com.nonetxmxy.mmzqfxy.tools.setVisible
import com.nonetxmxy.mmzqfxy.viewmodel.SplashFragViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashFragViewModel>() {

    private val viewModel: SplashFragViewModel by viewModels()

    private val tipsDialog: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_tips)
            dialog.setViewState<TextView>(R.id.tv_confirm) {
                setLimitClickListener {
                    lifecycleScope.launch {
                        viewModel.checkUpdate()
                    }
                    LocalCache.isShowedTips = true
                    dialog.dismiss()
                }
            }
            dialog.setViewState<TextView>(R.id.tv_cancel) {
                setLimitClickListener {
                    dialog.dismiss()
                    activity?.finish()
                }
            }
        }
    }

    private val updateDialog: RxDialogSet? by lazy {
        context?.let {
            val dialog = RxDialogSet.provideDialog(it, R.layout.dia_update)
            dialog.setViewState<TextView>(R.id.tv_update_later) {
                setLimitClickListener {
                    navController.navigate(SplashFragmentDirections.goMain())
                    dialog.dismiss()
                }
            }.setViewState<TextView>(R.id.tv_update_now) {
                setLimitClickListener {
                    // TODO 前往Google商店
                }
            }
        }
    }

    override fun getViewMode(): SplashFragViewModel = viewModel

    override fun getViewBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): FragmentSplashBinding =
        FragmentSplashBinding.inflate(inflater, parent, false)

    override fun setObserver() {
        navController = findNavController()

        lifecycleScope.launch {
            viewModel.closePage.collect {
                activity?.finish()
            }
        }

        lifecycleScope.launch {
            viewModel.showTipsDialog.collect {
                tipsDialog?.show()
            }
        }

        lifecycleScope.launch {
            viewModel.showUpdateDialog.collect {
                when (it) {
                    UpdateType.OPTIONAL -> updateDialog?.show()
                    UpdateType.FORCE ->
                        updateDialog?.setViewState<TextView>(R.id.tv_update_later) {
                            setVisible(false)
                        }?.show()
                    UpdateType.NO_NEED_UPDATE ->
                        navController.navigate(SplashFragmentDirections.goMain())
                }
            }
        }
    }
}