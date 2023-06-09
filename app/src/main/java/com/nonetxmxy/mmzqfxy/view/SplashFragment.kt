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
import com.nonetxmxy.mmzqfxy.tools.CommonUtil
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
                    viewLifecycleOwner.lifecycleScope.launch {
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.closePage.collect {
                activity?.finish()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showTipsDialog.collect {
                tipsDialog?.show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.showUpdateDialog.collect {
                val isNeedUpdate = it.oMGtrc == 0
                val isForceUpdate = it.ITwm == 1
                if (isForceUpdate) {
                    updateDialog?.setViewState<TextView>(R.id.tv_update_later) {
                        setVisible(false)
                    }?.setViewState<TextView>(R.id.tv_update_content) {
                        text = it.eHuxdiAPuTS
                    }?.setViewState<TextView>(R.id.tv_update_now) {
                        setLimitClickListener {
                            CommonUtil.goGooglePlay(activity, it.uvFD)
                        }
                    }?.show()
                } else if (isNeedUpdate) {
                    updateDialog?.setViewState<TextView>(R.id.tv_update_content) {
                        text = it.eHuxdiAPuTS
                    }?.setViewState<TextView>(R.id.tv_update_now) {
                        setLimitClickListener {
                            CommonUtil.goGooglePlay(activity, it.uvFD)
                        }
                    }?.show()
                } else {
                    navController.navigate(SplashFragmentDirections.goMain())
                }
            }
        }
    }
}